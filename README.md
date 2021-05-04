# TrafficFines
## Описание ##
TO-DO

## Start ##
1. sudo apt-get update
2. git clone https://github.com/atommaks/TrafficFInes.git
3. Установка mysql-сервера 8 версии (нужна 8 версия или выше для корректной работы):
    * sudo apt-get install wget
    * wget https://dev.mysql.com/get/mysql-apt-config_0.8.15-1_all.deb
    * sudo dpkg -i mysql-apt-config_0.8.15-1_all.deb
    *  В 1-ом пункте в открывшимся листе выберете 8 версию
    * Нажмите ОК
    * sudo apt update
    * sudo apt install mysql-server
    * установите для пользователя root пароль "atom1105"
    * далее везде со всем согласитесь
    * С помощью команды apt policy mysql-server проверьте установленную версию
4. Запуск mysql-сервера:
    * sudo systemctl enable —now mysql
    * C помощью команды systemctl status mysql убедитесь, что сервер запущен
5. Настройка mysql-сервера:
    * sudo mysql -u root -p
    * Введите пароль (он должен быть atom1105)
    * create database TrafficFines;
    * exit;
    * sudo mysql -u root -p TrafficFines < ~/TrafficFines/dumpfile.sql
    * sudo mysql
    * UNINSTALL PLUGIN validate-password;
    * ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'atom1105';
    * exit;
6. Если не установлена Java 8 или выше версии, то установите ее, следуя [инструкции](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-18-04-ru) 
7. Запуск приложения:
    * java -jar ./TrafficFines/TrafficFines.jar
