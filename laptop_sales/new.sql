create database `laptop_sales`;

drop  database`laptop_sales`;

use `laptop_sales`;

create table `accounts` (
	id int(10) auto_increment primary key not null,
    username varchar(20) not null,
    password varchar(20) not null,
    email varchar(50),
    fullname varchar(50),
    birthday date,
    position varchar(20) not null,
    address varchar(100),
    phone VARCHAR(11),
    avatar varchar(200)
);

INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`)
VALUES ('nsh', '1', 'nguyensonhai1009@gmail.com', 'Nguyễn Sơn Hải', '1995-09-10', 'admin', 'TP. Hồ Chí Minh', '0766701009');

INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`)
VALUES ('nguyenngockha', '29041999', 'khanguyen1000@gmail.com', 'Nguyễn Ngọc Kha', '1999-04-29', 'admin', 'TP. Hồ Chí Minh', '0329457486');

INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`)
VALUES ('staff', '123', 'staff@gmail.com', 'Nhân Viên Số 1', '1999-09-09', 'staff', 'TP. Hồ Chí Minh', '');

UPDATE `accounts` SET `password` = ?, `email` = ?, `fullname` = ?, `birthday` = ?, `position` = ?, `address` = ?, `phone` = ? WHERE (`username` = ?);


select * from accounts;

CREATE TABLE `Category` (
	`categoryID` VARCHAR(50) NOT NULL,
	`categoryName` VARCHAR(50) NOT NULL ,
	PRIMARY KEY (`categoryID`)
)
;
INSERT INTO `laptop_sales`.`category` (`categoryID`, `categoryName`) VALUES ('bp', 'Bàn Phím');
INSERT INTO `laptop_sales`.`category` (`categoryID`, `categoryName`) VALUES ('ch', 'Chuột');
INSERT INTO `laptop_sales`.`category` (`categoryID`, `categoryName`) VALUES ('laptop', 'Laptop');
INSERT INTO `laptop_sales`.`category` (`categoryID`, `categoryName`) VALUES ('lkpc', 'Linh kiện PC');
INSERT INTO `laptop_sales`.`category` (`categoryID`, `categoryName`) VALUES ('loa', 'Loa');
INSERT INTO `laptop_sales`.`category` (`categoryID`, `categoryName`) VALUES ('mh', 'Màn Hình');
INSERT INTO `laptop_sales`.`category` (`categoryID`, `categoryName`) VALUES ('tn', 'Tai Nghe');

CREATE TABLE `Products` (
	`productID` INT NOT NULL AUTO_INCREMENT,
	`categoryID` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`producer` VARCHAR(50) NOT NULL,
	`info` VARCHAR(50) NOT NULL ,
	`img` VARCHAR(50) NOT NULL ,
	`price` DOUBLE NOT NULL,
	PRIMARY KEY (`productID`),
	CONSTRAINT `FK_Products_Category` FOREIGN KEY (`categoryID`) REFERENCES `Category` (`categoryID`)
)