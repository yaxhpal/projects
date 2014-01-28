-- Set Home branch 
set @homebranch := 'CH';

-- Set item type
set @itemtype := 'BK';

-- Set inventory date
set @inventoryDate = '2013-11-10';

-- Generate total stock of given item available in given Branch
SELECT items.barcode, biblioitems.isbn, items.biblionumber, biblio.title, 
biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
ORDER BY items.datelastseen DESC;

-- List of titles issued out
SELECT items.barcode, biblioitems.isbn, items.biblionumber, biblio.title, 
biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
WHERE items.itype = @itemtype
AND   items.homebranch = @homebranch
AND   ((issues.itemnumber IS NOT NULL
AND   issues.issuedate < @inventoryDate)
OR    (old_issues.itemnumber IS NOT NULL
AND   (old_issues.issuedate < @inventoryDate
AND   @inventoryDate < old_issues.returndate)))
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;


-- List of items in the library
SELECT items.barcode, biblioitems.isbn, items.biblionumber, biblio.title, 
biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   ((issues.itemnumber IS NULL
OR    issues.issuedate > @inventoryDate))
AND   ((old_issues.itemnumber IS NULL
OR    (old_issues.returndate < @inventoryDate
OR    old_issues.issuedate > @inventoryDate)))
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;

-- List of items scanned
SELECT items.barcode, biblioitems.isbn, items.biblionumber, biblio.title, 
biblio.author
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.datelastseen =  @inventoryDate;

-- List of items not scanned
SELECT items.barcode, biblioitems.isbn, items.biblionumber, biblio.title, 
biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues     ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues ON  (items.itemnumber = old_issues.itemnumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.datelastseen !=  @inventoryDate
AND   ((issues.itemnumber IS NULL
OR    issues.issuedate > @inventoryDate))
AND   ((old_issues.itemnumber IS NULL
OR    (old_issues.returndate < @inventoryDate
OR    old_issues.issuedate > @inventoryDate)))
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;


-- List of titles missing
SELECT items.barcode, biblioitems.isbn, items.biblionumber, biblio.title, 
biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   items.itemlost = 4
ORDER BY items.dateaccessioned DESC;



-- List of duplicate titles
SELECT GROUP_CONCAT(biblionumber SEPARATOR ', ') AS biblionumbers, biblioitems.isbn, title, author
FROM biblio 
LEFT JOIN biblioitems USING (biblionumber) 
LEFT JOIN items USING (biblionumber)
WHERE items.homebranch = @homebranch
AND   items.itype = @itemtype 
GROUP BY CONCAT(title,"/",author) 
HAVING COUNT(CONCAT(title,"/",author)) > 1;


-- Final list of books available in library
SELECT items.barcode, biblioitems.isbn, items.biblionumber, biblio.title, 
biblio.author, items.datelastseen
FROM items 
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = @itemtype 
AND   items.homebranch = @homebranch
AND   ( ( items.datelastseen >= @inventoryDate) 
OR (items.datelastseen < @inventoryDate AND items.onloan IS NOT NULL))
GROUP BY items.itemnumber
ORDER BY items.datelastseen DESC;