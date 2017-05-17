delimiter $$

DROP schema IF EXISTS `pokerranking`$$

CREATE DATABASE `pokerranking` /*!40100 DEFAULT CHARACTER SET latin1 */$$

USE pokerranking$$

CREATE TABLE `pokerranking`.`estadousuario` (
  `codestado` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`codestado`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1$$

CREATE TABLE `pokerranking`.`usuario` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1$$

CREATE TABLE `pokerranking`.`perfil` (
  `codperfil` int(11) NOT NULL AUTO_INCREMENT,
  `nome_perfil` varchar(50) NOT NULL,
  `restricao` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`codperfil`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1$$

CREATE TABLE `pokerranking`.`usuarioperfil` (
  `id` int(11) NOT NULL,
  `codperfil` int(11) NOT NULL,
  KEY `codperfil` (`codperfil`),
  KEY `codusuario` (`id`),
  CONSTRAINT `usuarioperfil_ibfk_1` FOREIGN KEY (`codperfil`) REFERENCES `perfil` (`codperfil`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1$$

CREATE TABLE `pokerranking`.`menu` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1$$

CREATE TABLE `pokerranking`.`perfilmenu` (
  `codperfil` int(11) DEFAULT NULL,
  `codmenu` int(11) DEFAULT NULL,
  UNIQUE KEY `codmenu_UNIQUE` (`codmenu`),
  KEY `codperfil` (`codperfil`),
  KEY `codmenu` (`codmenu`),
  CONSTRAINT `perfilmenu_ibfk_2` FOREIGN KEY (`codmenu`) REFERENCES `menu` (`codmenu`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

CREATE TABLE `pokerranking`.`punter_pokerEvent` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

USE pokerranking$$

CREATE TABLE `nivel` (
  `id_nivel` int(11) NOT NULL AUTO_INCREMENT,
  `number_nivel` int(11) NOT NULL,
  `small_blind` double NOT NULL,
  `big_blind` double NOT NULL,
  `ante` double DEFAULT NULL,
  PRIMARY KEY (`id_nivel`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1$$

INSERT INTO `pokerranking`.`estadousuario` (`descricao`) VALUES('ATIVO')$$
INSERT INTO `pokerranking`.`estadousuario` (`descricao`)VALUES('INATIVO')$$

INSERT INTO `pokerranking`.`usuario`
(`login`,
`userpassword`,
`email`,
`nome`,
`codestado`,
`dataInclusao`,
`phone`)
VALUES(
'mboss',
'sfingy.com.br', 
'viniciusbrazpereira@gmail.com.br', 
'ADMINISTRATOR SYSTEM', 
1, 
'2014-01-01 00:00:00', 
'987285067'
)$$

INSERT INTO `pokerranking`.`usuario`
(`login`,
`userpassword`,
`email`,
`nome`,
`codestado`,
`dataInclusao`,
`phone`)
VALUES(
'pokerview',
'sfingy.com.br', 
'viniciusbrazpereira@gmail.com.br', 
'VIEW SYSTEM', 
1, 
'2014-01-01 00:00:00', 
'987285067'
)$$

INSERT INTO `pokerranking`.`menu`
VALUES (1,'Sistema','','ui-icon-tools',0,'',2,'A',1),
(2,'Cadastro Menu','','ui-icon-window',1,'/pages/protegido/cadastro_menu.jsf',1,'A',0),
(3,'Cadastro Usuário','','ui-icon-window',1,'/pages/protegido/cadastro_usuario.jsf',2,'A',0),
(4,'Cadastro Evento','','ui-icon-window',1,'/pages/protegido/cadastro_pokerEvent.jsf',3,'A',0),
(5,'Cadastro Perfil','','ui-icon-window',1,'/pages/protegido/perfil_menu.jsf',4,'A',0),
(6,'Home','','ui-icon-monitor',0,'/pages/protegido/home.jsf',1,'A',1),
(7,'Cadastro Nível','','ui-icon-window',1,'/pages/protegido/cadastro_nivel.jsf',5,'A',0)$$

INSERT INTO `pokerranking`.`perfil`
(`nome_perfil`,
`restricao`)
VALUES
(
'ADM. SISTEMA',
0
)$$

INSERT INTO `pokerranking`.`perfil`
(`nome_perfil`,
`restricao`)
VALUES
(
'APOSTADOR',
0
)$$

INSERT INTO `pokerranking`.`perfilmenu`
VALUES (2,6),(1,1),(1,2),(1,3),(1,4),(1,5),(1,7)$$

INSERT INTO `pokerranking`.`usuarioperfil`
(`id`,
`codperfil`)
VALUES
(
1,
1
)$$

INSERT INTO `pokerranking`.`usuarioperfil`
(`id`,
`codperfil`)
VALUES
(
1,
2
)$$

INSERT INTO `pokerranking`.`usuarioperfil`
(`id`,
`codperfil`)
VALUES
(
2,
2
)$$
