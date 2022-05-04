CREATE DATABASE  IF NOT EXISTS `diariodiabete` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `diariodiabete`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: diariodiabete
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `misurazione`
--

DROP TABLE IF EXISTS `misurazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `misurazione` (
  `email` varchar(45) NOT NULL,
  `marcaTemporale` varchar(45) NOT NULL,
  `valoreGlicemia` int(11) DEFAULT NULL,
  `primadopo` varchar(45) NOT NULL,
  `pasto` varchar(45) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `misurazione`
--

LOCK TABLES `misurazione` WRITE;
/*!40000 ALTER TABLE `misurazione` DISABLE KEYS */;
INSERT INTO `misurazione` VALUES ('','2020-02-15',0,'Dopo','Pranzo',1),('pippomisurazione','2020-02-19',89,'Dopo','Colazione',7),('pippomisurazione','2020-02-19',56,'Prima','Colazione',8),('pippomisurazione','2020-02-19',70,'Dopo','Pranzo',10),('pippomisurazione','2020-02-19',250,'Prima','Cena',12),('claudiobiancu96@hotmail.it','2020-02-19',143,'Dopo','Colazione',13),('claudiobiancu96@hotmail.it','2020-02-19',81,'Prima','Colazione',14),('claudiobiancu96@hotmail.it','2020-02-19',110,'Prima','Pranzo',15);
/*!40000 ALTER TABLE `misurazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'diariodiabete'
--

--
-- Dumping routines for database 'diariodiabete'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-26 20:42:55
