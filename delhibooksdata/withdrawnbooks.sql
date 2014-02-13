SELECT items.itemnumber as "Accession Number",
items.barcode as "Barcode",
biblioitems.isbn as "ISBN",
items.biblionumber as "Biblio Number",
items.itemcallnumber as "Item Call Number", 
biblio.title as "Title",
biblio.author as "Author",
items.dateaccessioned as "Accession Date",
(CASE(items.wthdrawn)
WHEN  1 THEN "Reason not specified"  
WHEN  2 THEN "Backdated edition"
WHEN  3 THEN "Copy damaged"
WHEN  4 THEN "Missing for long time"
WHEN  5 THEN "Later edition avilable"
WHEN  6 THEN "Less used"
WHEN  8 THEN "Lost & replaced for"
WHEN  9 THEN "Multiple copies"
WHEN 10 THEN "Out of policy"
WHEN 11 THEN "Superceded edition"
WHEN 13 THEN "Outdated"
ELSE 
 CASE(items.itemlost)
 WHEN 1 THEN "Lost"
 WHEN 3 THEN "Lost and Paid For"
 ELSE
 CASE(items.damaged)
 WHEN 1 THEN "Damaged"
 ELSE
 "On Shelf"
 END
 END
END) as "Status"  
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   (items.wthdrawn NOT IN (0,7,12) OR items.itemlost NOT IN (0, 2, 4) OR  items.damaged != 0)
AND   issues.itemnumber IS NULL
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;

/* ################################################################################################## */


SELECT items.barcode, biblioitems.isbn, items.biblionumber, 
biblio.title, biblio.author,items.dateaccessioned,
(CASE(items.wthdrawn)
WHEN  1 THEN "Reason not specified"  
WHEN  2 THEN "Backdated edition"
WHEN  3 THEN "Copy damaged"
WHEN  4 THEN "Missing for long time"
WHEN  5 THEN "Later edition avilable"
WHEN  6 THEN "Less used"
-- WHEN  7 THEN "Long overdue"
WHEN  8 THEN "Lost & replaced for"
WHEN  9 THEN "Multiple copies"
WHEN 10 THEN "Out of policy"
WHEN 11 THEN "Superceded edition"
-- WHEN 12 THEN "Withdrawn: Others"
WHEN 13 THEN "Outdated"
ELSE 
 CASE(items.itemlost)
 WHEN 1 THEN "Lost"
 -- WHEN 2 THEN "Long Overdue (Lost)"
 WHEN 3 THEN "Lost and Paid For"
 -- WHEN 4 THEN "Missing"
 ELSE
 CASE(items.damaged)
 WHEN 1 THEN "Damaged"
 ELSE
 "On Shelf"
 END
 END
END) as "Status"  
FROM items 
LEFT JOIN issues      ON  (items.itemnumber = issues.itemnumber)
LEFT JOIN old_issues  ON  (items.itemnumber = old_issues.itemnumber)
LEFT JOIN biblio      ON  (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.itype = 'BK'
AND   items.homebranch = 'DL'
AND   (items.wthdrawn NOT IN (0,7,12) OR items.itemlost NOT IN (0, 2, 4) OR  items.damaged != 0)
AND   (issues.itemnumber IS NOT NULL OR old_issues.itemnumber IS NOT NULL)
GROUP BY items.itemnumber
ORDER BY items.dateaccessioned DESC;


/* 

1)  Withdrawn
    6) Backdated edition
    2) Copy Damaged
   11) Missing for long time
    7) Later edition available
    8) Less used
   10) Lost and replaced for
   16) Multiple copies
   12) Out of policy
   13) Superceded edition
   14) Outdated
   15) Reason not specified
   12 THEN "Withdrawn: Others"


3)  Damaged
4)  Lost
    5)  Withdrawn and lost
    9)  Lost and paid for

*/
