--
-- Table structure for table `coupons`
--

DROP TABLE IF EXISTS `coupons`;

CREATE TABLE `coupons` (
    `id` INT(11) NOT NULL AUTO_INCREMENT UNIQUE,
    `code` CHAR(8) NOT NULL UNIQUE,
    `type` CHAR NOT NULL, /* P = percentage, A = amount */
	`startdate` DATE NOT NULL,
	`enddate` DATE DEFAULT NULL,
    `value` DECIMAL(28,6) DEFAULT NULL, /* Absolute value  */
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`created_by` int(11) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY `code_idx` (`code`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

-- coupon example - HKK4VP5A

ALTER TABLE member_registration ADD coupon_id INT(11) DEFAULT NULL;

