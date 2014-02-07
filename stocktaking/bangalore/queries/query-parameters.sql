-- Items which are on the shelf i.e. physically present in library
SELECT /* Items on shelf */
    items.itype as 'Item Type',
    biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	items.dateaccessioned as ' Accession Date',
    items.datelastseen as 'Last Seen Date',
	biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home branch>>
AND   items.datelastseen >= <<Inventory date>>
AND   items.itype = <<Item Type>>
AND   items.wthdrawn = 0
AND   items.itemlost = 0
AND   issues.itemnumber IS NULL
ORDER BY items.itype, items.dateaccessioned, items.datelastseen;


-- Items accessioned since given date
SELECT /* Items accessioned since given date */
    items.itype as 'Item Type',
    biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	items.dateaccessioned as ' Accession Date',
    items.datelastseen as 'Last Seen Date',
	biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
WHERE items.homebranch = <<Home branch>>
AND   items.dateaccessioned >= <<Inventory date>>
AND   items.itype = <<Item type>>
ORDER BY items.itype, items.dateaccessioned, items.datelastseen;

-- List of checked out
SELECT /* Checkouts */
    items.itype as 'Item Type',
    biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    issues.issuedate as 'Issue Date',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home branch>>
AND   items.wthdrawn = 0
AND   items.itemlost = 0
AND   items.itype = <<Item Type>>
AND   issues.itemnumber IS NOT NULL
ORDER BY items.itype, items.dateaccessioned, issues.issuedate;


/* List of all the withdrawn items */
SELECT /* Withdrawn */
    items.itype as 'Item Type',
    biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	items.dateaccessioned as ' Accession Date',
    items.datelastseen as 'Last Seen Date',
    items.wthdrawn as 'Withdrawn Code',
	biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
WHERE items.homebranch = <<Home branch>>
AND   items.wthdrawn != 0
AND   items.itype = <<Item Type>>
ORDER BY items.itype, items.dateaccessioned,  items.datelastseen;


/* List of all missing items  */
SELECT /* Lost */
    items.itype as 'Item Type',
    biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
	items.dateaccessioned as ' Accession Date',
    items.datelastseen as 'Last Seen Date',
    items.wthdrawn as 'Withdrawn Code',
	biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
WHERE items.homebranch = <<Home branch>>
AND   items.itemlost != 0
AND   items.itype = <<Item Type>>
ORDER BY items.itype, items.dateaccessioned,  items.datelastseen;


-- List of items which are issued out but also withdrawn
SELECT /* Checkouts and Withdrawn */
    items.itype as 'Item Type',
    biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
    items.dateaccessioned as ' Accession Date',  
    items.datelastseen as 'Last Seen Date',
    issues.issuedate as 'Issue Date',
    items.wthdrawn_date as 'Withdrawn Date',
    biblio.title as 'Title',
    biblio.author as 'Author'
FROM
items
LEFT JOIN biblio ON (items.biblionumber = biblio.biblionumber)
LEFT JOIN issues ON (items.itemnumber = issues.itemnumber)
WHERE items.homebranch = <<Home branch>>
AND   items.wthdrawn != 0
AND   items.itype = <<Item Type>>
AND   issues.itemnumber IS NOT NULL
ORDER BY items.itype, items.dateaccessioned, issues.issuedate;