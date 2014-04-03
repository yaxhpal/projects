alter table Artwork MODIFY category_id int(11) default null;
alter table Artwork add `externalLink` varchar(255) DEFAULT NULL;