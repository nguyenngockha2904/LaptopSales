-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: laptop_sales
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `fullname` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `position` varchar(20) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `avatar` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'nsh','1','nguyensonhai1009@gmail.com','Nguyễn Sơn Hải','1995-09-10','admin','TP. Hồ Chí Minh','0766701009',''),(2,'kha','1','khanguyen1000@gmail.com','Nguyễn Ngọc Kha','1998-04-29','admin','TP. Hồ Chí Minh','',''),(3,'ntho','1','hoangoanh@gmail.com','Nguyễn Thị Hoàng Oanh','1998-03-04','admin','TP. Hồ Chí Minh','0766700403','');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(10) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_phone` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `total` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bill_accounts_idx` (`user`),
  CONSTRAINT `FK_bill_accounts` FOREIGN KEY (`user`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_info`
--

DROP TABLE IF EXISTS `bill_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_info` (
  `bill_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `total` int(50) NOT NULL,
  PRIMARY KEY (`bill_id`,`product_id`),
  KEY `FK_billinfo_products_idx` (`product_id`),
  CONSTRAINT `FK_billinfo_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FX_billinfo_bill` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_info`
--

LOCK TABLES `bill_info` WRITE;
/*!40000 ALTER TABLE `bill_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES ('cpu','CPU'),('gpu','Card đồ hoạ'),('harddrive','Ổ đĩa cứng'),('keyboard','Bàn Phím'),('laptop','Laptop'),('mouse','Chuột');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `producer` varchar(50) NOT NULL,
  `info` varchar(500) NOT NULL,
  `img` varchar(500) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_products_categories` (`category_id`),
  CONSTRAINT `FK_products_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'laptop','Razer Blade Stealth 13.3 (2019)','Razer','i7 8565U RAM 16GB SSD 256GB MX150 FHD IPS Touch','file:/D:/Pictures/Products/Laptop/Razer_Blade_Stealth_13.3.jpg',39990000),(2,'laptop','Dell Alienware M15 R2','Dell','i7-9750H RAM 8GB SSD 256GB FHD IPS RTX 2060','file:/D:/Pictures/Products/Laptop/m15_r2.jpg',51990000),(3,'laptop','ThinkPad P1','Lenovo','i7-8750H RAM 16GB SSD 512GB Quadro P1000 FHD IPS','file:/D:/Pictures/Products/Laptop/p1.jpg',45990000),(4,'laptop','Asus ROG Strix G G731','Asus','Intel Core i7-9750H 2.6GHz up to 4.5GHz 12MB','file:/D:/Pictures/Products/Laptop/g731.jpg',41990000),(5,'laptop','HP Spectre X360 13 (2019)','HP','i5-8265U SSD 256GB RAM 8GB FHD IPS TOUCH','file:/D:/Pictures/Products/Laptop/hp_spectre_x360.jpg',41990000),(6,'cpu','Core i7 9700','Intel','12M / 3.0GHz upto 4.70GHz / 8 nhân 8 luồng','file:/D:/Pictures/Products/CPU/i7.jpg',8990000),(7,'cpu','Core i5 9500','Intel','9M / 3.0GHz upto 4.40GHz / 6 nhân 6 luồng','file:/D:/Pictures/Products/CPU/i5.jpg',5690000),(8,'cpu','Core i3 9100F','Intel','6M / 3.6GHz upto 4.20GHz / 4 nhân 4 luồng','file:/D:/Pictures/Products/CPU/i3.jpg',2190000),(9,'cpu','AMD Ryzen 9 3900x','AMD','70MB /3.8GHz /12 nhân 24 luồng','file:/D:/Pictures/Products/CPU/ryzen9.jpg',13090000),(10,'cpu','AMD Ryzen 7 3800x','AMD','36MB /3.9GHz /8 nhân 16 luồng','file:/D:/Pictures/Products/CPU/ryzen7.jpg',10290000),(11,'cpu','AMD Ryzen 5 3400G','AMD','6MB /3.7GHz /4 nhân 8 luồng','file:/D:/Pictures/Products/CPU/ryzen5.jpg',13090000),(12,'gpu','ROG Strix 2080Ti Matrix 11GB','Asus','','file:/D:/Pictures/Products/GPU/2080ti.png',54990000),(13,'gpu','ROG Strix GeForce RTX 2070 SUPER 8GB','Asus','','file:/D:/Pictures/Products/GPU/2070.png',16590000),(14,'gpu','GeForce RTX 2060 SUPER WINFORCE OC 8GB','NDIVIA','','file:/D:/Pictures/Products/GPU/2060.png',11990000),(15,'gpu','MSI GTX 1660 Ti GAMING X 6G','MSI','','file:/D:/Pictures/Products/GPU/1660ti.png',8390000),(16,'gpu','ROG Strix GeForce® GTX 1650 Advance 4GB','Asus','','file:/D:/Pictures/Products/GPU/1650.jpg',4770000),(17,'keyboard','Asus ROG Strix Scope','Asus','Nhà Sản Xuất : Asus ROG\n\nTình Trạng : Mới 100%- Fullbox\n\nBảo Hành : 36 tháng','file:/D:/Pictures/Products/Keyboards/bp1.png',300000),(18,'keyboard','Asus ROG Claymore Numpad','Asus','Nhà Sản Xuất : Asus\nTình Trạng : Mới 100% - Fullbox\nBảo Hành : 24 tháng','file:/D:/Pictures/Products/Keyboards/bp2.jpg',300000),(19,'keyboard','Asus Cerberus','Asus','Nhà Sản Xuất : Asus\nTình Trạng : Mới 100% - Fullbox\nBảo Hành : 24 tháng','file:/D:/Pictures/Products/Keyboards/bp3.jpg',250000),(20,'keyboard','Newmen T260+','Newmen','Nhà Sản Xuất : Newmen\n\nTình Trạng : Mới 100% - Fullbox\n\nBảo Hành : 12 tháng (Đổi mới 6 tháng đầu)','file:/D:/Pictures/Products/Keyboards/bp4.png',270000),(21,'keyboard','E-Dra EK387','E-Dra','Nhà Sản Xuất : E-Dra\n\nTình Trạng: Mới 100% - Fullbox\n\nBảo Hành: 24 tháng\n\nSwitch : Blue/Red/Brown\n\nLed: Rainbow','file:/D:/Pictures/Products/Keyboards/bp5.png',540000),(22,'keyboard','Cougar Aurora S','Cougar','Nhà Sản Xuất: Cougar\n\nBảo Hành: 12 tháng\n\nSwitch: Bàn Phím Giả Cơ ','file:/D:/Pictures/Products/Keyboards/bp6.png',890000),(23,'keyboard','Dare-U EK880 RGB 2019 - Black','Dare-U','Nhà Sản Xuất : Dare-U\n\nTình Trạng: Mới 100% - Fullbox\n\nBảo Hành: 24 tháng\n\nSwitch : D Switch - Blue/Red/Brown\n\nLed : RGB','file:/D:/Pictures/Products/Keyboards/bp7.jpg',880000),(24,'keyboard','Rapoo V580','Rapoo','Nhà Sản Xuất : Rapoo\n\n· Tình Trạng : Mới 100% - Fullbox\n\n· Bảo Hành : 12 tháng  \n\n· Switch : Rapoo Blue Switch','file:/D:/Pictures/Products/Keyboards/bp8.png',1200000),(25,'keyboard','Fuhlen D (Destroyer)','Fuhlen','Nhà sản xuất :Fuhlen\nTình trạng : Fullbox- 100%\nBảo hành : 24 tháng \nLED: Rainbow\nSwitch:  Quang học','file:/D:/Pictures/Products/Keyboards/bp9.jpg',990000),(26,'keyboard','K60M Cherry MX Blue','I-ROCKS','Nhà Sản Xuất : I-ROCKS\n\n Tình Trạng : Mới 100% - Fullbox\n\n Bảo Hành : 12 tháng ','file:/D:/Pictures/Products/Keyboards/bp10.png',1800000),(27,'mouse','Dare-U LM115G - Pink','Dare-U','Nhà Sản Xuất : Dare-U\n\nTình Trạng : Mới Fullbox 100%\n\nBảo Hành : 24 tháng\n\nMàu Sắc: Hồng','file:/D:/Pictures/Products/Mouses/ch1.png',200000),(28,'mouse','Durgod M39','Durgod','Nhà Sản Xuất : Durgod\n\nBảo Hành : 6 tháng','file:/D:/Pictures/Products/Mouses/ch2.jpg',200000),(29,'mouse','E-Dra EM614','E-Dra','Bảo Hành : 24 tháng ','file:/D:/Pictures/Products/Mouses/ch3.png',200000),(30,'mouse','Rapoo V26S','Rapoo','Nhà Sản Xuất: Rappo \n\nBảo Hành: 24 tháng \n\nTình Trạng: Mới 100%','file:/D:/Pictures/Products/Mouses/ch4.jpg',400000),(31,'mouse','Cougar 250M White RGB','Cougar ','Nhà sản xuất : COUGAR\n\nTình trạng : Mới 100%\n\nBảo Hành : 6 tháng','file:/D:/Pictures/Products/Mouses/ch5.png',600000),(32,'mouse','DS B1','MSI','·Nhà Sản Xuất : MSI\n\n· Tình Trạng : Mới 100% - Fullbox\n· Bảo Hành : 6 Tháng\n· Led : Đỏ\n· Trọng lượng: 108g\n· Số nút: 6 nút','file:/D:/Pictures/Products/Mouses/ch6.jpg',600000),(33,'mouse','Asus Cerberus','Asus','Nhà Sản Xuất: ASUS\n\nTình Trạng : Mới 100% - Fullbox\n\nBảo Hành : 24 Tháng','file:/D:/Pictures/Products/Mouses/ch7.png',500000),(34,'mouse','I-Rock M35 RGB','COUGAR','Nhà sản xuất : COUGAR\n\nTình trạng : Mới 100%\n\nBảo Hành : 12 tháng','file:/D:/Pictures/Products/Mouses/ch8.png',500000),(35,'mouse','Lenovo Legion M200 RGB','Lenovo ','Nhà Sản Xuất: Lenovo\n\nTình Trạng : Mới 100% - Fullbox\n\nBảo Hành : 12 Tháng','file:/D:/Pictures/Products/Mouses/ch9.jpg',490000),(36,'mouse','GM40 - Black','MSI ','Nhà sản xuất : MSI \n\nTình trạng : Mới 100% - FULLBOX\n\nBảo Hành : 24 tháng.','file:/D:/Pictures/Products/Mouses/ch10.jpg',740000);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `products_remaining` int(11) NOT NULL,
  `products_sold` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id_idx` (`product_id`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,1,30,0),(2,2,30,0),(3,3,30,0),(4,4,30,0),(5,5,30,0),(6,6,30,0),(7,7,30,0),(8,8,30,0),(9,9,30,0),(10,10,30,0),(11,11,30,0),(12,12,30,0),(13,13,30,0),(14,14,30,0),(15,15,30,0),(16,16,30,0),(17,17,30,0),(18,18,30,0),(19,19,30,0),(20,20,30,0),(21,21,30,0),(22,22,30,0),(23,23,30,0),(24,24,30,0),(25,25,30,0),(26,26,30,0),(27,27,30,0),(28,28,30,0),(29,29,30,0),(30,30,30,0),(31,31,30,0),(32,32,30,0),(33,33,30,0),(34,34,30,0),(35,35,30,0),(36,36,30,0);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'laptop_sales'
--
/*!50003 DROP PROCEDURE IF EXISTS `showBarChart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBarChart`(in bill_id int)
BEGIN
SELECT products.producer, sum(bill_info.amount)
FROM
    products 
    INNER JOIN bill_info ON bill_info.product_id = products.id
where bill_info.bill_id=bill_id
group by products.producer;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithDate`(in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where DATE(bill.date)=DATE(date);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithDuringTime` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithDuringTime`(in startdate datetime, in enddate datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithMonth` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithMonth`(in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where year(bill.date)=year(date) and month(bill.date)=month(date);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUser`(in user int)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithUserDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserDate`(in user int,in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and date(bill.date)=date(date);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithUserDuringTime` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserDuringTime`(in user int,in startdate datetime, in enddate datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithUserMonth` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserMonth`(in user int,in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and month(bill.date)=month(date) and year(bill.date)=year(date);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithUserYear` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithUserYear`(in user int,in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where accounts.id=user and Year(bill.date)=Year(date);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showBillWithYear` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showBillWithYear`(in date datetime)
BEGIN
select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total 
from accounts
 INNER JOIN bill ON accounts.id=bill.user
where Year(bill.date)=Year(date);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChart`(in id int)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithDate`(date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where DATE(bill.date)=DATE(date)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithDateAndUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithDateAndUser`(in id int, date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and DATE(bill.date)=DATE(date)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithDuringTime` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithDuringTime`(in startdate datetime, in enddate datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithDuringTimeAndUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithDuringTimeAndUser`(in id int, in startdate datetime, in enddate datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and date(bill.date)>=date(startdate) and date(bill.date)<=date(enddate)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithMonth` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithMonth`(date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where month(bill.date) = month(date) and year(bill.date)=year(date)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithMonthAndUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithMonthAndUser`(in id int, date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and month(bill.date) = month(date) and Year(bill.date)=Year(date)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithYear` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithYear`(date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where year(bill.date) = year(date)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `showPieChartWithYearAndUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPieChartWithYearAndUser`(in id int, date datetime)
BEGIN
SELECT categories.name,sum(bill_info.amount)
FROM
    products 
    INNER JOIN categories ON products.category_id=categories.id
    INNER JOIN bill_info ON products.id = bill_info.product_id
    INNER JOIN bill ON bill_info.bill_id = bill.id
where bill.user=id and year(bill.date) = year(date)
group by categories.name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-07 22:06:06
