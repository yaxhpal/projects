delimiter $$

CREATE TABLE `deleted_rfid_msgs` (
  `alertnumber` int(11) NOT NULL AUTO_INCREMENT,
  `borrowernumber` int(11) DEFAULT NULL,
  `itemnumber` int(11) DEFAULT NULL,
  `transactiontype` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`alertnumber`),
  KEY `emailborridx` (`borrowernumber`),
  KEY `emailitemidx` (`itemnumber`)
) ENGINE=InnoDB AUTO_INCREMENT=797043 DEFAULT CHARSET=utf8$$


-- INSERT deleted_rfid_msgs SELECT * FROM rfid_msgs;

select count(*) from deleted_rfid_msgs;