ALTER TABLE deleteditems DROP `wthdrawn_date`;

ALTER TABLE deleteditems ADD `wthdrawn_date` date DEFAULT NULL;