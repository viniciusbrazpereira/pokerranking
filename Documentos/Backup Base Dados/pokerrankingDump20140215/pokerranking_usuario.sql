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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `login` varchar(50) NOT NULL,
  `userpassword` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `codestado` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataInclusao` datetime NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `codestado` (`codestado`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`codestado`) REFERENCES `estadousuario` (`codestado`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('mboss','SGXQLB6mljw=','viniciusbrazpereira@gmail.com.br','ADMINISTRATOR SYSTEM',1,1,'2014-02-15 01:22:09','11987285067'),('pereira','pokerpoker','kaue@gmail.com','KAUE PEREIRA',1,2,'2014-02-14 22:10:09','119999999'),('viniciusbrazpereira','pokerpoker','viniciusbrazpereira@gmail.com','VINICIUS BRAZ PEREIRA',1,3,'2014-02-14 22:10:33','11999999999'),('camara','pokerpoker','camara@gmail.com','LUIS CAMARA',1,4,'2014-02-15 01:21:51','11999999999'),('sricardo','pokerpoker','sricardo@gmail.com','SERGIO RICARDO',1,5,'2014-02-14 22:11:21','11999999999'),('muniz','pokerpoker','muniz@gmail.com','RODRIGO MUNIZ',1,6,'2014-02-14 22:11:57','11999999999');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-15 12:43:01
