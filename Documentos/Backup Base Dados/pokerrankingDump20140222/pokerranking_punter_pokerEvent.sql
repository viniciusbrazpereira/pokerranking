CREATE DATABASE  IF NOT EXISTS `pokerranking` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `pokerranking`;
-- MySQL dump 10.13  Distrib 5.6.16, for osx10.7 (x86_64)
--
-- Host: 127.0.0.1    Database: pokerranking
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Table structure for table `punter_pokerEvent`
--

DROP TABLE IF EXISTS `punter_pokerEvent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `punter_pokerEvent` (
  `idPunter` int(11) NOT NULL,
  `idPokerEvent` int(11) NOT NULL,
  `countBuy` int(11) NOT NULL DEFAULT '0',
  `countRebuy` int(11) DEFAULT '0',
  `countAddon` int(11) DEFAULT '0',
  `prizeGain` double DEFAULT '0',
  `position` int(11) DEFAULT '0',
  PRIMARY KEY (`idPunter`,`idPokerEvent`),
  KEY `punter_foreign_idx` (`idPunter`),
  KEY `pokerEvent_foreign_idx` (`idPokerEvent`),
  CONSTRAINT `pokerEvent_foreign` FOREIGN KEY (`idPokerEvent`) REFERENCES `pokerEvent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `punter_foreign` FOREIGN KEY (`idPunter`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `punter_pokerEvent`
--

LOCK TABLES `punter_pokerEvent` WRITE;
/*!40000 ALTER TABLE `punter_pokerEvent` DISABLE KEYS */;
INSERT INTO `punter_pokerEvent` VALUES (2,1,1,2,1,0,4),(2,2,1,0,1,0,4),(3,1,1,0,0,0,3),(3,2,1,1,1,0,7),(4,1,1,1,1,54,2),(4,2,1,0,0,45,3),(5,1,1,5,1,0,5),(6,1,1,1,1,126,1),(6,2,1,3,1,67,2),(7,2,1,0,0,0,9),(8,2,1,1,0,0,11),(9,2,1,0,0,0,10),(10,2,1,3,1,0,8),(11,2,1,0,1,0,5),(12,2,1,2,1,112,1),(13,2,1,0,1,0,6);
/*!40000 ALTER TABLE `punter_pokerEvent` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-22  4:17:25
