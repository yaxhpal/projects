SELECT * FROM items  itemnumber = '86034'
select * from issues i where i.itemnumber='86034'
SELECT * FROM items itemnumber = '86034'
SELECT * FROM reserves WHERE (found = 'W' or found = 'T') AND itemnumber = '86034'
SELECT * FROM items WHERE itemnumber='86034'

INSERT INTO deleteditems SET biblioitemnumber = '80254',restricted = NULL,wthdrawn = '0',notforloan = '0',replacementpricedate = '2011-04-03',itemnumber = '86034',ccode = 'Books',itemnotes = NULL,location = NULL,itemcallnumber = ' R 428.14  JAK',stack = NULL,barcode = 'CH34741',itemlost = '0',uri = NULL,materials = NULL,datelastseen = '2011-04-03',price = NULL,issues = NULL,homebranch = 'CH',wthdrawn_date = NULL,replacementprice = NULL,more_subfields_xml = NULL,cn_source = NULL,booksellerid = NULL,biblionumber = '80254',renewals = NULL,holdingbranch = 'CH',timestamp = '2011-04-03 17:30:40',damaged = '0',stocknumber = NULL,cn_sort = 'R_428_140000000000000_JAK',reserves = NULL,enumchron = NULL,datelastborrowed = NULL,dateaccessioned = '2011-04-03',copynumber = NULL,permanent_location = NULL,itype = 'BK',paidfor = NULL,onloan = NULL


DELETE FROM items WHERE itemnumber='86034'
SELECT marcxml FROM biblioitems WHERE biblionumber='80254'
SELECT frameworkcode FROM biblio WHERE biblionumber='80254'
UPDATE deleteditems SET marc='00791nam a2200193Ia 4500001001100000008004100011020001500052082001900067245005900086260002400145300000900169650005900178700002000237906001400257852001400271952012900285999001700414952016600431CHBK000523110322s9999    xx            000 0 und d  a0521532981  a R 428.14  JAK  aStep up to IELTS: Student\'s Book ( Include IE 148-149)  aUKbCambridgec2004  a141p  aStep up to IELTS: Student\'s Book ( Include IE 148-149)  aVanessa,Jakeman  aENGbENGL  aENGbENGL  w2011-04-03pCH34741r2011-04-0340006R_428_140000000000000_JAK986034bCH10o R 428.14  JAKd2011-04-038Books70yBKaCH  c80254d80254  w2013-03-12pKB44804r2013-03-1240006R_428_140000000000000_JAK91052653bKD10oR 428.14  JAKd2013-03-12q2013-03-198NFIC70cGEN2ddcyEXs2013-03-12l1aKD' WHERE itemnumber='86034'
UPDATE biblio SET frameworkcode='' WHERE biblionumber='80254'
UPDATE biblioitems SET marc='00650nam a2200181Ia 4500001001100000008004100011020001500052082001900067245005900086260002400145300000900169650005900178700002000237906001400257852001400271999001700285952016600302CHBK000523110322s9999    xx            000 0 und d  a0521532981  a R 428.14  JAK  aStep up to IELTS: Student\'s Book ( Include IE 148-149)  aUKbCambridgec2004  a141p  aStep up to IELTS: Student\'s Book ( Include IE 148-149)  aVanessa,Jakeman  aENGbENGL  aENGbENGL  c80254d80254  w2013-03-12pKB44804r2013-03-1240006R_428_140000000000000_JAK91052653bKD10oR 428.14  JAKd2013-03-12q2013-03-198NFIC70cGEN2ddcyEXs2013-03-12l1aKD',marcxml='<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<record\n    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n    xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd\"\n    xmlns=\"http://www.loc.gov/MARC21/slim\">\n\n  <leader>00650nam a2200181Ia 4500</leader>\n  <controlfield tag=\"001\">CHBK000523</controlfield>\n  <controlfield tag=\"008\">110322s9999    xx            000 0 und d</controlfield>\n  <datafield tag=\"020\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">0521532981</subfield>\n  </datafield>\n  <datafield tag=\"082\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\"> R 428.14  JAK</subfield>\n  </datafield>\n  <datafield tag=\"245\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">Step up to IELTS: Student\'s Book ( Include IE 148-149)</subfield>\n  </datafield>\n  <datafield tag=\"260\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">UK</subfield>\n    <subfield code=\"b\">Cambridge</subfield>\n    <subfield code=\"c\">2004</subfield>\n  </datafield>\n  <datafield tag=\"300\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">141p</subfield>\n  </datafield>\n  <datafield tag=\"650\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">Step up to IELTS: Student\'s Book ( Include IE 148-149)</subfield>\n  </datafield>\n  <datafield tag=\"700\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">Vanessa,Jakeman</subfield>\n  </datafield>\n  <datafield tag=\"906\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">ENG</subfield>\n    <subfield code=\"b\">ENGL</subfield>\n  </datafield>\n  <datafield tag=\"852\" ind1=\" \" ind2=\" \">\n    <subfield code=\"a\">ENG</subfield>\n    <subfield code=\"b\">ENGL</subfield>\n  </datafield>\n  <datafield tag=\"999\" ind1=\" \" ind2=\" \">\n    <subfield code=\"c\">80254</subfield>\n    <subfield code=\"d\">80254</subfield>\n  </datafield>\n  <datafield tag=\"952\" ind1=\" \" ind2=\" \">\n    <subfield code=\"w\">2013-03-12</subfield>\n    <subfield code=\"p\">KB44804</subfield>\n    <subfield code=\"r\">2013-03-12</subfield>\n    <subfield code=\"4\">0</subfield>\n    <subfield code=\"0\">0</subfield>\n    <subfield code=\"6\">R_428_140000000000000_JAK</subfield>\n    <subfield code=\"9\">1052653</subfield>\n    <subfield code=\"b\">KD</subfield>\n    <subfield code=\"1\">0</subfield>\n    <subfield code=\"o\">R 428.14  JAK</subfield>\n    <subfield code=\"d\">2013-03-12</subfield>\n    <subfield code=\"q\">2013-03-19</subfield>\n    <subfield code=\"8\">NFIC</subfield>\n    <subfield code=\"7\">0</subfield>\n    <subfield code=\"c\">GEN</subfield>\n    <subfield code=\"2\">ddc</subfield>\n    <subfield code=\"y\">EX</subfield>\n    <subfield code=\"s\">2013-03-12</subfield>\n    <subfield code=\"l\">1</subfield>\n    <subfield code=\"a\">KD</subfield>\n  </datafield>\n</record>\n' WHERE biblionumber='80254'
SELECT COUNT(*) FROM 
WHERE server = 'biblioserver'
                         AND   biblio_auth_number = '80254'
                         AND   operation = 'specialUpdate'
                         AND   done = 0
INSERT INTO zebraqueue  (biblio_auth_number,server,operation) VALUES('80254','biblioserver','specialUpdate')
Insert into action_logs (timestamp,user,module,action,object,info) values (now(),'0','CATALOGUING','DELETE','86034','item')
		  