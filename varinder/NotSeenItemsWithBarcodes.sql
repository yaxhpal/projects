SELECT /* Could not be located */
    items.itype as 'Item Type',
    items.itemnumber as 'Accession Number',
    biblio.biblionumber as 'Biblio Number',
    items.barcode as 'Barcode',
    biblioitems.isbn as 'ISBN',
	items.callnumber as 'Call Number',
    items.cn_source as 'Classification',
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
WHERE items.homebranch = 'CH'
AND   items.datelastseen < '2013-11-10'
AND   items.wthdrawn = 0
AND   items.itemlost = 0
AND   items.barcode IS NOT NULL
AND   items.barcode  !=''
AND   items.itype NOT LIKE 'EBRA%'
AND   issues.itemnumber IS NULL
GROUP BY items.itemnumber
ORDER BY items.itype, items.dateaccessioned, items.datelastseen DESC;