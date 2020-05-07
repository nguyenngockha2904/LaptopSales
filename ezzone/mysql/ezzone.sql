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
    avatar longtext
);

INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`, `avatar`)
VALUES ('nsh', '1', 'nguyensonhai1009@gmail.com', 'Nguyễn Sơn Hải', '1995-09-10', 'admin', 'TP. Hồ Chí Minh', '0766701009', '');
INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`, `avatar`)
VALUES ('kha', '1', 'khanguyen1000@gmail.com', 'Nguyễn Ngọc Kha', '1998-04-29', 'admin', 'TP. Hồ Chí Minh', '', '');
INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`, `avatar`)
VALUES ('ntho', '1', 'hoangoanh@gmail.com', 'Nguyễn Thị Hoàng Oanh', '1998-03-04', 'admin', 'TP. Hồ Chí Minh', '0766700403', '');

select * from accounts;
select * from categories;
select * from products;

CREATE TABLE `categories` (
	id VARCHAR(50) NOT NULL,
	name VARCHAR(50) NOT NULL ,
	PRIMARY KEY (id)
);

INSERT INTO `laptop_sales`.`categories` (`id`, `name`) VALUES ('laptop', 'Laptop');
INSERT INTO `laptop_sales`.`categories` (`id`, `name`) VALUES ('mouse', 'Chuột');
INSERT INTO `laptop_sales`.`categories` (`id`, `name`) VALUES ('keyboard', 'Bàn Phím');
INSERT INTO `laptop_sales`.`categories` (`id`, `name`) VALUES ('harddrive', 'Ổ đĩa cứng');
INSERT INTO `laptop_sales`.`categories` (`id`, `name`) VALUES ('cpu', 'CPU');
INSERT INTO `laptop_sales`.`categories` (`id`, `name`) VALUES ('gpu', 'Card đồ hoạ');


CREATE TABLE `products` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`category_id` VARCHAR(50) NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	`producer` VARCHAR(50) NOT NULL,
	`info` VARCHAR(500) NOT NULL ,
	`img` VARCHAR(500) NOT NULL ,
	`price` DOUBLE NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_products_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
);

INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('laptop', 'Razer Blade Stealth 13.3 (2019)', 'Razer', 'i7 8565U RAM 16GB SSD 256GB MX150 FHD IPS Touch', 'file:/D:/Pictures/Products/Laptop/Razer_Blade_Stealth_13.3.jpg', '39990000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('laptop', 'Dell Alienware M15 R2', 'Dell', 'i7-9750H RAM 8GB SSD 256GB FHD IPS RTX 2060', 'file:/D:/Pictures/Products/Laptop/m15_r2.jpg', '51990000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('laptop', 'ThinkPad P1', 'Lenovo', 'i7-8750H RAM 16GB SSD 512GB Quadro P1000 FHD IPS', 'file:/D:/Pictures/Products/Laptop/p1.jpg', '45990000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('laptop', 'Asus ROG Strix G G731', 'Asus', 'Intel Core i7-9750H 2.6GHz up to 4.5GHz 12MB', 'file:/D:/Pictures/Products/Laptop/g731.jpg', '41990000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('laptop', 'HP Spectre X360 13 (2019)', 'HP', 'i5-8265U SSD 256GB RAM 8GB FHD IPS TOUCH', 'file:/D:/Pictures/Products/Laptop/hp_spectre_x360.jpg', '41990000');

INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('cpu', 'Core i7 9700', 'Intel', '12M / 3.0GHz upto 4.70GHz / 8 nhân 8 luồng', 'file:/D:/Pictures/Products/CPU/i7.jpg', '8990000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('cpu', 'Core i5 9500', 'Intel', '9M / 3.0GHz upto 4.40GHz / 6 nhân 6 luồng', 'file:/D:/Pictures/Products/CPU/i5.jpg', '5690000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('cpu', 'Core i3 9100F', 'Intel', '6M / 3.6GHz upto 4.20GHz / 4 nhân 4 luồng', 'file:/D:/Pictures/Products/CPU/i3.jpg', '2190000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('cpu', 'AMD Ryzen 9 3900x', 'AMD', '70MB /3.8GHz /12 nhân 24 luồng', 'file:/D:/Pictures/Products/CPU/ryzen9.jpg', '13090000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('cpu', 'AMD Ryzen 7 3800x', 'AMD', '36MB /3.9GHz /8 nhân 16 luồng', 'file:/D:/Pictures/Products/CPU/ryzen7.jpg', '10290000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('cpu', 'AMD Ryzen 5 3400G', 'AMD', '6MB /3.7GHz /4 nhân 8 luồng', 'file:/D:/Pictures/Products/CPU/ryzen5.jpg', '13090000');

INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('gpu', 'ROG Strix 2080Ti Matrix 11GB', 'Asus', '', 'file:/D:/Pictures/Products/GPU/2080ti.png', '54990000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('gpu', 'ROG Strix GeForce RTX 2070 SUPER 8GB', 'Asus', '', 'file:/D:/Pictures/Products/GPU/2070.png', '16590000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('gpu', 'GeForce RTX 2060 SUPER WINFORCE OC 8GB', 'NDIVIA', '', 'file:/D:/Pictures/Products/GPU/2060.png', '11990000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('gpu', 'MSI GTX 1660 Ti GAMING X 6G', 'MSI', '', 'file:/D:/Pictures/Products/GPU/1660ti.png', '8390000');
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ('gpu', 'ROG Strix GeForce® GTX 1650 Advance 4GB', 'Asus', '', 'file:/D:/Pictures/Products/GPU/1650.jpg', '4770000');

INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Asus ROG Strix Scope', 'Asus', 'Nhà Sản Xuất : Asus ROG\n\nTình Trạng : Mới 100%- Fullbox\n\nBảo Hành : 36 tháng', 'file:/D:/Pictures/Products/Keyboards/bp1.png', '300000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Asus ROG Claymore Numpad', 'Asus', 'Nhà Sản Xuất : Asus\nTình Trạng : Mới 100% - Fullbox\nBảo Hành : 24 tháng', 'file:/D:/Pictures/Products/Keyboards/bp2.jpg', '300000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Asus Cerberus', 'Asus', 'Nhà Sản Xuất : Asus\nTình Trạng : Mới 100% - Fullbox\nBảo Hành : 24 tháng', 'file:/D:/Pictures/Products/Keyboards/bp3.jpg', '250000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Newmen T260+', 'Newmen', 'Nhà Sản Xuất : Newmen\n\nTình Trạng : Mới 100% - Fullbox\n\nBảo Hành : 12 tháng (Đổi mới 6 tháng đầu)', 'file:/D:/Pictures/Products/Keyboards/bp4.png', '270000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'E-Dra EK387', 'E-Dra', 'Nhà Sản Xuất : E-Dra\n\nTình Trạng: Mới 100% - Fullbox\n\nBảo Hành: 24 tháng\n\nSwitch : Blue/Red/Brown\n\nLed: Rainbow', 'file:/D:/Pictures/Products/Keyboards/bp5.png', '540000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Cougar Aurora S', 'Cougar', 'Nhà Sản Xuất: Cougar\n\nBảo Hành: 12 tháng\n\nSwitch: Bàn Phím Giả Cơ ', 'file:/D:/Pictures/Products/Keyboards/bp6.png', '890000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Dare-U EK880 RGB 2019 - Black', 'Dare-U', 'Nhà Sản Xuất : Dare-U\n\nTình Trạng: Mới 100% - Fullbox\n\nBảo Hành: 24 tháng\n\nSwitch : D Switch - Blue/Red/Brown\n\nLed : RGB', 'file:/D:/Pictures/Products/Keyboards/bp7.jpg', '880000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Rapoo V580', 'Rapoo', 'Nhà Sản Xuất : Rapoo\n\n· Tình Trạng : Mới 100% - Fullbox\n\n· Bảo Hành : 12 tháng  \n\n· Switch : Rapoo Blue Switch', 'file:/D:/Pictures/Products/Keyboards/bp8.png', '1200000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'Fuhlen D (Destroyer)', 'Fuhlen', 'Nhà sản xuất :Fuhlen\nTình trạng : Fullbox- 100%\nBảo hành : 24 tháng \nLED: Rainbow\nSwitch:  Quang học', 'file:/D:/Pictures/Products/Keyboards/bp9.jpg', '990000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'keyboard', 'K60M Cherry MX Blue', 'I-ROCKS', 'Nhà Sản Xuất : I-ROCKS\n\n Tình Trạng : Mới 100% - Fullbox\n\n Bảo Hành : 12 tháng ', 'file:/D:/Pictures/Products/Keyboards/bp10.png', '1800000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ( 'mouse', 'Dare-U LM115G - Pink', 'Dare-U', 'Nhà Sản Xuất : Dare-U\n\nTình Trạng : Mới Fullbox 100%\n\nBảo Hành : 24 tháng\n\nMàu Sắc: Hồng', 'file:/D:/Pictures/Products/Mouses/ch1.png', '200000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'mouse', 'Durgod M39', 'Durgod', 'Nhà Sản Xuất : Durgod\n\nBảo Hành : 6 tháng', 'file:/D:/Pictures/Products/Mouses/ch2.jpg', '200000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`) 
VALUES ( 'mouse', 'E-Dra EM614', 'E-Dra', 'Bảo Hành : 24 tháng ', 'file:/D:/Pictures/Products/Mouses/ch3.png', '200000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'mouse', 'Rapoo V26S', 'Rapoo', 'Nhà Sản Xuất: Rappo \n\nBảo Hành: 24 tháng \n\nTình Trạng: Mới 100%', 'file:/D:/Pictures/Products/Mouses/ch4.jpg', '400000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'mouse', 'Cougar 250M White RGB', 'Cougar ', 'Nhà sản xuất : COUGAR\n\nTình trạng : Mới 100%\n\nBảo Hành : 6 tháng', 'file:/D:/Pictures/Products/Mouses/ch5.png', '600000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'mouse', 'DS B1', 'MSI', '·Nhà Sản Xuất : MSI\n\n· Tình Trạng : Mới 100% - Fullbox\n· Bảo Hành : 6 Tháng\n· Led : Đỏ\n· Trọng lượng: 108g\n· Số nút: 6 nút', 'file:/D:/Pictures/Products/Mouses/ch6.jpg', '600000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`) 
VALUES ( 'mouse', 'Asus Cerberus', 'Asus', 'Nhà Sản Xuất: ASUS\n\nTình Trạng : Mới 100% - Fullbox\n\nBảo Hành : 24 Tháng', 'file:/D:/Pictures/Products/Mouses/ch7.png', '500000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
VALUES ( 'mouse', 'I-Rock M35 RGB', 'COUGAR', 'Nhà sản xuất : COUGAR\n\nTình trạng : Mới 100%\n\nBảo Hành : 12 tháng', 'file:/D:/Pictures/Products/Mouses/ch8.png', '500000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'mouse', 'Lenovo Legion M200 RGB', 'Lenovo ', 'Nhà Sản Xuất: Lenovo\n\nTình Trạng : Mới 100% - Fullbox\n\nBảo Hành : 12 Tháng', 'file:/D:/Pictures/Products/Mouses/ch9.jpg', '490000' );
INSERT INTO `products` (`category_id`, `name`, `producer`, `info`, `img`, `price`)
 VALUES ( 'mouse', 'GM40 - Black', 'MSI ', 'Nhà sản xuất : MSI \n\nTình trạng : Mới 100% - FULLBOX\n\nBảo Hành : 24 tháng.', 'file:/D:/Pictures/Products/Mouses/ch10.jpg', '740000' );


CREATE TABLE `bill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user` INT(10) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_phone` int(11) NOT NULL,
  `date` DATETIME NOT NULL,
  `total` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_bill_accounts_idx` (`user` ASC) VISIBLE,
  CONSTRAINT `FK_bill_accounts`
    FOREIGN KEY (`user`)
    REFERENCES `accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `bill_info` (
  `bill_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL,
  `total` INT(50) NOT NULL,
  PRIMARY KEY (`bill_id`, `product_id`),
  INDEX `FK_billinfo_products_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `FK_billinfo_products`
    FOREIGN KEY (`product_id`)
    REFERENCES `products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FX_billinfo_bill`
    FOREIGN KEY (`bill_id`)
    REFERENCES `bill` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `warehouse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `products_remaining` INT NOT NULL,
  `products_sold` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `product_id_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    

USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChart`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChart` (in id int)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id
group by categories.name;
END$$
call showPieChart(1);

USE `laptop_sales`;
DROP procedure IF EXISTS `showBarChart`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showBarChart` (in bill_id int)
BEGIN
SELECT products.producer, sum(bill_info.amount)
FROM
    products 
    INNER JOIN bill_info ON bill_info.product_id = products.id
where bill_info.bill_id=bill_id
group by products.producer;
END$$

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithUser`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUser`(in user int)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user;
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithUserYear`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserYear`(in user int,in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and Year(bill.date)=Year(date);
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithUserMonth`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserMonth`(in user int,in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and month(bill.date)=month(date) and year(bill.date)=year(date);
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithUserDate`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserDate`(in user int,in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and date(bill.date)=date(date);
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithUserDuringTime`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserDuringTime`(in user int,in startdate datetime, in enddate datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate);
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithDate`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithDate`(in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where DATE(bill.date)=DATE(date);
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithMonth`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithMonth`(in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where year(bill.date)=year(date) and month(bill.date)=month(date);
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithYear`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithYear`(in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where Year(bill.date)=Year(date);
END$$

DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showBillWithDuringTime`;

DELIMITER $$
USE `laptop_sales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithDuringTime`(in startdate datetime, in enddate datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate);
END$$

DELIMITER ;


USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithDuringTimeAndUser`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithDuringTimeAndUser` (in id int, in startdate datetime, in enddate datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate)
group by categories.name;
END$$
DELIMITER ;


USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithDateAndUser`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithDateAndUser` (in id int, date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and DATE(bill.date)=DATE(date)
group by categories.name;
END$$
DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithMonthAndUser`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithMonthAndUser` (in id int, date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and month(bill.date) = month(date) and Year(bill.date)=Year(date)
group by categories.name;
END$$
DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithYearAndUser`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithYearAndUser` (in id int, date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and year(bill.date) = year(date)
group by categories.name;
END$$
DELIMITER ;


USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithDuringTime`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithDuringTime` (in startdate datetime, in enddate datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate)
group by categories.name;
END$$
DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithDate`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithDate` (date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where DATE(bill.date)=DATE(date)
group by categories.name;
END$$
DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithMonth`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithMonth` (date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where month(bill.date) = month(date) and year(bill.date)=year(date)
group by categories.name;
END$$
DELIMITER ;

USE `laptop_sales`;
DROP procedure IF EXISTS `showPieChartWithYear`;
DELIMITER $$
USE `laptop_sales`$$
CREATE PROCEDURE `showPieChartWithYear` (date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where year(bill.date) = year(date)
group by categories.name;
END$$
DELIMITER ;



