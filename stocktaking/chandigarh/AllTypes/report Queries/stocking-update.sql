-- Set Home branch 
set @homebranch  := 'CH';

-- Set inventory date
set @inventoryDate := '2013-11-10';

-- List of items not scanned and are neither marked missing nor withdrawn i.e. coluld not be located
SELECT /* Not scanned */  
	items.itemnumber
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home Branch>>
AND   (items.datelastseen <  <<Inventory Date>> OR datelastseen IS NULL)
AND   issues.itemnumber IS NULL
AND   items.itemlost = 0
AND   items.wthdrawn = 0
GROUP BY items.itemnumber
ORDER BY 1 ASC INTO OUTFILE "/tmp/not-scanned-not-missing-not-withdrawn.csv";

-- List of items not scanned but marked missing or withdrawn
SELECT /* Not scanned but marked missing or withdrawn */   
	items.itemnumber
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home Branch>>
AND   (items.datelastseen <  <<Inventory Date>> OR datelastseen IS NULL)
AND   issues.itemnumber IS NULL
AND   ( items.itemlost != 0 OR items.wthdrawn != 0)
GROUP BY items.itemnumber
ORDER BY 1 ASC INTO OUTFILE "/tmp/not-scanned-missing-or-withdrawn.csv";