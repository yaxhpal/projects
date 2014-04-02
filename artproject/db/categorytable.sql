-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: oeuvres
-- ------------------------------------------------------
-- Server version       5.1.41-3ubuntu12.9

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
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Category` (
  `id` bigint(19) unsigned NOT NULL COMMENT 'Id',
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `cnd_group` varchar(64) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `status` char(1) DEFAULT NULL COMMENT 'Status of the Record;\nA: Active ; I : Inactive;\nD : Deleted\n',
  `created` int(11) DEFAULT NULL,
  `updated` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Code and Description';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
INSERT INTO `Category` VALUES (1000,'Artworks','Artworks','Category',NULL,1,'A',1394430179,1394430179),(1001,'Furniture','Furniture','Category',NULL,2,'A',1394430179,1394430179),(1002,'Lighting','Lighting','Category',NULL,3,'A',1394430179,1394430179),(1003,'Objets d’art','Objets d’art','Category',NULL,4,'A',1394430179,1394430179),(1004,'Rugs','Rugs','Category',NULL,5,'A',1394430179,1394430179),(1005,'Sculptures','Sculptures','Category',NULL,6,'A',1394430179,1394430179),(2000,'Commissions','Commissions','SubCategory',1000,1,'A',1394430179,1394430179),(2001,'Movement','Movement','SubCategory',1000,2,'A',1394430179,1394430179),(2002,'Styles','Styles','SubCategory',1000,3,'A',1394430179,1394430179),(2003,'Contemporary','Contemporary','SubCategory',1001,1,'A',1394430179,1394430179),(2004,'Commissions','Commissions','SubCategory',1001,2,'A',1394430180,1394430180),(2005,'Antiques','Antiques','SubCategory',1001,3,'A',1394430180,1394430180),(2006,'Contemporary','Contemporary','SubCategory',1002,1,'A',1394430180,1394430180),(2007,'Commissions','Commissions','SubCategory',1002,2,'A',1394430180,1394430180),(2008,'Antiques','Antiques','SubCategory',1002,3,'A',1394430180,1394430180),(2009,'Contemporary','Contemporary','SubCategory',1003,1,'A',1394430180,1394430180),(2010,'Commissions','Commissions','SubCategory',1003,2,'A',1394430180,1394430180),(2011,'Antiques','Antiques','SubCategory',1003,3,'A',1394430180,1394430180),(2012,'Contemporary','Contemporary','SubCategory',1004,1,'A',1394430180,1394430180),(2013,'Commissions','Commissions','SubCategory',1004,2,'A',1394430180,1394430180),(2014,'Antiques','Antiques','SubCategory',1004,3,'A',1394430180,1394430180),(2015,'Commissions','Commissions','SubCategory',1005,1,'A',1394430180,1394430180),(2016,'Movement','Movement','SubCategory',1005,2,'A',1394430180,1394430180),(2017,'Styles','Styles','SubCategory',1005,3,'A',1394430177,1394430177);
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-29 15:42:45
