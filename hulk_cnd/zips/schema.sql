-- MySQL dump 10.13  Distrib 5.5.33, for Linux (x86_64)
--
-- Host: localhost    Database: hulktest
-- ------------------------------------------------------
-- Server version	5.5.33-log

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
-- Table structure for table `cndtest`
--

DROP TABLE IF EXISTS `cndtest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cndtest` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'unique id corresponding to each master entry',
  `name` varchar(256) NOT NULL COMMENT 'name',
  `description` varchar(256) DEFAULT NULL COMMENT 'full desription of the specefic master entry',
  `group_name` varchar(256) NOT NULL COMMENT 'indicates in which group this master entry belongs',
  `parent` bigint(20) unsigned DEFAULT NULL COMMENT 'points to the parent of this entry.\nif no parent exist null',
  `sequence` int(11) DEFAULT NULL COMMENT ' sequence no  help to sort the entry in a desired order',
  `option1` varchar(2048) DEFAULT NULL COMMENT 'option',
  `option2` varchar(64) DEFAULT NULL COMMENT 'option 2',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72300 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-25 11:55:34
