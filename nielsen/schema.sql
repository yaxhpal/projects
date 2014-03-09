--
-- Table structure for table `externaldata`
--

DROP TABLE IF EXISTS `externaldata`;

CREATE TABLE `externaldata` (
    `id` SERIAL,
    `isbn10` VARCHAR(13) DEFAULT NULL,
    `isbn13` VARCHAR(17) DEFAULT NULL,
    `vendor_id` VARCHAR(30) DEFAULT NULL,
    `type` VARCHAR(10) DEFAULT NULL,
    `title` MEDIUMTEXT DEFAULT NULL,
    `author` MEDIUMTEXT DEFAULT NULL,
    `publisher` MEDIUMTEXT DEFAULT NULL,
    `description` MEDIUMTEXT DEFAULT NULL,
    `date_published` DATE DEFAULT NULL,
    `copyright_year` SMALLINT DEFAULT NULL,
    `subject` MEDIUMTEXT DEFAULT NULL,
    `edition` TINYINT DEFAULT 1,
	`number_of_pages` SMALLINT DEFAULT NULL,
    `imageflag` TINYINT DEFAULT 0,
    `thumbnail_url` MEDIUMTEXT DEFAULT NULL,
    `image_url` MEDIUMTEXT DEFAULT NULL,
    `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `isbn` (`isbn13`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;