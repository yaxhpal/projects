--
-- Table structure for table `Artist`
--
DROP TABLE IF EXISTS `Artist`;
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

--
-- Table structure for table `Organisation`
--
DROP TABLE IF EXISTS `Organisation`;
CREATE TABLE `Organisation` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Movement`
--
DROP TABLE IF EXISTS `Movement`;
CREATE TABLE `Movement` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Style`
--
DROP TABLE IF EXISTS `Style`;
CREATE TABLE `Style` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Exhibition`
--
DROP TABLE IF EXISTS `Exhibition`;
CREATE TABLE `Exhibition` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `User`
--
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT '0',
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `securityCode` varchar(255) DEFAULT NULL,
  `sessionToken` text DEFAULT NULL,
  `organisation_id` bigint(20) unsigned DEFAULT NULL,  
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_User_email` (`email`),
  KEY `FK_User_Organisation_id` (`organisation_id`),
  CONSTRAINT `FK_User_Organisation_id` FOREIGN KEY (`organisation_id`) REFERENCES `Organisation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `Location`
--
DROP TABLE IF EXISTS `Location`;
CREATE TABLE `Location` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
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


--
-- Table structure for table `Category`
--
DROP TABLE IF EXISTS `Category`;
CREATE TABLE `Category` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
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


--
-- Table structure for table `Artwork`
--
DROP TABLE IF EXISTS `Artwork`;
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

--
-- Table structure for table `Comment`
--
DROP TABLE IF EXISTS `Comment`;
CREATE TABLE `Comment` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
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

--
-- Table structure for table `Document`
--
DROP TABLE IF EXISTS `Document`;
CREATE TABLE `Document` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
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

--
-- Table structure for table `Image`
--
DROP TABLE IF EXISTS `Image`;
CREATE TABLE `Image` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,  
  PRIMARY KEY (`id`),
  KEY `FK_Image_Artwork_id` (`artwork_id`),
  CONSTRAINT `FK_Image_Artwork_id` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Provenance`
--
DROP TABLE IF EXISTS `Provenance`;
CREATE TABLE `Provenance` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,  
  PRIMARY KEY (`id`),
  KEY `FK_Provenance_Artwork_id` (`artwork_id`),
  CONSTRAINT `FK_Provenance_Artwork_id` FOREIGN KEY (`artwork_id`) REFERENCES `Artwork` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `PurchaseHistory`
--
DROP TABLE IF EXISTS `PurchaseHistory`;
CREATE TABLE `PurchaseHistory` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
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

--
-- Table structure for table `Slideshow`
--
DROP TABLE IF EXISTS `Slideshow`;
CREATE TABLE `Slideshow` (
  `id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `version` int(11) DEFAULT NULL,  
  PRIMARY KEY (`id`),
  KEY `FK_Slideshow_User_id` (`user_id`),
  CONSTRAINT `FK_Slideshow_User_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Tag`
--
DROP TABLE IF EXISTS `Tag`;
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

--
-- Table structure for table `Artwork_Category`
--
DROP TABLE IF EXISTS `Artwork_Category`;
CREATE TABLE `Artwork_Category` (
  `artwork_id` bigint(20) unsigned DEFAULT NULL,
  `categories_id` bigint(20) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping among artworks and categories.';


--
-- Table structure for table `Artwork_Exhibition`
--
DROP TABLE IF EXISTS `Artwork_Exhibition`;
CREATE TABLE `Artwork_Exhibition` (
  `artwork_id` bigint(20) unsigned NOT NULL,
  `exhibitions_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`artwork_id`,`exhibitions_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and exhibitions.';


--
-- Table structure for table `Artwork_Movement`
--
DROP TABLE IF EXISTS `Artwork_Movement`;
CREATE TABLE `Artwork_Movement` (
  `artwork_id` bigint(20) unsigned NOT NULL,
  `movements_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`artwork_id`,`movements_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and movements.';

--
-- Table structure for table `Artwork_Style`
--
DROP TABLE IF EXISTS `Artwork_Style`;
CREATE TABLE `Artwork_Style` (
  `artwork_id` bigint(20) unsigned NOT NULL,
  `styles_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`artwork_id`,`styles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and styles.';


--
-- Table structure for table `Slideshow_Artwork`
--
DROP TABLE IF EXISTS `Artwork_Slideshow`;
CREATE TABLE `Artwork_Slideshow` (
  `artworks_id` bigint(20) unsigned NOT NULL,
  `slideshow_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`Slideshow_id`,`artworks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Mapping between artworks and slideshows.';

--
-- Table structure for table `Tag_Artwork`
--
DROP TABLE IF EXISTS `Artwork_Tag`;
CREATE TABLE `Artwork_Tag` (
  `artworks_id` bigint(20) unsigned NOT NULL,
  `tag_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`Tag_id`,`artworks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `Upload_Log`
--
DROP TABLE IF EXISTS `UploadLog`;
CREATE TABLE `UploadLog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `filename` varchar(256) DEFAULT NULL COMMENT 'Upload file name',
  `failed` text COMMENT 'Filed Uploads',
  `comments` varchar(256) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='Log Information of Artwork Upload';

--
-- Table structure for table `hibernate_sequence`
--
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
