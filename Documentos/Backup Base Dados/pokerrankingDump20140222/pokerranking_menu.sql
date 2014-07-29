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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `codmenu` int(11) NOT NULL AUTO_INCREMENT,
  `nome_menu` varchar(50) NOT NULL,
  `actionlistener` varchar(50) DEFAULT NULL,
  `icon` varchar(30) DEFAULT NULL,
  `codmenupai` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `sequencia` int(11) DEFAULT NULL,
  `sistema` varchar(1) DEFAULT NULL,
  `submenu` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codmenu`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'Sistema','','ui-icon-tools',0,'',1,'A',1),(2,'Cadastro Menu','','ui-icon-window',1,'/pages/protegido/cadastro_menu.jsf',1,'A',0),(3,'Cadastro Usuário','','ui-icon-window',1,'/pages/protegido/cadastro_usuario.jsf',2,'A',0),(4,'Cadastro Evento','','ui-icon-window',1,'/pages/protegido/cadastro_pokerEvent.jsf',3,'A',0),(5,'Cadastro Perfil','','ui-icon-window',1,'/pages/protegido/perfil_menu.jsf',4,'A',0);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
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
