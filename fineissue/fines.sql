select * from old_issues where
borrowernumber='309656' order by date_due DESC;


SELECT old_issues.*, items.itype as itemtype, items.homebranch, items.barcode
FROM old_issues 
LEFT JOIN items  USING (itemnumber)
WHERE old_issues.date_due < '2014-02-08' and old_issues.date_due > '2014-01-01'
and old_issues.branchcode = 'CL';


SELECT issues.*, items.itype as itemtype, items.homebranch, items.barcode
     FROM issues 
LEFT JOIN items       USING (itemnumber)
    WHERE date_due < CURDATE() 

SELECT issues.*, biblioitems.itemtype, items.itype, items.homebranch, items.barcode
     FROM issues 
LEFT JOIN items       USING (itemnumber)
LEFT JOIN biblioitems USING (biblioitemnumber)
    WHERE date_due < CURDATE()

UPDATE accountlines
				SET date=now(), amount=?, amountoutstanding=?,
					lastincrement=?, accounttype='FU'
	  			WHERE borrowernumber=?
				AND   itemnumber=?
				AND   accounttype IN ('FU','O')
				AND   description LIKE ?
				LIMIT 1;

SELECT * FROM accountlines
		WHERE itemnumber=?
		AND   borrowernumber=?
		AND   accounttype IN ('FU','O','F','M')
		AND   description like ? 

SELECT * FROM issues
         LEFT JOIN items       ON issues.itemnumber      = items.itemnumber
         LEFT JOIN biblio      ON items.biblionumber     = biblio.biblionumber
         LEFT JOIN biblioitems ON items.biblioitemnumber = biblioitems.biblioitemnumber
            WHERE issues.borrowernumber  = ?
            AND   issues.date_due < CURDATE()

INSERT INTO accountlines  ( borrowernumber,itemnumber,
date,amount,description,accounttype,amountoutstanding,
lastincrement,accountno) VALUES ('?','?',now(),'?','?','FU','?','?','?');

SELECT title FROM biblio LEFT JOIN items ON biblio.biblionumber=items.biblionumber WHERE items.itemnumber=?;

SELECT * FROM issues WHERE itemnumber=?;
