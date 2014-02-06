-- Set Home branch 
set @homebranch  := 'CH';

-- Set inventory date
set @inventoryDate := '2013-11-10';


-- Generate total stock of given item available in given Branch
SELECT /* General stock */ 
    items.itemnumber,
	biblio.biblionumber,
	biblioitems.isbn,
    items.barcode,
    items.itype,
    items.itemcallnumber,
    biblio.title,
    biblio.author,
    items.datelastseen
FROM items
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.homebranch = <<Home Branch>>
ORDER BY items.cn_sort , itemcallnumber , title;

-- List of titles issued out
SELECT /* Issued out */ 
    items.itemnumber,
	biblio.biblionumber,
	biblioitems.isbn,
    items.barcode,
    items.itype,
    items.itemcallnumber,
    biblio.title,
    biblio.author,
    items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home Branch>>
AND   issues.itemnumber IS NOT NULL
GROUP BY items.itemnumber
ORDER BY items.cn_sort , itemcallnumber , title;


-- List of items in the library including scanned items
SELECT /* In the Library */ 
	items.itemnumber,
	biblio.biblionumber,
	biblioitems.isbn,
    items.barcode,
    items.itype,
    items.itemcallnumber,
    biblio.title,
    biblio.author,
    items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home Branch>>
AND   issues.itemnumber IS NULL
AND   items.datelastseen >= <<Inventory Date>>
GROUP BY items.itemnumber
ORDER BY items.cn_sort , itemcallnumber , title;


-- List of items not scanned and are neither marked missing nor withdrawn i.e. coluld not be located
SELECT /* Not scanned */  
	items.itemnumber,
	biblio.biblionumber,
	biblioitems.isbn,
    items.barcode,
    items.itype,
    items.itemcallnumber,
    biblio.title,
    biblio.author,
    items.datelastseen
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
ORDER BY items.cn_sort , itemcallnumber , title;

-- List of items not scanned but marked missing or withdrawn
SELECT /* Not scanned but marked missing or withdrawn */   
	items.itemnumber,
	biblio.biblionumber,
	biblioitems.isbn,
    items.barcode,
    items.itype,
    items.itemcallnumber,
    biblio.title,
    biblio.author,
    items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home Branch>>
AND   (items.datelastseen <  <<Inventory Date>> OR datelastseen IS NULL)
AND   issues.itemnumber IS NULL
AND   ( items.itemlost != 0 OR items.wthdrawn != 0)
GROUP BY items.itemnumber
ORDER BY items.cn_sort , itemcallnumber , title;


-- Final list of items available in library
SELECT /* Final list of available items */   
	items.itemnumber,
	biblio.biblionumber,
	biblioitems.isbn,
    items.barcode,
    items.itype,
    items.itemcallnumber,
    biblio.title,
    biblio.author,
    items.datelastseen
FROM items 
LEFT JOIN biblio                ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems           ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues  				ON  (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home Branch>>
AND   (( items.datelastseen >= @inventoryDate) OR (issues.itemnumber IS NOT NULL))
GROUP BY items.itemnumber
ORDER BY items.cn_sort , itemcallnumber , title;



-- List of duplicate titles
SELECT /* Duplicate titles */ GROUP_CONCAT(biblionumber SEPARATOR ', ') AS biblionumbers,
biblioitems.isbn, title, author
FROM biblio 
LEFT JOIN biblioitems USING (biblionumber) 
LEFT JOIN items USING (biblionumber)
WHERE items.homebranch = <<Home Branch>>
GROUP BY CONCAT(title,"/",author) 
HAVING COUNT(CONCAT(title,"/",author)) > 1;
