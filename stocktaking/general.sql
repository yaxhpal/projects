-- Set Home branch 
set @homebranch := 'BL';

-- Set item type
set @itemtype := 'DVD';

-- Set inventory date
set @inventoryDate = '2013-12-16';


-- CREATE TEMPORARY TABLE IF NOT EXISTS onloanOnInventoryDate AS (
-- SELECT DISTINCT itemnumber
-- FROM   old_issues 
-- WHERE  old_issues.branchcode = @homebranch 
-- AND    old_issues.issuedate  < @inventoryDate 
-- AND    old_issues.returndate > @inventoryDate);
-- 
CREATE TEMPORARY TABLE IF NOT EXISTS issuedAndNotReturned AS (
SELECT DISTINCT itemnumber
FROM   issues 
WHERE  issues.branchcode = @homebranch  
AND    issues.issuedate  < @inventoryDate );

-- Generate total stock of given item available in given Branch
SELECT /* General stock */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
ORDER BY items.datelastseen DESC;


-- List of titles issued out
SELECT /* Issued out */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issuedAndNotReturned  ON  (items.itemnumber = issuedAndNotReturned.itemnumber)
WHERE items.itype = @itemtype
AND   items.homebranch = @homebranch
AND   issuedAndNotReturned.itemnumber IS NOT NULL
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;


-- List of items in the library
SELECT /* In the Library */ items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issuedAndNotReturned  ON  (items.itemnumber = issuedAndNotReturned.itemnumber)
WHERE items.itype      = @itemtype
AND   items.homebranch = @homebranch
AND   issuedAndNotReturned.itemnumber IS NULL
AND   items.datelastseen >= @inventoryDate
AND   items.wthdrawn = 0
AND   items.itemlost = 0
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;


-- List of items scanned
SELECT /* Scanned */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.datelastseen =  @inventoryDate
GROUP BY items.itemnumber
ORDER BY items.biblionumber;


-- List of items not scanned
SELECT /* Not scanned */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issuedAndNotReturned  ON  (items.itemnumber = issuedAndNotReturned.itemnumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.datelastseen <  @inventoryDate
AND   issuedAndNotReturned.itemnumber IS NULL
AND   items.wthdrawn = 0
AND   items.itemlost = 0
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;


-- List of items not scanned and missing
SELECT /* Not scanned and missing */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issuedAndNotReturned  ON  (items.itemnumber = issuedAndNotReturned.itemnumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.datelastseen <  @inventoryDate
AND   issuedAndNotReturned.itemnumber IS NULL 
AND   items.itemlost = 4
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;


-- List of duplicate titles
SELECT /* Duplicate titles */ GROUP_CONCAT(biblionumber SEPARATOR ', ') AS biblionumbers,
biblioitems.isbn, title, author
FROM biblio 
LEFT JOIN biblioitems USING (biblionumber) 
LEFT JOIN items USING (biblionumber)
WHERE items.homebranch = @homebranch
AND   items.itype = @itemtype 
GROUP BY CONCAT(title,"/",author) 
HAVING COUNT(CONCAT(title,"/",author)) > 1;


-- Final list of items available in library
SELECT /* Final list of available items */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issuedAndNotReturned  ON  (items.itemnumber = issuedAndNotReturned.itemnumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.itemlost = 0
AND   items.wthdrawn = 0
AND   (( items.datelastseen >= @inventoryDate) OR (issuedAndNotReturned.itemnumber IS NOT NULL))
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;

DROP TABLE issuedAndNotReturned;
