# 실행

## 데이터베이스 컨테이너 만들기

`docker run -e MYSQL_ROOT_PASSWORD=rootpw123 -p 13336:3306 -d --name malldb mariadb`

## 계정 만들기
```sql
CREATE DATABASE malldb;

CREATE USER 'malladmin'@'localhost' IDENTIFIED BY 'adminpw123';
CREATE USER 'malladmin'@'%' IDENTIFIED BY 'adminpw123';

GRANT ALL PRIVILEGES ON malldb.* TO 'malladmin'@'localhost';
GRANT ALL PRIVILEGES ON malldb.* TO 'malladmin'@'%';

FLUSH PRIVILEGES;
```