CREATE DATABASE  IF NOT EXISTS `pokerranking` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `pokerranking`;
-- MySQL dump 10.13  Distrib 5.6.16, for osx10.7 (x86_64)
--
-- Host: localhost    Database: pokerranking
-- ------------------------------------------------------
-- Server version	5.6.12

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
-- Table structure for table `pokerEvent`
--

DROP TABLE IF EXISTS `pokerEvent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pokerranking`.`pokerEvent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `buy` double DEFAULT NULL,
  `rebuy` double DEFAULT NULL,
  `addon` double DEFAULT NULL,
  `dataInclusao` datetime NOT NULL,
  `dataEvent` datetime NOT NULL,
  `prizeWinner` double DEFAULT NULL,
  `dataInicio` datetime DEFAULT NULL,
  `dataFinal` datetime DEFAULT NULL,
  `prizeHome` double DEFAULT NULL,
  `prizeTotal` double DEFAULT NULL,
  `percentPrizeHome` double DEFAULT NULL,  
  `isPercentAddon` tinyint(1) DEFAULT NULL,
  `isPercentRebuyin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokerEvent`
--

LOCK TABLES `pokerEvent` WRITE;
/*!40000 ALTER TABLE `pokerEvent` DISABLE KEYS */;
INSERT INTO `pokerEvent` VALUES (1,'Primeiro Poker com Ranking',10,10,10,'2014-02-14 22:13:10','2014-02-14 22:00:00',180,'2014-02-14 22:16:15','2014-02-15 00:46:38',0,0,0,0,0);
/*!40000 ALTER TABLE `pokerEvent` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-15 12:43:02
