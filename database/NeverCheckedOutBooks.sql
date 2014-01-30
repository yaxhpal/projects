-- Never checked out Books  (Yashpal-DB = 5474)

SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   (issues.itemnumber IS NULL AND old_issues.itemnumber IS NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;


-- Never checked out Books excluding notforloan, withdrawn, damaged and lost  (Yashpal-DB = 3219)
SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   items.notforloan = 0  -- Could not be issued
AND   items.wthdrawn = 0     -- On Shelf
AND   items.damaged = 0      -- Not damaged
AND   items.itemlost = 0     -- Not missing, long overdue, lost 
AND   (issues.itemnumber IS NULL AND old_issues.itemnumber IS NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;

-- Not For Loan (Yashpal-DB = 19)
SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   items.notforloan != 0  -- Could not be issued
AND   items.wthdrawn = 0     -- On Shelf
AND   items.damaged = 0      -- Not damaged
AND   items.itemlost = 0     -- Not missing, long overdue, lost 
AND   (issues.itemnumber IS NULL AND old_issues.itemnumber IS NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;

-- Withdrawn Books (Yashpal-DB = 1359)
SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   items.notforloan = 0    -- Could be issued
AND   items.wthdrawn != 0     -- Not on Shelf
AND   items.damaged = 0       -- Not damaged
AND   items.itemlost = 0      -- Not missing, long overdue, lost 
AND   (issues.itemnumber IS NULL AND old_issues.itemnumber IS NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;

-- Damaged Books (Yashpal-DB = 8)
SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   items.notforloan = 0    -- Could be issued
AND   items.wthdrawn = 0      -- On Shelf
AND   items.damaged != 0      -- Damaged
AND   items.itemlost = 0      -- Not missing, long overdue, lost 
AND   (issues.itemnumber IS NULL AND old_issues.itemnumber IS NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;

-- Lost Books (Yashpal-DB = 860)
SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   items.notforloan = 0    -- Could be issued
AND   items.wthdrawn = 0      -- On Shelf
AND   items.damaged = 0       -- Not damaged
AND   items.itemlost != 0     -- Missing, long overdue, lost 
AND   (issues.itemnumber IS NULL AND old_issues.itemnumber IS NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;

-- Lost and withdrawn Books (Yashpal-DB = 9)
SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   items.notforloan = 0    -- Could be issued
AND   items.wthdrawn != 0     -- Not on Shelf
AND   items.damaged = 0       -- Not damaged
AND   items.itemlost != 0     -- Missing, long overdue, lost  
AND   (issues.itemnumber IS NULL AND old_issues.itemnumber IS NULL)
GROUP BY items.itemnumber -- Select only distinct
ORDER BY items.dateaccessioned DESC;
