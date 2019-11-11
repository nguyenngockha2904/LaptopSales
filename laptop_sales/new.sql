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
	`name` VARCHAR(100) NOT NULL,
	`producer` VARCHAR(50) NOT NULL,
	`info` VARCHAR(500) NOT NULL ,
	`img` VARCHAR(500) NOT NULL ,
	`price` DOUBLE NOT NULL,
	PRIMARY KEY (`productID`),
	CONSTRAINT `FK_Products_Category` FOREIGN KEY (`categoryID`) REFERENCES `Category` (`categoryID`)
);
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('10', 'tn', 'Tai nghe Rapoo VH300 Blue Virtual 7.1', 'Rapoo', 'Tình Trạng : Mới 100% - Fullbox\n· Bảo Hành : 24 tháng \n· Màu sắc: Đen\n· Led: Xanh\n· Port: USB Port', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/tn1.png', '840000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('12', 'bp', 'Asus Cerberus', 'Asus', 'Tình Trạng : Mới 100% - Fullbox\nBảo Hành : 24 tháng', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/bp1.jpg', '999000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('13', 'ch', 'Chuột Gaming Cougar Minos XT', 'Cougar ', 'Bảo Hành: 12 tháng,Tình Trạng: 100% Fullbox', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/ch1.jpg', '1200000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('15', 'lkpc', 'Fan LED 1st Player R1 120mm RGB', 'XIGMATEK', 'Nhà sản xuất  : XIGMATEK\n\nMàu                 : RGB\n\nBảo Hành        : 12 Tháng', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/lkpc1.png', '100000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('16', 'lkpc', 'AORUS GeForce® RTX 2080 Ti XTREME 11G GDDR6', 'GIGABYTE', 'Mã sản phẩm: N208TAORUS X-11GC\n\nTình trạng: Fullbox – 100%\n\nBảo hành: 36 tháng', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/lkpc2.jpg', '36890000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('17', 'lkpc', 'SSD APACER AS340 Panther 2.5 Sata III 120Gb', 'APACER', 'Mã sản phẩm: AS340 PANTHER SATA III SSD\n\nTình trạng: Fullbox – 100%\n\nBảo hành: 36 tháng', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/lkpc3.png', '550000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('18', 'lkpc', 'Intel Core i3 8100 / 6M / 3.6GHz / 4 nhân 4 luồng', 'Intel', '-Tình trạng : NEW - 100%\n-Bảo hành : 36 tháng ', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/lkpc4.jpg', '3250000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('19', 'bp', 'Bàn phím E-Dra EK387', ' E-Dra', 'Tình Trạng: Mới 100% - Fullbox\n\nBảo Hành: 24 tháng\n\nSwitch : Blue/Red/Brown\n\nLed: Rainbow', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/bp2.png', '540000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('20', 'bp', 'Bàn phím Leopold FC750R PD Black Red', 'Leopold ', 'Tình trạng : Mới - 100%\nBảo hành : 24 tháng \nSwitch : Blue/Brown/Red\nLed : Không', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/bp3.png', '3050000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('21', 'laptop', 'Laptop Asus ZenBook UX433FA A6053T', 'ASUS', 'Intel® Core™ i5-8265U\n8GB LPDDR3 Bus 2133Mhz (Onboard)\n256GB SSD M.2 PCIe \nIntel UHD Graphics 620\n14-inch FHD', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/lp2.jpg', '22490000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('22', 'ch', 'Chuột Razer Deathadder Essential', 'Razer', ' Tình Trạng : Mới 100% - Fullbox\n· Bảo Hành : 24 tháng \n\n· Led : Green', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/ch2.png', '790000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('23', 'ch', 'Chuột Dare-U EM908 RGB', 'Dare-U', 'Tình Trạng : Có sẵn hàng\n\nBảo Hành : 24 tháng\n\nLed : RGB', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/ch3.jpg', '390000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('24', 'tn', 'Tai nghe DareU EH722S 7.1', ' Dare-U', 'Tình Trạng : Mới 100% - Fullbox\n\n· Bảo Hành : 12 tháng  \n\n· Màu sắc: Đen\n\n· Led: RGB', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/loa2.jpg', '590000');
INSERT INTO `laptop_sales`.`products` (`productID`, `categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES ('25', 'loa', 'Loa Logitech Z333', 'Logitech', 'Nhà sản xuất : Logitech\nTình trạng : Fullbox-100%\nBảo hành : 12 Tháng', 'file:/C:/Git/laptop_sales/src/com/superducks/laptopsales/images/Loa1.jpg', '1490000');

CREATE TABLE `laptop_sales`.`bill` (
  `idbill` INT(11) NOT NULL AUTO_INCREMENT,
  `idUser` INT(10) NOT NULL,
  `date` DATETIME NOT NULL,
  `total` INT(11) NOT NULL,
  PRIMARY KEY (`idbill`),
  INDEX `FK_bill_accounts_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `FK_bill_accounts`
    FOREIGN KEY (`idUser`)
    REFERENCES `laptop_sales`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `laptop_sales`.`billinfo` (
  `idbill` INT(11) NOT NULL,accounts
  `productID` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL,
  `total` INT(11) NOT NULL,
  PRIMARY KEY (`idbill`, `productID`),
  INDEX `FK_billinfo_products_idx` (`productID` ASC) VISIBLE,
  CONSTRAINT `FK_billinfo_products`
    FOREIGN KEY (`productID`)
    REFERENCES `laptop_sales`.`products` (`productID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FX_billinfo_bill`
    FOREIGN KEY (`idbill`)
    REFERENCES `laptop_sales`.`bill` (`idbill`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE `laptop_sales`.`cart` (
  `idcart` INT NOT NULL AUTO_INCREMENT,
  `img` LONGTEXT NULL,
  `name` VARCHAR(45) NULL,
  `total` VARCHAR(45) NULL,
  PRIMARY KEY (`idcart`));


