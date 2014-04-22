-- MySQL dump 10.13  Distrib 5.5.33, for Linux (x86_64)
--
-- Host: localhost    Database: oeuvres_clean
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
-- Table structure for table `Artist`
--

DROP TABLE IF EXISTS `Artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artist` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `about` text,
  `birthPlace` varchar(255) DEFAULT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `yearOfBirth` int(11) DEFAULT NULL,
  `yearOfDeath` int(11) DEFAULT NULL,
  `hasPhoto` tinyint(1) DEFAULT '0',
  `externalLink` varchar(512) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artist`
--

LOCK TABLES `Artist` WRITE;
/*!40000 ALTER TABLE `Artist` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artist_Movement`
--

DROP TABLE IF EXISTS `Artist_Movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artist_Movement` (
  `artist_id` bigint(20) unsigned NOT NULL,
  `movements_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`artist_id`,`movements_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artist_Movement`
--

LOCK TABLES `Artist_Movement` WRITE;
/*!40000 ALTER TABLE `Artist_Movement` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artist_Movement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artwork`
--

DROP TABLE IF EXISTS `Artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `artBookBio` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `year` int(11) DEFAULT NULL,
  `yearNote` varchar(255) DEFAULT NULL,
  `signed` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `width` int(11) NOT NULL DEFAULT '0',
  `medium` varchar(255) DEFAULT NULL,
  `origin` varchar(255) NOT NULL,
  `tagNumber` varchar(255) DEFAULT NULL,
  `editionNo` varchar(255) DEFAULT NULL,
  `depth` int(11) NOT NULL DEFAULT '0',
  `height` int(11) NOT NULL DEFAULT '0',
  `framedDepth` int(11) NOT NULL DEFAULT '0',
  `framedHeight` int(11) NOT NULL DEFAULT '0',
  `framedWidth` int(11) NOT NULL DEFAULT '0',
  `exportRestriction` varchar(255) DEFAULT NULL,
  `importRestriction` varchar(255) DEFAULT NULL,
  `conditionDescription` varchar(255) DEFAULT NULL,
  `externalLink` varchar(512) DEFAULT NULL,
  `artist_id` bigint(20) unsigned DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `location_id` bigint(20) unsigned DEFAULT NULL,
  `category_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Artwork_Artist_id` (`artist_id`),
  KEY `FK_Artwork_User_id` (`user_id`),
  KEY `FK_Artwork_Location_id` (`location_id`),
  KEY `FK_Artwork_Category_id` (`category_id`),
  CONSTRAINT `FK_Artwork_Artist_id` FOREIGN KEY (`artist_id`) REFERENCES `Artist` (`id`),
  CONSTRAINT `FK_Artwork_User_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_Artwork_Location_id` FOREIGN KEY (`location_id`) REFERENCES `Location` (`id`),
  CONSTRAINT `FK_Artwork_Category_id` FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artwork`
--

LOCK TABLES `Artwork` WRITE;
/*!40000 ALTER TABLE `Artwork` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artwork` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artwork_Category`
--

DROP TABLE IF EXISTS `Artwork_Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Category` (
  `artwork_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `categories_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`artwork_id`,`categories_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping among artworks and categories.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artwork_Category`
--

LOCK TABLES `Artwork_Category` WRITE;
/*!40000 ALTER TABLE `Artwork_Category` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artwork_Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artwork_Exhibition`
--

DROP TABLE IF EXISTS `Artwork_Exhibition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Exhibition` (
  `artwork_id` bigint(20) unsigned NOT NULL,
  `exhibitions_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`artwork_id`,`exhibitions_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and exhibitions.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artwork_Exhibition`
--

LOCK TABLES `Artwork_Exhibition` WRITE;
/*!40000 ALTER TABLE `Artwork_Exhibition` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artwork_Exhibition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artwork_Movement`
--

DROP TABLE IF EXISTS `Artwork_Movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Movement` (
  `artwork_id` bigint(20) unsigned NOT NULL,
  `movements_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`artwork_id`,`movements_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and movements.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artwork_Movement`
--

LOCK TABLES `Artwork_Movement` WRITE;
/*!40000 ALTER TABLE `Artwork_Movement` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artwork_Movement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Slideshow_Artwork`
--

DROP TABLE IF EXISTS `Slideshow_Artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Slideshow_Artwork` (
  `artworks_id` bigint(20) unsigned NOT NULL,
  `slideshow_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`slideshow_id`,`artworks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and slideshows.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Slideshow_Artwork`
--

LOCK TABLES `Slideshow_Artwork` WRITE;
/*!40000 ALTER TABLE `Slideshow_Artwork` DISABLE KEYS */;
/*!40000 ALTER TABLE `Slideshow_Artwork` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artwork_Style`
--

DROP TABLE IF EXISTS `Artwork_Style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Style` (
  `artwork_id` bigint(20) unsigned NOT NULL,
  `styles_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`artwork_id`,`styles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and styles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artwork_Style`
--

LOCK TABLES `Artwork_Style` WRITE;
/*!40000 ALTER TABLE `Artwork_Style` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artwork_Style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tag_Artwork`
--

DROP TABLE IF EXISTS `Tag_Artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag_Artwork` (
  `artworks_id` bigint(20) unsigned NOT NULL,
  `tag_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`tag_id`,`artworks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tag_Artwork`
--

LOCK TABLES `Tag_Artwork` WRITE;
/*!40000 ALTER TABLE `Tag_Artwork` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tag_Artwork` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `cnd_group` varchar(64) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `status` char(1) DEFAULT NULL COMMENT 'Status of the Record; A: Active ; I : Inactive; D : Deleted',
  `created` int(11) DEFAULT NULL,
  `updated` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Code and Description';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Comment`
--

DROP TABLE IF EXISTS `Comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `author_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Comment_Artwork_id` (`artwork_id`),
  KEY `FK_Comment_User_id` (`author_id`),
  CONSTRAINT `FK_Comment_Artwork_id` FOREIGN KEY (`author_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_Comment_User_id` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comment`
--

LOCK TABLES `Comment` WRITE;
/*!40000 ALTER TABLE `Comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `Comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Document`
--

DROP TABLE IF EXISTS `Document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Document` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `file` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Document_Artwork_id` (`artwork_id`),
  KEY `FK_Document_User_id` (`user_id`),
  CONSTRAINT `FK_Document_Artwork_id` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`),
  CONSTRAINT `FK_Document_User_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Document`
--

LOCK TABLES `Document` WRITE;
/*!40000 ALTER TABLE `Document` DISABLE KEYS */;
/*!40000 ALTER TABLE `Document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Exhibition`
--

DROP TABLE IF EXISTS `Exhibition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Exhibition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Exhibition`
--

LOCK TABLES `Exhibition` WRITE;
/*!40000 ALTER TABLE `Exhibition` DISABLE KEYS */;
/*!40000 ALTER TABLE `Exhibition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Image`
--

DROP TABLE IF EXISTS `Image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Image` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Image_Artwork_id` (`artwork_id`),
  CONSTRAINT `FK_Image_Artwork_id` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Image`
--

LOCK TABLES `Image` WRITE;
/*!40000 ALTER TABLE `Image` DISABLE KEYS */;
/*!40000 ALTER TABLE `Image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Location` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `location` varchar(255) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `owner_id` bigint(20) unsigned DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `wall` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Location_User_id` (`owner_id`),
  CONSTRAINT `FK_Location_User_id` FOREIGN KEY (`owner_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Movement`
--

DROP TABLE IF EXISTS `Movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Movement` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Movement`
--

LOCK TABLES `Movement` WRITE;
/*!40000 ALTER TABLE `Movement` DISABLE KEYS */;
/*!40000 ALTER TABLE `Movement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Organisation`
--

DROP TABLE IF EXISTS `Organisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organisation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Organisation`
--

LOCK TABLES `Organisation` WRITE;
/*!40000 ALTER TABLE `Organisation` DISABLE KEYS */;
/*!40000 ALTER TABLE `Organisation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Provenance`
--

DROP TABLE IF EXISTS `Provenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Provenance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Provenance_Artwork_id` (`artwork_id`),
  CONSTRAINT `FK_Provenance_Artwork_id` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Provenance`
--

LOCK TABLES `Provenance` WRITE;
/*!40000 ALTER TABLE `Provenance` DISABLE KEYS */;
/*!40000 ALTER TABLE `Provenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PurchaseHistory`
--

DROP TABLE IF EXISTS `PurchaseHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PurchaseHistory` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `commission` varchar(255) DEFAULT NULL,
  `insurance` varchar(255) DEFAULT NULL,
  `price` varchar(255) NOT NULL,
  `purchasedFrom` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `valuation` varchar(255) DEFAULT NULL,
  `valuationDate` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `artistRR` varchar(255) DEFAULT NULL,
  `importTax` varchar(255) DEFAULT NULL,
  `valuedBy` varchar(255) DEFAULT NULL,
  `purchaseDate` varchar(255) DEFAULT NULL,
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `owner_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PurchaseHistory_User_id` (`owner_id`),
  KEY `FK_PurchaseHistory_Artwork_id` (`artwork_id`),
  CONSTRAINT `FK_PurchaseHistory_User_id` FOREIGN KEY (`owner_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_PurchaseHistory_Artwork_id` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PurchaseHistory`
--

LOCK TABLES `PurchaseHistory` WRITE;
/*!40000 ALTER TABLE `PurchaseHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `PurchaseHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Slideshow`
--

DROP TABLE IF EXISTS `Slideshow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Slideshow` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Slideshow_User_id` (`user_id`),
  CONSTRAINT `FK_Slideshow_User_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Slideshow`
--

LOCK TABLES `Slideshow` WRITE;
/*!40000 ALTER TABLE `Slideshow` DISABLE KEYS */;
/*!40000 ALTER TABLE `Slideshow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Style`
--

DROP TABLE IF EXISTS `Style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Style` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Style`
--

LOCK TABLES `Style` WRITE;
/*!40000 ALTER TABLE `Style` DISABLE KEYS */;
/*!40000 ALTER TABLE `Style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Tag_name` (`name`),
  KEY `FK_Tag_User_id` (`user_id`),
  CONSTRAINT `FK_Tag_User_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tag`
--

LOCK TABLES `Tag` WRITE;
/*!40000 ALTER TABLE `Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UploadLog`
--

DROP TABLE IF EXISTS `UploadLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UploadLog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `filename` varchar(256) DEFAULT NULL COMMENT 'Upload file name',
  `failed` text COMMENT 'Filed Uploads',
  `comments` varchar(256) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='Log Information of Artwork Upload';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UploadLog`
--

LOCK TABLES `UploadLog` WRITE;
/*!40000 ALTER TABLE `UploadLog` DISABLE KEYS */;
/*!40000 ALTER TABLE `UploadLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT '0',
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `securityCode` varchar(255) DEFAULT NULL,
  `sessionToken` text,
  `organisation_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_User_email` (`email`),
  KEY `FK_User_Organisation_id` (`organisation_id`),
  CONSTRAINT `FK_User_Organisation_id` FOREIGN KEY (`organisation_id`) REFERENCES `Organisation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,1,'2013-07-13','niklas@zennstrom.com','Niklas','Zennström','6025e0109cde76bbbee89b0cffd6ca2bf33f1aea463e3f266e1513b22b27a196','demo','demo',NULL,NULL,1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-21 18:06:54
