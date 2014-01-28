-- Generate total stock of DVD’s available in Bangalore
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BL'
ORDER BY items.dateaccessioned DESC;

-- List of titles issued out
SELECT items.barcode, biblio.title, biblio.author, items.datelastborrowed, items.datelastseen
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BL'
AND   items.onloan IS NOT NULL
ORDER BY items.datelastseen DESC;

-- BL84345

SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN issues     ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BL'
AND    (issues.itemnumber IS NOT NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;

-- List of items in the library
SELECT items.barcode, biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BL'
AND   items.onloan IS NULL
ORDER BY items.datelastseen DESC;


-- List of items not scanned
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BL'
AND   items.datelastseen < '2013-12-16'
ORDER BY items.dateaccessioned DESC;

-- List of titles missing
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BK'
AND   items.itemlost = 4 -- Missing items
ORDER BY items.dateaccessioned DESC;

-- List of duplicate titles
SELECT GROUP_CONCAT(biblionumber SEPARATOR ', ') AS biblionumbers, title, author
FROM biblio LEFT JOIN biblioitems USING (biblionumber) 
LEFT JOIN items USING (biblionumber)
WHERE items.homebranch = 'BL'
AND   items.itype = 'DVD'
GROUP BY CONCAT(title,"/",author) 
HAVING COUNT(CONCAT(title,"/",author)) > 1;


-- Final list of DVD’s available in library
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BL'
AND   items.datelastseen >= '2013-12-16'
UNION
SELECT items.barcode, biblio.title, biblio.author, items.dateaccessioned
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
WHERE items.itype = 'DVD'
AND   items.homebranch = 'BL'
AND   items.datelastseen < '2013-12-16'
AND	  items.onloan IS NOT NULL;




-- List of titles issued out
SELECT items.barcode, biblio.title, biblio.author, items.datelastseen, old_issues.issuedate, old_issues.returndate
FROM items 
LEFT JOIN biblio     ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN old_issues ON  (items.itemnumber = old_issues.itemnumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.onloan IS NULL
AND   old_issues.issuedate < @inventoryDate
AND   @inventoryDate <= old_issues.returndate
ORDER BY items.datelastseen DESC;


