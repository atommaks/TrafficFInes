-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: localhost    Database: TrafficFines
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Cars`
--

DROP TABLE IF EXISTS `Cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Cars` (
  `CarId` int(11) NOT NULL AUTO_INCREMENT,
  `Model` varchar(36) NOT NULL,
  `Brand` varchar(50) NOT NULL,
  `Color` varchar(20) NOT NULL,
  `StateNumber` varchar(9) NOT NULL,
  PRIMARY KEY (`CarId`),
  UNIQUE KEY `cars_stnum_uniqe` (`StateNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cars`
--

LOCK TABLES `Cars` WRITE;
/*!40000 ALTER TABLE `Cars` DISABLE KEYS */;
INSERT INTO `Cars` VALUES (2,'Седан','Mercedes A-Class','Серый','о011оо777'),(3,'Седан','Mazda6','Красный','о012оо777'),(4,'Седан','Mercedes A-Class','Черный','о013оо777'),(5,'Седан','BMW Series 5','Серый','о014оо777'),(6,'Седан','BMW Series 5','Синий','о015оо777'),(7,'Хэтчбек','Mercedes B-Class','Черный','о016оо777'),(8,'Хэтчбек','BMW Series 3','Черный','о017оо777'),(9,'Хэтчбек','Volvo V40','Красный','о018оо777'),(10,'Хэтчбек','BMW Series 3','Синий','о019оо777'),(11,'Хэтчбек','Mercedes B-Class','Серый','о020оо777'),(12,'Внедорожник','Mitsubishi Outlander','Серый','о021оо777'),(13,'Внедорожник','Mitsubishi Outlander','Белый','о022оо777'),(14,'Минивен','Mercedes W-Class','Черный','о023оо777');
/*!40000 ALTER TABLE `Cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DrivingLicences`
--

DROP TABLE IF EXISTS `DrivingLicences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `DrivingLicences` (
  `DrivingLicenceNumber` varchar(10) NOT NULL,
  `IssueDate` date NOT NULL,
  `ExpirationDate` date NOT NULL,
  `Categories` varchar(57) NOT NULL,
  `IssueDepartment` varchar(20) NOT NULL,
  PRIMARY KEY (`DrivingLicenceNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DrivingLicences`
--

LOCK TABLES `DrivingLicences` WRITE;
/*!40000 ALTER TABLE `DrivingLicences` DISABLE KEYS */;
INSERT INTO `DrivingLicences` VALUES ('9921300001','2015-11-11','2025-11-11','B B1 M','ГИБДД 7720'),('9921300002','2016-10-11','2026-10-11','B B1 M','ГИБДД 7718'),('9921300003','2017-09-11','2027-09-11','B B1 M','ГИБДД 7719'),('9921300004','2018-08-11','2028-08-11','B B1 M','ГИБДД 7719'),('9921300005','2019-07-11','2029-07-11','B B1 M','ГИБДД 7720'),('9921300006','2013-06-11','2023-06-11','B B1 M','ГИБДД 7720'),('9921300007','2017-02-13','2027-02-13','B B1 M','ГИБДД 7718'),('9921300009','2017-02-20','2027-02-20','B B1 M','ГИБДД 7718');
/*!40000 ALTER TABLE `DrivingLicences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Fines`
--

DROP TABLE IF EXISTS `Fines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Fines` (
  `FineId` int(11) NOT NULL AUTO_INCREMENT,
  `FineTypeId` int(11) NOT NULL,
  `FineDate` date NOT NULL,
  `DriverLicenceNumber` varchar(10) NOT NULL,
  `StateNumber` varchar(9) NOT NULL,
  `Deprivation` tinyint(1) DEFAULT NULL,
  `Paid` tinyint(1) NOT NULL DEFAULT (0),
  PRIMARY KEY (`FineId`),
  KEY `fines_type_fk` (`FineTypeId`),
  KEY `fines_licence_fk` (`DriverLicenceNumber`),
  KEY `StateNumber` (`StateNumber`),
  CONSTRAINT `fines_ibfk_1` FOREIGN KEY (`StateNumber`) REFERENCES `cars` (`StateNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fines_licence_fk` FOREIGN KEY (`DriverLicenceNumber`) REFERENCES `drivinglicences` (`DrivingLicenceNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fines_type_fk` FOREIGN KEY (`FineTypeId`) REFERENCES `finestype` (`TypeId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Fines`
--

LOCK TABLES `Fines` WRITE;
/*!40000 ALTER TABLE `Fines` DISABLE KEYS */;
INSERT INTO `Fines` VALUES (2,5,'2021-04-03','9921300001','о014оо777',1,0);
/*!40000 ALTER TABLE `Fines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FinesType`
--

DROP TABLE IF EXISTS `FinesType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `FinesType` (
  `TypeId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `FineValue` int(11) NOT NULL,
  PRIMARY KEY (`TypeId`),
  CONSTRAINT `finestype_chk_1` CHECK ((`FineValue` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FinesType`
--

LOCK TABLES `FinesType` WRITE;
/*!40000 ALTER TABLE `FinesType` DISABLE KEYS */;
INSERT INTO `FinesType` VALUES (1,'Превышение скорости на 20-40 км/ч',500),(2,'Превышение скорости на 40-60 км/ч',1000),(3,'Превышение скорости на 60-80 км/ч',1500),(4,'Превышение скорости на 80 км/ч',2500),(5,'Двойная сплошная',2500);
/*!40000 ALTER TABLE `FinesType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OwnerCarInt`
--

DROP TABLE IF EXISTS `OwnerCarInt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `OwnerCarInt` (
  `CarId` int(11) NOT NULL,
  `OwnerId` int(11) NOT NULL,
  UNIQUE KEY `CarId` (`CarId`),
  UNIQUE KEY `CarId_2` (`CarId`),
  KEY `ownerCarInt_ownerId_fk` (`OwnerId`),
  CONSTRAINT `ownerCarInt_carId_fk` FOREIGN KEY (`CarId`) REFERENCES `cars` (`CarId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ownerCarInt_ownerId_fk` FOREIGN KEY (`OwnerId`) REFERENCES `owners` (`OwnerId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OwnerCarInt`
--

LOCK TABLES `OwnerCarInt` WRITE;
/*!40000 ALTER TABLE `OwnerCarInt` DISABLE KEYS */;
INSERT INTO `OwnerCarInt` VALUES (2,2),(5,2),(14,2),(6,3),(7,4),(3,5),(4,5),(8,6),(9,7),(10,8),(11,9),(12,9),(13,9);
/*!40000 ALTER TABLE `OwnerCarInt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OwnerFineInt`
--

DROP TABLE IF EXISTS `OwnerFineInt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `OwnerFineInt` (
  `FineID` int(11) NOT NULL,
  `OwnerId` int(11) NOT NULL,
  KEY `ownerFineInt_fineId_fk` (`FineID`),
  KEY `OwnerId` (`OwnerId`),
  CONSTRAINT `ownerFineInt_fineId_fk` FOREIGN KEY (`FineID`) REFERENCES `fines` (`FineId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ownerfineint_ibfk_1` FOREIGN KEY (`OwnerId`) REFERENCES `owners` (`OwnerId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OwnerFineInt`
--

LOCK TABLES `OwnerFineInt` WRITE;
/*!40000 ALTER TABLE `OwnerFineInt` DISABLE KEYS */;
INSERT INTO `OwnerFineInt` VALUES (2,2);
/*!40000 ALTER TABLE `OwnerFineInt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Owners`
--

DROP TABLE IF EXISTS `Owners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Owners` (
  `OwnerId` int(11) NOT NULL AUTO_INCREMENT,
  `LastName` varchar(36) NOT NULL,
  `FirstName` varchar(36) NOT NULL,
  `MiddleName` varchar(36) NOT NULL,
  `Birthday` date NOT NULL,
  `City` varchar(36) NOT NULL,
  `DrivingLicenceNumber` varchar(10) NOT NULL,
  PRIMARY KEY (`OwnerId`),
  UNIQUE KEY `DrivingLicenceNumber` (`DrivingLicenceNumber`),
  CONSTRAINT `owner_fk` FOREIGN KEY (`DrivingLicenceNumber`) REFERENCES `drivinglicences` (`DrivingLicenceNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Owners`
--

LOCK TABLES `Owners` WRITE;
/*!40000 ALTER TABLE `Owners` DISABLE KEYS */;
INSERT INTO `Owners` VALUES (2,'Юсупов','Алексей','Олегович','1956-10-30','Ростов','9921300001'),(3,'Андреев','Георгий','Вадимович','1990-01-01','Зеленоград','9921300002'),(4,'Селезнев','Артур','Сергеевич','1944-04-03','Люберцы','9921300003'),(5,'Самойлов','Вадим','Павлович','1977-06-13','Москва','9921300004'),(6,'Вязьмин','Евгений','Альбертович','1943-04-14','Рязань','9921300005'),(7,'Алтуфьев','Артем','Павлович','1985-09-08','Рязань','9921300006'),(8,'Себастьянов','Сергей','Сергеевич','1991-10-18','Москва','9921300007'),(9,'Сазонов','Кирилл','Алексеевич','1986-05-12','Зеленоград','9921300009');
/*!40000 ALTER TABLE `Owners` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-04 19:11:51
