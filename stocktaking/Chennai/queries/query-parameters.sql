/*All the items whether on shelf or issued*/
SELECT /* Total Stock */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    items.homebranch as 'Library Code',
    (CASE WHEN issues.itemnumber IS NULL THEN 'On shelf'
	ELSE 'Issued' 
    END) as 'Status',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.itype LIKE <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.wthdrawn = 0
AND   items.itemlost = 0
ORDER BY items.itype, items.datelastseen DESC, items.dateaccessioned;

-- Items which are on the shelf i.e. physically present in library
SELECT /* Items on shelf */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.datelastseen >= <<Inventory date>>
AND   items.itype LIKE <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.wthdrawn = 0
AND   items.itemlost = 0
AND   issues.itemnumber IS NULL
ORDER BY items.itype, items.datelastseen DESC, items.dateaccessioned;

-- List of checked out
SELECT /* Checkouts */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    issues.issuedate as 'Issue Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.itype like <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.wthdrawn = 0
AND   items.itemlost = 0
AND   issues.itemnumber IS NOT NULL
ORDER BY items.itype, issues.issuedate DESC, items.dateaccessioned;

/* All the items of given type and belonging to given library
   which could not be located since given inventory date. */
SELECT /* Could not be located */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.datelastseen < <<Inventory date>>
AND   items.itype LIKE <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.wthdrawn = 0
AND   items.itemlost = 0
AND   issues.itemnumber IS NULL
ORDER BY items.itype, items.datelastseen DESC, items.dateaccessioned;


/* List of all the withdrawn items */
SELECT /* Withdrawn */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.itype LIKE <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.wthdrawn != 0
AND   items.itemlost = 0
AND   issues.itemnumber IS NULL
ORDER BY items.itype, items.dateaccessioned DESC,  items.datelastseen;

/* List of all missing items  */
SELECT /* Lost */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.itype LIKE <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.wthdrawn = 0
AND   items.itemlost != 0
AND   issues.itemnumber IS NULL
ORDER BY items.itype, items.datelastseen DESC, items.dateaccessioned;

/* List of all item in process  */
SELECT /* Item in process */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.itype LIKE <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.notforloan = 4
ORDER BY items.itype, items.dateaccessioned DESC, items.datelastseen;

-- Items accessioned since given date
SELECT /* Items accessioned since given date */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.dateaccessioned >= <<Inventory date>>
AND   items.itype LIKE <<Enter item type (% for any)>>
ORDER BY items.itype, items.dateaccessioned, items.datelastseen;


-- List of items which are issued out but also withdrawn
SELECT /* Checkouts and Withdrawn */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
	biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	biblioitems.isbn as 'ISBN',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
	issues.issuedate as 'Issue Date',
    items.homebranch as 'Library Code',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN biblioitems ON  (items.biblionumber = biblioitems.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Library|branches>>
AND   items.itype LIKE <<Enter item type (% for any)>>
AND   items.itype NOT LIKE 'EBRA%'
AND   items.wthdrawn != 0
AND   items.itemlost = 0
AND   issues.itemnumber IS NOT NULL
ORDER BY items.itype, items.dateaccessioned,  items.datelastseen;