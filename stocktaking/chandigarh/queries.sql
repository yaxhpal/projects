-- Generate total stock of DVD’s available in Bangalore
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
ORDER BY items.dateaccessioned DESC;

-- List of titles issued out
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   items.onloan IS NOT NULL
ORDER BY items.dateaccessioned DESC;

-- List of items in the library
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   items.onloan IS NULL
ORDER BY items.dateaccessioned DESC;


-- List of items not scanned
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   items.datelastseen < '2013-12-16'
ORDER BY items.dateaccessioned DESC;

-- List of titles missing
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BK'
AND   items.itemlost = 4 -- Missing itemss 
ORDER BY items.dateaccessioned DESC;

-- List of duplicate titles
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM  items
LEFT JOIN biblioitems ON (items.biblionumber = biblioitems.biblionumber) 
LEFT JOIN biblio       ON (items.biblionumber = biblio.biblionumber) 
WHERE items.homebranch = 'CH'
AND   items.itype = 'DVD'
GROUP BY CONCAT(title,"/",author) 
HAVING COUNT(CONCAT(title,"/",author)) > 1
ORDER BY items.dateaccessioned DESC;


-- Final list of DVD’s available in library
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   items.datelastseen >= '2013-12-16'
UNION
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   items.datelastseen < '2013-12-16'
AND	  items.onloan IS NOT NULL;


-- #####################################################################################

-- Generate total stock of DVD’s available in Bangalore
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   items.notforloan = 0    -- Could be issued
AND   items.wthdrawn   = 0    -- Not on Shelf
AND   items.damaged    = 0    -- Not damaged
AND   items.itemlost   = 0    -- Missing, long overdue, lost  
ORDER BY items.dateaccessioned DESC;


SELECT count(distinct items.itemnumber)
FROM items
LEFT JOIN issues     ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   issues.itemnumber IS NOT NULL;


SELECT count(distinct items.itemnumber)
FROM items
LEFT JOIN old_issues ON  (items.itemnumber = old_issues.itemnumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'CH'
AND   old_issues.itemnumber IS NOT NULL;