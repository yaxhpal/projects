-- Set Home branch 
set @homebranch := 'BL';

-- Set item type
set @itemtype := 'DVD';

-- Set inventory date
set @inventoryDate = '2013-12-16';


-- Generate total stock of given item available in given Branch
SELECT /* General stock */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = <<Item Type>> 
AND   items.homebranch = <<Homebranch>>
ORDER BY items.datelastseen DESC;


-- List of titles issued out
SELECT /* Issued out */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype = <<Item Type>>
AND   items.homebranch = <<Homebranch>>
AND   issues.itemnumber IS NOT NULL
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;


-- List of items in the library including scanned items
SELECT /* In the Library */ items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype      = <<Item Type>>
AND   items.homebranch = <<Homebranch>>
AND   issues.itemnumber IS NULL
AND   items.datelastseen >= <<Inventory Date>>
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;


-- List of items scanned
-- SELECT /* Scanned */ items.barcode, biblioitems.isbn, items.biblionumber,
-- biblio.title, biblio.author
-- FROM items 
-- LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
-- LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
-- WHERE items.itype = <<Item Type>> 
-- AND   items.homebranch = <<Homebranch>>
-- AND   items.datelastseen =  << Inventory Date>>
-- GROUP BY items.itemnumber
-- ORDER BY items.biblionumber;


-- List of items not scanned
SELECT /* Not scanned */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype = <<Item Type>> 
AND   items.homebranch = <<Homebranch>>
AND   items.datelastseen <  << Inventory Date (YYYY-MM-DD)>>
AND   issues.itemnumber IS NULL
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;


-- List of items not scanned and missing
SELECT /* Not scanned and missing */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype = <<Item Type>> 
AND   items.homebranch = <<Homebranch>>
AND   items.datelastseen <  << Inventory Date (YYYY-MM-DD)>>
AND   issues.itemnumber IS NULL
AND   items.itemlost = 0
AND   items.wthdrawn = 0
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;

-- List of items not scanned and missing
SELECT /* Not scanned and missing */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype = <<Item Type>> 
AND   items.homebranch = <<Homebranch>>
AND   items.datelastseen <  << Inventory Date (YYYY-MM-DD)>>
AND   issues.itemnumber IS NULL
AND   ( items.itemlost != 0 OR items.wthdrawn != 0)
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;


-- List of duplicate titles
SELECT /* Duplicate titles */ GROUP_CONCAT(biblionumber SEPARATOR ', ') AS biblionumbers,
biblioitems.isbn, title, author
FROM biblio 
LEFT JOIN biblioitems USING (biblionumber) 
LEFT JOIN items USING (biblionumber)
WHERE items.homebranch = <<Homebranch>>
AND   items.itype = <<Item Type>> 
GROUP BY CONCAT(title,"/",author) 
HAVING COUNT(CONCAT(title,"/",author)) > 1;


-- Final list of items available in library
SELECT /* Final list of available items */ items.barcode, biblioitems.isbn, items.biblionumber,
biblio.title, biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.itype = <<Item Type>> 
AND   items.homebranch = <<Homebranch>>
AND   (( items.datelastseen >= << Inventory Date (YYYY-MM-DD)>>) OR (issues.itemnumber IS NOT NULL))
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;
