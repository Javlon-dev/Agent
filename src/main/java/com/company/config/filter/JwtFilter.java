package com.company.config.filter;

import com.company.config.details.CustomUserDetails;
import com.company.dto.JwtDTO;
import com.company.enums.Role;
import com.company.repository.AgentRepository;
import com.company.service.details.CustomAdminDetailsService;
import com.company.service.details.CustomAgentDetailsService;
import com.company.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final CustomAgentDetailsService customAgentDetailsService;

    private final CustomAdminDetailsService customAdminDetailsService;

    private final AgentRepository agentRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (!Optional.ofNullable(header).isPresent() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            JwtDTO jwtDTO;
            try {
                jwtDTO = JwtUtil.decode(token);
            } catch (JwtException e) {
                log.warn("Jwt Invalid token={}", token);

                agentRepository.updateToken(LocalDateTime.now(), token);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Message", "Agent Already Login!");
                return;
            }

            CustomUserDetails details;
            if (jwtDTO.getRole().equals(Role.ROLE_ADMIN)) {
                details = (CustomUserDetails) customAdminDetailsService
                        .loadUserByUsername(jwtDTO.getId());

            } else { // ROLE_AGENT
                details = (CustomUserDetails) customAgentDetailsService
                        .loadUserByUsername(jwtDTO.getId());

                if (!details.getToken().equals(token)) {
                    log.warn("Multipart Agent id={}", details.getId());

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader("Message", "Agent Already Login!");
                    return;
                }

            }

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(details,
                    null, details.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);

    }


}
