CREATE DATABASE  IF NOT EXISTS `purfumedb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `purfumedb`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: purfumedb
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authentication`
--

DROP TABLE IF EXISTS `authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authentication` (
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authentication`
--

LOCK TABLES `authentication` WRITE;
/*!40000 ALTER TABLE `authentication` DISABLE KEYS */;
INSERT INTO `authentication` VALUES ('123');
/*!40000 ALTER TABLE `authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bottle`
--

DROP TABLE IF EXISTS `bottle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bottle` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Quantity1` float NOT NULL DEFAULT '0',
  `Quantity2` float NOT NULL DEFAULT '0',
  `Unit_cost` varchar(50) NOT NULL DEFAULT '0',
  `Liquid_used_grams` double NOT NULL,
  `Reorder_quantity` double NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bottle`
--

LOCK TABLES `bottle` WRITE;
/*!40000 ALTER TABLE `bottle` DISABLE KEYS */;
INSERT INTO `bottle` VALUES (1,'3ml',15,0,'0.83',3,3),(3,'3.5ml',14,0,'1.5',3,3),(4,'30ml',35,0,'2.5',6,10),(5,'50ml',17,0,'3.25',12,5),(6,'100ml',5,0,'4.5',22,3),(7,'Pen',5,0,'4.5',3,3),(8,'Pocket',2,0,'9.0',3,3),(9,'50ml (Special)',7,0,'12.5',12,5),(10,'4ml',6,0,'5',4,1),(11,'6ml',2,0,'4.17',6,1);
/*!40000 ALTER TABLE `bottle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bottles_flavors`
--

DROP TABLE IF EXISTS `bottles_flavors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bottles_flavors` (
  `BottleID` int(11) DEFAULT NULL,
  `FlavorID` int(11) DEFAULT NULL,
  `Used_grams` double DEFAULT NULL,
  KEY `BottleID` (`BottleID`),
  KEY `FlavorID` (`FlavorID`),
  CONSTRAINT `Bottles_Flavors_ibfk_1` FOREIGN KEY (`BottleID`) REFERENCES `bottle` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `Bottles_Flavors_ibfk_2` FOREIGN KEY (`FlavorID`) REFERENCES `flavor` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bottles_flavors`
--

LOCK TABLES `bottles_flavors` WRITE;
/*!40000 ALTER TABLE `bottles_flavors` DISABLE KEYS */;
INSERT INTO `bottles_flavors` VALUES (1,1,0),(1,2,0),(3,1,0),(3,2,0),(4,1,22.5),(4,2,1),(5,1,36.5),(5,2,4),(6,1,76.5),(6,2,7),(7,1,3),(7,2,1),(8,1,4),(8,2,1),(9,1,36.5),(9,2,1),(10,1,0),(10,2,0),(11,1,0),(11,2,0);
/*!40000 ALTER TABLE `bottles_flavors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flavor`
--

DROP TABLE IF EXISTS `flavor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flavor` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Quantity1` float NOT NULL DEFAULT '0',
  `Quantity2` float NOT NULL DEFAULT '0',
  `Unit_cost` varchar(50) NOT NULL DEFAULT '0',
  `Reorder_quantity` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flavor`
--

LOCK TABLES `flavor` WRITE;
/*!40000 ALTER TABLE `flavor` DISABLE KEYS */;
INSERT INTO `flavor` VALUES (1,'Alcohol',1146,0,'0.01',0),(2,'Reinforced',58,0,'0.24',0);
/*!40000 ALTER TABLE `flavor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liquid`
--

DROP TABLE IF EXISTS `liquid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `liquid` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Category` varchar(50) NOT NULL,
  `Quality` int(11) NOT NULL,
  `Quantity1` float NOT NULL DEFAULT '0',
  `Quantity2` float NOT NULL DEFAULT '0',
  `Unit_cost` varchar(50) NOT NULL DEFAULT '0',
  `Reorder_quantity` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liquid`
--

LOCK TABLES `liquid` WRITE;
/*!40000 ALTER TABLE `liquid` DISABLE KEYS */;
INSERT INTO `liquid` VALUES (1,'Viagra','female','western',1000,66,0,'1.0',0),(4,'Hugo','Male','Western',1000,94,0,'1.55',0),(5,'Dunhill Desire','male','western',1000,79,0,'1.55',0),(6,'Insense Ultramarine','Male','Western',1000,36,0,'1.75',0),(7,'Pi Givenchy','Male ','Western',1000,69,0,'1.4',0),(8,'Ted Lapidus','Male','Western',1000,40,0,'1.5',0),(9,'Eternity','Male','Western',1000,50,0,'1.4',0),(10,'Lacoste Black\"Intense\"','Male','Western',1000,129,0,'1.52',0),(11,'Black XS LEXCES','Male','Western',1000,57,0,'1.45',0),(12,'3G','Male','Western',1000,65,0,'1.4',0),(13,'Dior Sauvage','Male','Wester',1000,97,0,'1.35',0),(14,'Silver Scent','Male','Western',1000,43,0,'1.1',0),(15,'Lacoste White \"Pure\"','Male','Western',1000,23,30,'1.45,1.5',0),(16,'Sculpture','Male','Western',1000,3,100,'1.45,1.65',0),(17,'BMW','Male','Western',1000,182,0,'1.25',0),(18,'Invictus','Male','Western',1000,85,0,'1.45',0),(19,'Puma Jam','Male ','Western',1000,161,0,'1.15',0),(20,'Chrome Legend','Male ','Western',1000,52,0,'1.55',0),(21,'Pleasures','Male','Western',1000,17,0,'2.0',0),(22,'Acqua Di Gio','Male ','Western',1000,33,0,'1.25',0),(23,'Sultan El 3otor','Male ','Eastern',2000,60,0,'3.7',0),(24,'X Chocolate','Male','Western',1000,14,0,'1.75',0),(25,'Dunhill Fresh','Male','Western',1000,49,0,'1.4',0),(26,'3od Sultan','Male ','Eastern',2000,19,50,'2.65,1.59',0),(27,'Herrera 212 Sexy','Male','Western',1000,42,0,'1.45',0),(28,'Lacoste Magnetic','Male ','Western',1000,11,0,'1.45',0),(29,'Black dress','Female','Western',1000,37,0,'1.0',0),(30,'Jadore Dior','Female','Western',1000,41,0,'1.5',0),(31,'Britney Spears','Female','Western',1000,63,0,'1.35',0),(32,'INDRA','Female','western',1000,4,0,'2.0',0),(33,'8aram','Female','Eastern',1000,57,0,'1.3',0),(34,'Kenzo','Female','Western',1000,35,0,'1.45',0),(35,'Bonbon','Female','western',1000,64,0,'1.4',0),(36,'Paris','Female','Western',1000,32,0,'1.35',0),(37,'VIP 212','Female','western',1000,30,0,'1.4',0),(38,'Hypnotic Poison Dior','Female','Eastern',1000,25,0,'1.65',0),(58,'Sexy Little Thing Victoria','Female','Western',1000,80,0,'1.2',10),(59,'Sexy Graffiti Escada','Female','Western',1000,66,0,'1.3',10),(60,'My Love Apple','Female','Western',1000,28,0,'1.05',10),(61,'Mercedes ','Male','Western',1000,19,0,'1.1',10),(62,'Lacoste Essential','Male','Western',1000,86,0,'1.4',15),(63,'Gucci Rush','Female','Western',1000,41,0,'1.6',10),(64,'Montana','Female','Western',1000,20,0,'1.0',10),(65,'S.T.Dupont','Female','Western',1000,21,0,'1.8',10),(70,'David Beckham','Male','Western',1000,15,30,'1.35,1.45',10),(71,'Joop Jump','Male','Western',1000,2,50,'1.3,1.4',10),(72,'Boss','Male','Western',1000,64,0,'1.45',10),(73,'Cool Water Dvidoff','Male','Western',1000,49,0,'1.45',10),(74,'Body Burberry','Female','Western',1000,28,0,'2.4',10),(75,'Mix 3od','Male','Eastern',1000,16,0,'1.2',5),(76,'MidNight Fantasy','Female','Western',1000,47,0,'1.4',5),(77,'Mask Tahara','Female','Eastern',1000,24,0,'1.5',5),(78,'Davidoff','Male','Western',1000,24,0,'1.7',5),(79,'Drakkar','Male','Western',1000,13,0,'1.55',7),(80,'Fahrenheit','Male','Western',1000,31,0,'1.5',7),(81,'One Man Show','male','Western',1000,86,0,'1.4',8),(82,'COCO CHANEL','Female','western',1000,50,0,'1',10);
/*!40000 ALTER TABLE `liquid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufactured_bottle`
--

DROP TABLE IF EXISTS `manufactured_bottle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manufactured_bottle` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Cost` float DEFAULT NULL,
  `Selling_price` float DEFAULT NULL,
  `LiquidID` int(11) DEFAULT NULL,
  `BottleID` int(11) DEFAULT NULL,
  `Used_Grams` double DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `Selling_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `LiquidID` (`LiquidID`),
  KEY `BottleID` (`BottleID`),
  CONSTRAINT `Manufactured_bottle_ibfk_1` FOREIGN KEY (`LiquidID`) REFERENCES `liquid` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `Manufactured_bottle_ibfk_2` FOREIGN KEY (`BottleID`) REFERENCES `bottle` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufactured_bottle`
--

LOCK TABLES `manufactured_bottle` WRITE;
/*!40000 ALTER TABLE `manufactured_bottle` DISABLE KEYS */;
INSERT INTO `manufactured_bottle` VALUES (1,42.155,100,62,6,31,'Normal Bottle','23/10/2018'),(2,13.08,20,58,8,3,'Normal Bottle','23/10/2018'),(3,11.93,15,23,1,3,'Normal Bottle','23/10/2018'),(4,11.93,15,23,1,3,'Normal Bottle','23/10/2018'),(5,50.105,90,8,6,29,'Normal Bottle','26/10/2018'),(6,37.555,73,30,5,16,'Normal Bottle','26/10/2018'),(7,4.53,10,33,1,3,'Normal Bottle','26/10/2018'),(8,5.03,10,37,1,3,'Normal Bottle','26/10/2018'),(9,4.73,10,59,1,3,'Normal Bottle','26/10/2018'),(10,8.88,15,26,1,3,'Normal Bottle','26/10/2018'),(11,8.88,15,26,1,3,'Normal Bottle','26/10/2018'),(12,8.88,15,26,1,3,'Normal Bottle','26/10/2018'),(13,25.555,50,19,5,18,'Normal Bottle','04/11/2018'),(14,17.315,35,58,4,11,'Normal Bottle','04/11/2018'),(15,25.255,60,58,5,17,'Normal Bottle','04/11/2018'),(16,28.855,60,30,5,16,'Normal Bottle','04/11/2018'),(17,27.805,60,70,5,17,'Normal Bottle','04/11/2018'),(18,31.205,60,20,5,17,'Normal Bottle','04/11/2018'),(19,15.815,25,16,4,8,'Normal Bottle','04/11/2018'),(20,21.265,35,4,4,11,'Normal Bottle','04/11/2018'),(21,16.315,35,14,4,11,'Normal Bottle','04/11/2018'),(22,25.415,35,1,4,17,'Normal Bottle','04/11/2018'),(23,16.715,35,17,4,10,'Normal Bottle','04/11/2018'),(24,20.715,35,30,4,11,'Normal Bottle','04/11/2018'),(25,17.415,35,58,4,11,'Normal Bottle','04/11/2018'),(26,24.215,35,1,4,16,'Normal Bottle','04/11/2018'),(27,21.265,35,20,4,11,'Normal Bottle','04/11/2018'),(28,20.165,35,16,4,11,'Normal Bottle','04/11/2018'),(29,20.165,35,16,4,11,'Normal Bottle','04/11/2018'),(30,30.455,50,10,5,16,'Normal Bottle','09/11/2018'),(31,21.115,35,71,4,13,'Normal Bottle','09/11/2018'),(32,5.515,10,18,4,3,'Normal Bottle','09/11/2018'),(33,18.765,35,16,4,4,'Mix (7.0 g of BMW)','16/11/2018'),(34,18.765,35,16,4,4,'Mix (7.0 g of BMW)','16/11/2018'),(35,42.205,65,10,9,16,'Normal Bottle','16/11/2018'),(36,39.005,65,7,9,16,'Normal Bottle','16/11/2018'),(37,42.955,65,20,9,17,'Normal Bottle','16/11/2018'),(38,35.005,65,19,9,16,'Normal Bottle','16/11/2018'),(39,39.805,65,16,9,16,'Normal Bottle','16/11/2018'),(40,36.605,65,17,9,16,'Normal Bottle','16/11/2018'),(41,17.415,35,58,4,11,'Normal Bottle','18/11/2018'),(42,22.005,50,72,5,10,'Mix (8.0 g of Silver Scent)','20/11/2018'),(43,49.905,60,26,5,17,'Normal Bottle','14/12/2018'),(44,50.005,90,8,6,29,'Normal Bottle','14/12/2018'),(45,53.005,90,8,6,31,'Normal Bottle','14/12/2018'),(46,46.805,100,62,6,32,'Normal Bottle','24/12/2018'),(47,17.815,25,78,4,8,'Normal Bottle','24/12/2018'),(48,18.965,35,16,4,5,'Mix (6.0 g of BMW)','24/12/2018'),(49,20.165,35,72,4,11,'Normal Bottle','24/12/2018'),(50,32.755,60,20,5,18,'Normal Bottle','24/12/2018'),(51,31.205,60,20,5,17,'Normal Bottle','24/12/2018'),(52,29.005,60,19,5,21,'Normal Bottle','24/12/2018'),(53,32.055,50,10,5,17,'Normal Bottle','24/12/2018'),(54,49.905,60,26,5,17,'Normal Bottle','24/12/2018'),(55,28.655,50,35,5,17,'Normal Bottle','24/12/2018'),(56,49.905,60,26,5,17,'Normal Bottle','24/12/2018'),(57,23.415,35,10,4,12,'Normal Bottle','24/12/2018'),(58,18.965,35,17,4,12,'Normal Bottle','24/12/2018'),(59,30.055,60,7,5,18,'Normal Bottle','24/12/2018'),(60,21.565,35,10,4,11,'Normal Bottle','24/12/2018'),(61,23.465,35,80,4,13,'Normal Bottle','24/12/2018'),(62,39.605,63,30,9,17,'Normal Bottle','24/12/2018'),(63,40.455,63,5,9,17,'Normal Bottle','24/12/2018'),(64,18.065,35,58,4,12,'Normal Bottle','24/12/2018'),(65,18.565,35,58,4,12,'Normal Bottle','24/12/2018'),(66,31.205,50,79,5,17,'Normal Bottle','24/12/2018'),(67,54.505,90,10,6,30,'Normal Bottle','24/12/2018'),(68,21.565,35,10,4,11,'Normal Bottle','24/12/2018'),(69,21.565,35,10,4,11,'Normal Bottle','24/12/2018'),(70,4.48,10,58,1,3,'Normal Bottle','24/12/2018'),(71,5.03,10,76,1,3,'Normal Bottle','24/12/2018'),(72,25.305,60,19,5,18,'Normal Bottle','24/12/2018'),(73,16.615,35,19,4,11,'Normal Bottle','24/12/2018'),(74,17.315,35,58,4,11,'Normal Bottle','24/12/2018'),(75,31.155,50,20,5,17,'Normal Bottle','24/12/2018'),(76,35.205,50,78,5,18,'Normal Bottle','24/12/2018'),(77,18.565,35,58,4,12,'Normal Bottle','24/12/2018'),(78,16.315,25,36,4,9,'Normal Bottle','24/12/2018'),(79,2.03,0,75,1,1,'Normal Bottle','24/12/2018'),(80,25.255,50,58,5,17,'Normal Bottle','24/12/2018'),(81,13.17,20,77,11,6,'Normal Bottle','24/12/2018'),(82,10.8,17,34,10,4,'Normal Bottle','24/12/2018'),(83,11.6,17,38,10,4,'Normal Bottle','24/12/2018'),(84,10.4,17,36,10,4,'Normal Bottle','24/12/2018'),(85,28.705,60,17,5,10,'Mix (8.0 g of Sculpture)','24/12/2018'),(86,16.015,25,17,4,5,'Mix (4.0 g of Sculpture)','24/12/2018'),(87,19.965,35,17,4,7,'Mix (5.0 g of Sculpture)','24/12/2018'),(88,11,17,80,10,4,'Normal Bottle','24/12/2018'),(89,8.95,15,26,3,3,'Normal Bottle','24/12/2018'),(90,51.055,100,17,6,18,'Mix (15.0 g of Sculpture)','24/12/2018'),(91,21.415,35,17,4,7,'Mix (6.0 g of Sculpture)','24/12/2018'),(92,25.525,50,19,5,18,'Normal Bottle','07/01/2019'),(93,19.165,35,1,4,15,'Normal Bottle','07/01/2019'),(94,16.115,35,58,4,10,'Normal Bottle','07/01/2019'),(95,20.165,35,16,4,5,'Mix (7.0 g of BMW)','07/01/2019'),(96,20.165,35,16,4,5,'Mix (7.0 g of BMW)','07/01/2019'),(97,25.515,35,16,4,8,'Mix (7.0 g of BMW)','09/01/2019'),(98,14.965,25,16,4,4,'Mix (4.0 g of BMW)','09/01/2019'),(99,25.525,50,19,5,18,'Normal Bottle','09/01/2019'),(100,32.025,50,73,5,18,'Normal Bottle','09/01/2019'),(101,51.445,90,8,6,29,'Normal Bottle','20/01/2019'),(102,33.225,60,20,5,18,'Normal Bottle','20/01/2019'),(103,32.325,60,30,5,18,'Normal Bottle','20/01/2019'),(104,47.045,100,19,6,34,'Normal Bottle','20/01/2019'),(105,27.175,60,19,5,19,'Normal Bottle','20/01/2019'),(106,20.765,35,81,4,12,'Normal Bottle','20/01/2019'),(107,20.765,35,81,4,12,'Normal Bottle','20/01/2019'),(108,21.965,35,80,4,12,'Normal Bottle','20/01/2019'),(109,5.58,10,17,1,3,'Normal Bottle','27/01/2019'),(110,19.965,35,17,4,7,'Mix (5.0 g of Sculpture)','27/01/2019'),(111,18.965,35,17,4,12,'Normal Bottle','27/01/2019'),(112,22.265,35,5,4,5,'Mix (9.0 g of Invictus)','30/01/2019'),(113,16.465,35,17,4,12,'Normal Bottle','30/01/2019'),(114,14.665,35,14,4,12,'Normal Bottle','30/01/2019'),(115,19.965,35,17,4,7,'Mix (5.0 g of Sculpture)','06/02/2019'),(116,30.225,50,5,5,18,'Normal Bottle','06/02/2019'),(117,29.625,60,70,5,18,'Normal Bottle','06/02/2019'),(118,32.875,60,15,5,19,'Normal Bottle','06/02/2019'),(119,30.975,60,36,5,19,'Normal Bottle','06/02/2019'),(120,6.33,0,8,1,3,'Normal Bottle','06/02/2019'),(121,20.215,35,17,4,13,'Normal Bottle','06/02/2019'),(122,27.825,50,17,5,18,'Normal Bottle','06/02/2019'),(123,22.925,35,10,4,12,'Normal Bottle','06/02/2019'),(124,32.685,50,10,5,18,'Normal Bottle','06/02/2019'),(125,19.565,35,75,4,13,'Normal Bottle','06/02/2019'),(126,22.465,35,73,4,12,'Normal Bottle','06/02/2019'),(127,28.075,50,19,5,10,'Mix (9.0 g of BMW)','06/02/2019'),(128,21.265,35,38,4,12,'Normal Bottle','17/02/2019'),(129,33.225,60,20,5,18,'Normal Bottle','17/02/2019'),(130,32.875,50,11,5,19,'Normal Bottle','17/02/2019'),(131,28.125,50,58,5,19,'Normal Bottle','17/02/2019'),(132,34.375,50,63,5,18,'Normal Bottle','17/02/2019'),(133,30.775,50,37,5,18,'Normal Bottle','17/02/2019'),(134,52.945,90,8,6,30,'Normal Bottle','17/02/2019'),(135,17.015,25,16,4,9,'Normal Bottle','17/02/2019'),(136,55.925,60,26,5,19,'Normal Bottle','17/02/2019');
/*!40000 ALTER TABLE `manufactured_bottle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packing_objects`
--

DROP TABLE IF EXISTS `packing_objects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `packing_objects` (
  `Cost` double NOT NULL,
  `Quantity` int(11) NOT NULL DEFAULT '0',
  `Other_costs` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packing_objects`
--

LOCK TABLES `packing_objects` WRITE;
/*!40000 ALTER TABLE `packing_objects` DISABLE KEYS */;
INSERT INTO `packing_objects` VALUES (0.5,-17,0.5);
/*!40000 ALTER TABLE `packing_objects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_transaction`
--

DROP TABLE IF EXISTS `purchase_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_transaction` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Item` varchar(50) NOT NULL,
  `Description` varchar(150) NOT NULL,
  `Quantity` float NOT NULL,
  `Cost` float NOT NULL,
  `Date` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_transaction`
--

LOCK TABLES `purchase_transaction` WRITE;
/*!40000 ALTER TABLE `purchase_transaction` DISABLE KEYS */;
INSERT INTO `purchase_transaction` VALUES (1,'Insense Ultramarine','liquid',36,63,'19/10/2018'),(2,'Pi Givenchy','liquid',53,74.2,'19/10/2018'),(3,'Dunhill Desire','liquid',69,106.95,'19/10/2018'),(4,'Hugo','liquid',75,116.25,'19/10/2018'),(5,'Ted Lapidus','liquid',91,136.5,'19/10/2018'),(6,'Eternity','liquid',50,70,'19/10/2018'),(7,'Lacoste Black\"Intense\"','liquid',33,52.8,'19/10/2018'),(8,'Black XS LEXCES','liquid',26,37.7,'19/10/2018'),(9,'3G','liquid',65,91,'19/10/2018'),(10,'Viagra','liquid',84,84,'19/10/2018'),(11,'Dior Sauvage','liquid',47,63.45,'19/10/2018'),(12,'Silver Scent','liquid',74,81.4,'19/10/2018'),(13,'Lacoste White \"Pure\"','liquid',42,60.9,'19/10/2018'),(14,'Sculpture','liquid',71,102.95,'19/10/2018'),(15,'BMW','liquid',93,116.25,'19/10/2018'),(16,'Invictus','liquid',67,97.15,'19/10/2018'),(17,'Puma Jam','liquid',44,50.6,'19/10/2018'),(18,'Chrome Legend','liquid',105,162.75,'19/10/2018'),(19,'Pleasures','liquid',17,34,'19/10/2018'),(20,'X Chocolate','liquid',14,24.5,'19/10/2018'),(21,'Acqua Di Gio','liquid',33,41.25,'19/10/2018'),(22,'3od Sultan','liquid',21,55.65,'19/10/2018'),(23,'Herrera 212 Sexy','liquid',42,60.9,'19/10/2018'),(24,'Sultan El 3otor','liquid',36,133.2,'19/10/2018'),(25,'Dunhill Fresh','liquid',49,68.6,'19/10/2018'),(26,'Lacoste Essential','liquid',49,56.35,'19/10/2018'),(27,'Mercedes ','liquid',19,20.9,'19/10/2018'),(28,'Lacoste Magnetic','liquid',11,15.95,'19/10/2018'),(29,'Black dress','liquid',37,37,'19/10/2018'),(30,'Jadore Dior','liquid',49,73.5,'19/10/2018'),(31,'Britney Spears','liquid',63,85.05,'19/10/2018'),(32,'INDRA','liquid',4,8,'19/10/2018'),(33,'8aram','liquid',60,78,'19/10/2018'),(34,'Kenzo','liquid',39,56.55,'19/10/2018'),(35,'Bonbon','liquid',51,71.4,'19/10/2018'),(36,'Paris','liquid',64,86.4,'19/10/2018'),(37,'VIP 212','liquid',51,71.4,'19/10/2018'),(38,'Hypnotic Poison Dior','liquid',11,18.15,'19/10/2018'),(39,'Sexy Little Thing Victoria','liquid',59,70.8,'19/10/2018'),(40,'Montana','liquid',20,20,'19/10/2018'),(41,'S.T.Dupont','liquid',21,37.8,'19/10/2018'),(42,'My Love Apple','liquid',28,29.4,'19/10/2018'),(43,'Gucci Rush','liquid',59,94.4,'19/10/2018'),(44,'Sexy Graffiti Escada','liquid',69,89.7,'19/10/2018'),(45,'Alcohol','flavor',5000,45,'19/10/2018'),(46,'Reinforced','flavor',250,60,'19/10/2018'),(47,'3 ml','bottle',28,23.24,'19/10/2018'),(48,'3.5 ml','bottle',14,21,'19/10/2018'),(49,'30 ml','bottle',20,55,'19/10/2018'),(50,'50 ml','bottle',17,55.25,'19/10/2018'),(51,'100 ml','bottle',5,22.5,'19/10/2018'),(52,'Pen','bottle',5,22.5,'19/10/2018'),(53,'Pocket','bottle',3,27,'19/10/2018'),(54,'Other','bottle',1,10,'19/10/2018'),(55,'David Beckham','new item',50,67.5,'27/10/2018'),(57,'Joop Jump','new item',15,19.5,'27/10/2018'),(58,'Boss','new item',35,50.75,'27/10/2018'),(59,'Cool Water','new item',29,44.95,'27/10/2018'),(60,'Body Burberry','new item',28,67.2,'27/10/2018'),(62,'50ml (Special)','bottle',6,90,'16/11/2018'),(63,'3od Sultan','liquid',10,26.5,'14/12/2018'),(64,'Other','Sticker',5,5,'24/12/2018'),(65,'Other','Sticker 2',2,3,'24/12/2018'),(66,'BMW','liquid',100,125,'24/12/2018'),(67,'Sexy Little Thing Victoria','liquid',70,80.5,'24/12/2018'),(68,'Mix 3od','new item',30,36,'24/12/2018'),(69,'Puma Jam','liquid',100,115,'24/12/2018'),(70,'Black XS LEXCES','liquid',50,72.5,'24/12/2018'),(71,'Boss','liquid',50,72.5,'24/12/2018'),(72,'Ted Lapidus','liquid',70,105,'24/12/2018'),(73,'3od Sultan','liquid',70,185.5,'24/12/2018'),(74,'Joop Jump','liquid',50,70,'24/12/2018'),(75,'Lacoste Black\"Intense\"','liquid',100,160,'24/12/2018'),(76,'Sculpture','liquid',70,101.5,'24/12/2018'),(77,'Lacoste Essential','liquid',100,140,'24/12/2018'),(78,'MidNight Fantasy','new item',50,70,'24/12/2018'),(79,'Jadore Dior','liquid',70,105,'24/12/2018'),(80,'Hypnotic Poison Dior','liquid',30,49.5,'24/12/2018'),(81,'Mask Tahara','new item',30,45,'24/12/2018'),(82,'Davidoff','new item',50,85,'24/12/2018'),(83,'Chrome Legend','liquid',30,46.5,'24/12/2018'),(84,'Dior Sauvage','liquid',50,67.5,'24/12/2018'),(85,'Dunhill Desire','liquid',50,77.5,'24/12/2018'),(86,'4ml','bottle',5,25,'24/12/2018'),(87,'Viagra','liquid',30,30,'24/12/2018'),(88,'50ml (Special)','bottle',2,25,'24/12/2018'),(89,'6ml','bottle',3,12.5,'24/12/2018'),(90,'30ml','bottle',25,62.5,'24/12/2018'),(91,'50ml','bottle',20,60,'24/12/2018'),(92,'100ml','bottle',5,22.5,'24/12/2018'),(93,'4ml','bottle',5,25,'24/12/2018'),(94,'Drakkar','new item',30,46.5,'24/12/2018'),(95,'Fahrenheit','new item',30,45,'24/12/2018'),(96,'Other','Packing',100,27,'07/01/2019'),(97,'BMW','liquid',100,125,'07/01/2019'),(98,'Puma Jam','liquid',100,115,'07/01/2019'),(99,'Sexy Little Thing Victoria','liquid',100,120,'07/01/2019'),(100,'Sculpture','liquid',100,165,'07/01/2019'),(101,'Chrome Legend','liquid',50,77.5,'07/01/2019'),(102,'Pi Givenchy','liquid',50,70,'07/01/2019'),(103,'One Man Show','new item',30,42,'07/01/2019'),(104,'3od Sultan','liquid',50,79.5,'07/01/2019'),(105,'Sultan El 3otor','liquid',30,111,'07/01/2019'),(106,'Ted Lapidus','liquid',30,45,'07/01/2019'),(107,'Invictus','liquid',30,43.5,'07/01/2019'),(108,'Hugo','liquid',30,46.5,'07/01/2019'),(109,'Bonbon','liquid',30,42,'07/01/2019'),(110,'30ml','bottle',41,102.5,'27/01/2019'),(111,'50ml','bottle',20,65,'27/01/2019'),(112,'100ml','bottle',5,22.5,'27/01/2019'),(113,'Fahrenheit','liquid',30,45,'02/02/2019'),(114,'One Man Show','liquid',30,42,'02/02/2019'),(115,'David Beckham','liquid',30,43.5,'02/02/2019'),(116,'Cool Water Dvidoff','liquid',50,72.5,'02/02/2019'),(117,'Lacoste White \"Pure\"','liquid',30,45,'02/02/2019'),(118,'Lacoste Black\"Intense\"','liquid',150,228,'02/02/2019'),(119,'BMW','liquid',100,125,'02/02/2019'),(120,'Puma Jam','liquid',100,115,'02/02/2019'),(121,'50ml (Special)','bottle',7,87.5,'02/02/2019'),(122,'One Man Show','liquid',50,70,'20/02/2019'),(124,'COCO CHANEL','new item',50,50,'20/02/2019');
/*!40000 ALTER TABLE `purchase_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'purfumedb'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_new_quantity` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_new_quantity`(IN `item_name` VARCHAR(50), IN `description` VARCHAR(150), IN `item_quantity` FLOAT, IN `item_cost` FLOAT, IN `transaction_date` VARCHAR(60), IN `material_name` VARCHAR(50), IN `materialID` INT, IN `material_quantity1` FLOAT, IN `material_quantity2` FLOAT, IN `material_unit_cost` VARCHAR(50))
BEGIN

insert into Purchase_transaction(Item,Description,Quantity,Cost,Date) values(item_name,description,item_quantity,item_cost,transaction_date);

SET @query = CONCAT("update ",material_name," set Quantity1 = ",material_quantity1,", Quantity2 = ",material_quantity2,", Unit_cost = '" , material_unit_cost,"' where ID =",materialID);
 PREPARE stmt FROM @query;
 EXECUTE stmt;
 DEALLOCATE PREPARE stmt;
 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_new_bottle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_new_bottle`(IN `name` VARCHAR(100), IN `quantity` FLOAT, IN `unit_cost` VARCHAR(50), IN `reorder_quantity` FLOAT, IN `liquid_used_grams` FLOAT, IN `alcahol_used_grams` FLOAT, IN `reinforcement_used_grams` FLOAT, IN `insert_date` VARCHAR(50), inout `bottleID` int)
BEGIN

set @ID = -1;

insert into Bottle (Name,Quantity1,Unit_cost,Liquid_used_grams,Reorder_Quantity) values(name,quantity,unit_cost,liquid_used_grams,reorder_quantity);

select @ID := max(ID) from Bottle;

insert into bottles_flavors values(@ID,1,alcahol_used_grams);
insert into bottles_flavors values(@ID,2,reinforcement_used_grams);

insert into Purchase_transaction (item,description,quantity,Cost,Date) values(name,'new bottle',quantity,quantity*unit_cost,insert_date);

set bottleID = @ID;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_new_liquid` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_new_liquid`(IN `name` VARCHAR(100), IN `type` VARCHAR(50), IN `category` VARCHAR(50), IN `quality` INT, IN `quantity` FLOAT, IN `gram_cost` VARCHAR(50), IN `reorder_quantity` FLOAT, IN `insert_date` VARCHAR(50))
BEGIN

insert into Liquid (Name,Type,Category,Quality,Quantity1,Unit_cost,Reorder_Quantity) values(name,type,category,quality,quantity,gram_cost,reorder_quantity);

insert into Purchase_transaction (item,description,quantity,Cost,Date) values(name,'new item',quantity,quantity*gram_cost,insert_date);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `manufacture_mix_bottle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `manufacture_mix_bottle`(IN `cost` DOUBLE, IN `selling_price` DOUBLE, IN `liquidID` INT, IN `bottleID` INT, IN `liquid_used_grams` DOUBLE, IN `bottle_description` VARCHAR(100), IN `selling_date` VARCHAR(50), IN `liquid_quantity1` FLOAT, IN `liquid_quantity2` FLOAT, IN `liquid_unit_cost` VARCHAR(50), IN `bottle_quantity1` FLOAT, IN `bottle_quantity2` FLOAT, IN `bottle_unit_cost` VARCHAR(50), IN `alcahol_quantity1` FLOAT, IN `alcahol_quantity2` FLOAT, IN `alcahol_unit_cost` VARCHAR(50), IN `reinforcement_quantity1` FLOAT, IN `reinforcement_quantity2` FLOAT, IN `reinforcement_unit_cost` VARCHAR(50), IN `mix_liquidID` INT, IN `mix_liquid_quantity1` FLOAT, IN `mix_liquid_quantity2` FLOAT, IN `mix_liquid_unit_cost` VARCHAR(50))
    NO SQL
BEGIN

  insert into Manufactured_bottle  
 (Cost,Selling_price,LiquidID,BottleID,Used_grams,Description,Selling_date)
  values(cost,selling_price,liquidID,bottleID,liquid_used_grams,bottle_description,selling_date);
  
  update Liquid set Quantity1 = liquid_quantity1, Quantity2 = liquid_quantity2, Unit_cost = liquid_unit_cost where ID = liquidID;
  
  update Liquid set Quantity1 = mix_liquid_quantity1, Quantity2 = mix_liquid_quantity2, Unit_cost = mix_liquid_unit_cost where ID = mix_liquidID;
  
  update Bottle set Quantity1 = bottle_quantity1, Quantity2 = bottle_quantity2, Unit_cost = bottle_unit_cost where ID = bottleID;
  
  update Flavor set Quantity1 = alcahol_quantity1, Quantity2 = alcahol_quantity2, Unit_cost = alcahol_unit_cost where ID = 1;
  
  update Flavor set Quantity1 = reinforcement_quantity1, Quantity2 = reinforcement_quantity2, Unit_cost = reinforcement_unit_cost where ID = 2;
  
  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `manufacture_new_bottle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `manufacture_new_bottle`(IN `cost` DOUBLE, IN `selling_price` DOUBLE, IN `liquidID` INT, IN `bottleID` INT, IN `liquid_used_grams` DOUBLE, IN `bottle_description` VARCHAR(100), IN `selling_date` VARCHAR(50), IN `liquid_quantity1` FLOAT, IN `liquid_quantity2` FLOAT, IN `liquid_unit_cost` VARCHAR(50), IN `bottle_quantity1` FLOAT, IN `bottle_quantity2` FLOAT, IN `bottle_unit_cost` VARCHAR(50), IN `alcahol_quantity1` FLOAT, IN `alcahol_quantity2` FLOAT, IN `alcahol_unit_cost` VARCHAR(50), IN `reinforcement_quantity1` FLOAT, IN `reinforcement_quantity2` FLOAT, IN `reinforcement_unit_cost` VARCHAR(50))
BEGIN

  insert into Manufactured_bottle  
 (Cost,Selling_price,LiquidID,BottleID,Used_grams,Description,Selling_date)
  values(cost,selling_price,liquidID,bottleID,liquid_used_grams,bottle_description,selling_date);
  
  update Liquid set Quantity1 = liquid_quantity1, Quantity2 = liquid_quantity2, Unit_cost = liquid_unit_cost where ID = liquidID;
  
  update Bottle set Quantity1 = bottle_quantity1, Quantity2 = bottle_quantity2, Unit_cost = bottle_unit_cost where ID = bottleID;
  
  update Flavor set Quantity1 = alcahol_quantity1, Quantity2 = alcahol_quantity2, Unit_cost = alcahol_unit_cost where ID = 1;
  
  update Flavor set Quantity1 = reinforcement_quantity1, Quantity2 = reinforcement_quantity2, Unit_cost = reinforcement_unit_cost where ID = 2;
  
  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `save_new_bottle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `save_new_bottle`(IN `name` VARCHAR(100), IN `reorder_quantity` FLOAT, IN `liquid_used_grams` FLOAT, IN `alcahol_used_grams` FLOAT, IN `reinforcement_used_grams` FLOAT, inout `bottleID` int)
BEGIN

set @ID = -1;

insert into Bottle (Name,Liquid_used_grams,Reorder_Quantity) values(name,liquid_used_grams,reorder_quantity);

select @ID := max(ID) from Bottle;

insert into bottles_flavors values(@ID,1,alcahol_used_grams);
insert into bottles_flavors values(@ID,2,reinforcement_used_grams);

set bottleID = @ID;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_bottle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_bottle`(IN `bID` INT, IN `name` varchar(50), IN `liquid_used_grams` double, IN `alcahol_used_grams` double, IN `reinforcement_used_grams` double, IN `reorder_quantity` double)
BEGIN

	update bottle set Name = name , Liquid_used_grams = liquid_used_grams,
    Reorder_quantity = reorder_quantity where ID = bID;
    
    update bottles_flavors set Used_grams = alcahol_used_grams where BottleID = bID and FlavorID = 1;
    update Bottles_Flavors set Used_grams = reinforcement_used_grams where BottleID = bID and FlavorID = 2;

    
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

-- Dump completed on 2019-02-21 16:30:36
