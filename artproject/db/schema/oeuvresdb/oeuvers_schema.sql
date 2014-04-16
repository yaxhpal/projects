-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: oeuvres
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.9

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
  `id` bigint(20) NOT NULL,
  `about` text,
  `birthPlace` varchar(255) DEFAULT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `yearOfBirth` int(11) DEFAULT NULL,
  `yearOfDeath` int(11) DEFAULT NULL,
  `hasPhoto` tinyint(1) DEFAULT '0',
  `externalLink` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Artist_Movement`
--

DROP TABLE IF EXISTS `Artist_Movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artist_Movement` (
  `Artist_id` bigint(20) NOT NULL,
  `movements_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Artist_id`,`movements_id`),
  KEY `FK_5f9wqkqlpd6m4nxgc2f7j51is` (`movements_id`),
  KEY `FK_lxyn6ds1olgnuh8xs068csrml` (`Artist_id`),
  CONSTRAINT `FK_5f9wqkqlpd6m4nxgc2f7j51is` FOREIGN KEY (`movements_id`) REFERENCES `Movement` (`id`),
  CONSTRAINT `FK_lxyn6ds1olgnuh8xs068csrml` FOREIGN KEY (`Artist_id`) REFERENCES `Artist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Artwork`
--

DROP TABLE IF EXISTS `Artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork` (
  `id` bigint(20) NOT NULL,
  `artBookBio` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `artist_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `yearNote` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `depth` int(11) NOT NULL DEFAULT '0',
  `height` int(11) NOT NULL DEFAULT '0',
  `tagNumber` varchar(255) DEFAULT NULL,
  `width` int(11) NOT NULL DEFAULT '0',
  `medium` varchar(255) DEFAULT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  `exportRestriction` varchar(255) DEFAULT NULL,
  `importRestriction` varchar(255) DEFAULT NULL,
  `origin` varchar(255) NOT NULL,
  `editionNo` varchar(255) DEFAULT NULL,
  `framedDepth` int(11) NOT NULL DEFAULT '0',
  `framedHeight` int(11) NOT NULL DEFAULT '0',
  `framedWidth` int(11) NOT NULL DEFAULT '0',
  `signed` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `conditionDescription` varchar(255) DEFAULT NULL,
  `externalLink` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3797EFD4A98C6ED2` (`artist_id`),
  KEY `FK3797EFD4FD002E52` (`user_id`),
  KEY `FK_sejdp2lp7j8m1rnquh25fljtc` (`location_id`),
  CONSTRAINT `FK3797EFD4A98C6ED2` FOREIGN KEY (`artist_id`) REFERENCES `Artist` (`id`),
  CONSTRAINT `FK3797EFD4FD002E52` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_sejdp2lp7j8m1rnquh25fljtc` FOREIGN KEY (`location_id`) REFERENCES `Location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Artwork_Category`
--

DROP TABLE IF EXISTS `Artwork_Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Category` (
  `Artwork_id` bigint(20) DEFAULT NULL,
  `categories_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Mapping among artworks and categories.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Artwork_Exhibition`
--

DROP TABLE IF EXISTS `Artwork_Exhibition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Exhibition` (
  `Artwork_id` bigint(20) NOT NULL,
  `exhibitions_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Artwork_id`,`exhibitions_id`),
  KEY `FK_kgdakspynrvhc9ntil6i0hk7g` (`exhibitions_id`),
  KEY `FK_en85qlc105vgkop2wi3tbuiiy` (`Artwork_id`),
  CONSTRAINT `FK_en85qlc105vgkop2wi3tbuiiy` FOREIGN KEY (`Artwork_id`) REFERENCES `Artwork` (`id`),
  CONSTRAINT `FK_kgdakspynrvhc9ntil6i0hk7g` FOREIGN KEY (`exhibitions_id`) REFERENCES `Exhibition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Artwork_Movement`
--

DROP TABLE IF EXISTS `Artwork_Movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Movement` (
  `Artwork_id` bigint(20) NOT NULL,
  `movements_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Artwork_id`,`movements_id`),
  KEY `FK_eim4m2634sp7jwvnx1napqn7d` (`movements_id`),
  KEY `FK_khitfbjffmewcwjqscbu8kg9p` (`Artwork_id`),
  CONSTRAINT `FK_eim4m2634sp7jwvnx1napqn7d` FOREIGN KEY (`movements_id`) REFERENCES `Movement` (`id`),
  CONSTRAINT `FK_khitfbjffmewcwjqscbu8kg9p` FOREIGN KEY (`Artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Artwork_Style`
--

DROP TABLE IF EXISTS `Artwork_Style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artwork_Style` (
  `Artwork_id` bigint(20) NOT NULL,
  `styles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Artwork_id`,`styles_id`),
  KEY `FK_hwmdq1ub5vt7nc6lwv0xochd4` (`styles_id`),
  KEY `FK_ev9pp0ejr3devwn2s00ruaxpl` (`Artwork_id`),
  CONSTRAINT `FK_ev9pp0ejr3devwn2s00ruaxpl` FOREIGN KEY (`Artwork_id`) REFERENCES `Artwork` (`id`),
  CONSTRAINT `FK_hwmdq1ub5vt7nc6lwv0xochd4` FOREIGN KEY (`styles_id`) REFERENCES `Style` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `Comment`
--

DROP TABLE IF EXISTS `Comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comment` (
  `id` bigint(20) NOT NULL,
  `date` datetime DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `artwork_id` bigint(20) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kd4g6405lv440ydwhjlgp45ie` (`artwork_id`),
  KEY `FK_j94pith5sd971k29j6ysxuk7` (`author_id`),
  CONSTRAINT `FK_j94pith5sd971k29j6ysxuk7` FOREIGN KEY (`author_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_kd4g6405lv440ydwhjlgp45ie` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Document`
--

DROP TABLE IF EXISTS `Document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Document` (
  `id` bigint(20) NOT NULL,
  `file` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `artwork_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b0ay811yri9artao1itp13ryh` (`artwork_id`),
  KEY `FK_gwmu2dyi74bhuc2x4wsprhwxy` (`user_id`),
  CONSTRAINT `FK_b0ay811yri9artao1itp13ryh` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`),
  CONSTRAINT `FK_gwmu2dyi74bhuc2x4wsprhwxy` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Exhibition`
--

DROP TABLE IF EXISTS `Exhibition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Exhibition` (
  `id` bigint(20) NOT NULL,
  `date` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Image`
--

DROP TABLE IF EXISTS `Image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Image` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `artwork_id` bigint(20) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK437B93B65B4B5C2` (`artwork_id`),
  CONSTRAINT `FK437B93B65B4B5C2` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Location` (
  `id` bigint(20) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `wall` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pq6r9gpqbed1vmt8rb4uqu7rb` (`owner_id`),
  CONSTRAINT `FK_pq6r9gpqbed1vmt8rb4uqu7rb` FOREIGN KEY (`owner_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Movement`
--

DROP TABLE IF EXISTS `Movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Movement` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Organisation`
--

DROP TABLE IF EXISTS `Organisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organisation` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Provenance`
--

DROP TABLE IF EXISTS `Provenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Provenance` (
  `id` bigint(20) NOT NULL,
  `date` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `artwork_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_drnd7vmw95rbf2ux890ljtlh` (`artwork_id`),
  CONSTRAINT `FK_drnd7vmw95rbf2ux890ljtlh` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PurchaseHistory`
--

DROP TABLE IF EXISTS `PurchaseHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PurchaseHistory` (
  `id` bigint(20) NOT NULL,
  `commission` varchar(255) DEFAULT NULL,
  `insurance` varchar(255) DEFAULT NULL,
  `price` varchar(255) NOT NULL,
  `purchasedFrom` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `valuation` varchar(255) DEFAULT NULL,
  `valuationDate` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `artwork_id` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `artistRR` varchar(255) DEFAULT NULL,
  `importTax` varchar(255) DEFAULT NULL,
  `valuedBy` varchar(255) DEFAULT NULL,
  `purchaseDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bksndbfg4jr27ix5d47s993bt` (`artwork_id`),
  KEY `FK_3v0ny99mijdlmyusctots6e10` (`owner_id`),
  CONSTRAINT `FK_3v0ny99mijdlmyusctots6e10` FOREIGN KEY (`owner_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_bksndbfg4jr27ix5d47s993bt` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Slideshow`
--

DROP TABLE IF EXISTS `Slideshow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Slideshow` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4806F80EFD002E52` (`user_id`),
  CONSTRAINT `FK4806F80EFD002E52` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Slideshow_Artwork`
--

DROP TABLE IF EXISTS `Slideshow_Artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Slideshow_Artwork` (
  `Slideshow_id` bigint(20) NOT NULL,
  `artworks_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Slideshow_id`,`artworks_id`),
  KEY `FK_emoqqy70716dpr68775h0a3fe` (`artworks_id`),
  KEY `FK_41bffgqlrleu4ga3d28lu6gsw` (`Slideshow_id`),
  CONSTRAINT `FK_41bffgqlrleu4ga3d28lu6gsw` FOREIGN KEY (`Slideshow_id`) REFERENCES `Slideshow` (`id`),
  CONSTRAINT `FK_emoqqy70716dpr68775h0a3fe` FOREIGN KEY (`artworks_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Style`
--

DROP TABLE IF EXISTS `Style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Style` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_24642shpebunaq3ggshotv9hk` (`name`),
  KEY `FK_sj4d7kfky7axscj2lae432f55` (`user_id`),
  CONSTRAINT `FK_sj4d7kfky7axscj2lae432f55` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tag_Artwork`
--

DROP TABLE IF EXISTS `Tag_Artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag_Artwork` (
  `Tag_id` bigint(20) NOT NULL,
  `artworks_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Tag_id`,`artworks_id`),
  KEY `FK_h8jl3e3dm28mwl3hmg6pci0s1` (`artworks_id`),
  KEY `FK_160q91n7h37x0q6og5d5xmlwl` (`Tag_id`),
  CONSTRAINT `FK_160q91n7h37x0q6og5d5xmlwl` FOREIGN KEY (`Tag_id`) REFERENCES `Tag` (`id`),
  CONSTRAINT `FK_h8jl3e3dm28mwl3hmg6pci0s1` FOREIGN KEY (`artworks_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Upload_Log`
--

DROP TABLE IF EXISTS `UploadLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UploadLog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `filename` varchar(256) DEFAULT NULL COMMENT 'Upload file name',
  `Failed` text COMMENT 'Filed Uploads',
  `Comments` varchar(256) DEFAULT NULL,
  `upload_date` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='Log Information of Artwork Upload';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL,
  `active` tinyint(1) DEFAULT '0',
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `securityCode` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `organisation_id` bigint(20) DEFAULT NULL,
  `sessionToken` text,
  PRIMARY KEY (`id`),
  KEY `FK285FEB792E7F2` (`organisation_id`),
  CONSTRAINT `FK285FEB792E7F2` FOREIGN KEY (`organisation_id`) REFERENCES `Organisation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-06 18:17:27
