--
-- Table structure for table `leadcampaign`
--

DROP TABLE IF EXISTS `leadcampaign`;
CREATE TABLE `leadcampaign` (
  `id` SERIAL,
  `campaign_id` varchar(128),
  `firstname` varchar(128),
  `lastname` varchar(128),
  `email` varchar(128),
  `mobile` INT(14),
  `city` varchar(128),
  `city_other` varchar(128),
  `reg_id`  INT(11),
  `is_member` BOOLEAN DEFAULT 0,
  `categorycode` varchar(100),
  PRIMARY KEY (`id`, `campaign_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;