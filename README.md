# Agent
Отдельный сервис для агентов

•	Создать методы Login, Logout и Register отдельно от готовых  методов Spring Security 
1)	 Register: Только админ может добавлять аккаунты для агентов 
Создать таблицу, что бы хранить данных об агенте 
 Table name: `Agent`
 Parameters: ```FirstName, SecondName, Nickname, password``` (генерировать пароль самим с использованием цифор, букв(```Uppercase, Lowercase```). Шифровать пароль через sha256 + solt(```nickname```)) ```loginDate, logoutDate, islogged```(```Boolean```)
2)	Login: Заходить через никнейм и пароль 
И учесть, что через один аккаунт может заходить только один агент. 
Создать три метода (любых не важно содержание) которому вы будете давать доступ только для агентов 
Записывать время входа в `Agent` -> `loginDate`

3)	Logout: Выйти из системы 
Записывать время выхода в `Agent` -> `logoutDate`