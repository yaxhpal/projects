-- Set Home branch 
set @homebranch  := 'CH';

-- Set inventory date
set @inventoryDate := '2013-11-10';


-- List of titles checkouts
SELECT /* checkouts */ 
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
WHERE items.homebranch = 'CH'
AND   items.itype = 'BK'
AND   items.wthdrawn = 0
AND   items.itemlost = 0
AND   issues.itemnumber IS NOT NULL
GROUP BY items.itemnumber
ORDER BY items.cn_sort , items.itemcallnumber , biblio.title;


-- List of items in items in process
SELECT /* items in process */ 
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
WHERE items.homebranch = 'CH'
AND   items.itype = 'BK'
AND   items.notforloan = 4
GROUP BY items.itemnumber
ORDER BY items.cn_sort , items.itemcallnumber , biblio.title;



-- List of withdrawn items
SELECT /* withdrawn items */ 
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
WHERE items.homebranch = 'CH'
AND   items.itype = 'BK'
AND   items.wthdrawn != 0
ORDER BY items.cn_sort , items.itemcallnumber , biblio.title;


-- List of missing items
SELECT /* missing items */ 
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
WHERE items.homebranch = 'CH'
AND   items.itype = 'BK'
AND   items.itemlost != 0
ORDER BY items.cn_sort , items.itemcallnumber , biblio.title;

-- Final list of items available in library
SELECT /* On shelf */   
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
WHERE items.homebranch = 'CH'
AND   items.itype = 'BK'
AND   items.datelastseen >= '2013-11-10' 
AND   issues.itemnumber IS NULL
AND   items.itemlost = 0
AND   items.wthdrawn  = 0
GROUP BY items.itemnumber
ORDER BY items.cn_sort , itemcallnumber , title;


-- List of duplicate titles
SELECT /* Duplicate titles */ GROUP_CONCAT(biblionumber SEPARATOR ', ') AS biblionumbers,
biblioitems.isbn, title, author
FROM biblio 
LEFT JOIN biblioitems USING (biblionumber) 
LEFT JOIN items USING (biblionumber)
WHERE items.homebranch = 'CH'
AND   items.itype = 'BK'
GROUP BY CONCAT(title,"/",author) 
HAVING COUNT(CONCAT(title,"/",author)) > 1;
