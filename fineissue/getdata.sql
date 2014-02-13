SELECT issues.*, items.itype as itemtype, items.homebranch, items.barcode
     FROM issues 
LEFT JOIN items       USING (itemnumber)
    WHERE date_due < CURDATE();


SELECT
    issues.branchcode,
    borrowers.borrowernumber,
    borrowers.categorycode,
    borrowers.cardnumber,
    issues.itemnumber,
	items.biblionumber,
    items.itype,
    issues.issuedate,
    issues.date_due,
    accountlines.amount,
    accountlines.amountoutstanding,
    accountlines.accounttype,
    accountlines.date,
    accountlines.description
FROM
issues
LEFT JOIN accountlines ON (issues.borrowernumber = accountlines.borrowernumber AND issues.itemnumber = accountlines.itemnumber)
LEFT JOIN borrowers ON (issues.borrowernumber = borrowers.borrowernumber)
LEFT JOIN items ON (issues.itemnumber = items.itemnumber)
WHERE issues.date_due < CURDATE()
ORDER BY issues.date_due DESC;


SELECT borrowernumber FROM  issues WHERE issues.date_due < CURDATE();


SELECT 
    issues.branchcode,
    issues.borrowernumber,
    issues.itemnumber,
    issues.issuedate,
    issues.date_due,
    accountlines.amount,
    accountlines.amountoutstanding,
    accountlines.accounttype,
    accountlines.date,
    accountlines.description
FROM
    issues
        LEFT JOIN
    accountlines ON (issues.borrowernumber = accountlines.borrowernumber
        AND issues.itemnumber = accountlines.itemnumber)
WHERE
    issues.date_due < CURDATE()
        AND issues.branchcode = 'CL'
GROUP BY issues.borrowernumber
ORDER BY issues.date_due DESC;





select 
    *
from
    accountlines
        left join
    old_issues ON (accountlines.borrowernumber = old_issues.borrowernumber)
where
    old_issues.returndate = '2014-02-05'
        and old_issues.date_due < old_issues.returndate
        and old_issues.branchcode = 'CL'
        and accountlines.itemnumber = old_issues.itemnumber
order by accountlines.accountno desc;



select 
    borrowers.cardnumber,
    borrowernumber,
    borrowers.branchcode,
    itemnumber,
    issuedate,
    date_due,
    returndate
from
    old_issues
        left join
    borrowers USING (borrowernumber)
where
    borrowers.cardnumber in ('ACL014183' , 'ACL014840',
        'ACL015219',
        'ACL015894',
        'ACL016595',
        'ACL016780',
        'ACL016997',
        'ACL017563',
        'ACL017798',
        'ACL018833',
        'ACL019383',
        'ACL019393',
        'ACL019651',
        'ACL020136',
        'ACL020285',
        'ACL020691',
        'ACL020867',
        'ACL020932',
        'ACL021074')
        and old_issues.returndate > '2014-01-25';

select 
    *
from
    old_issues
where
    returndate = '2014-02-05'
        and date_due < returndate
        and branchcode = 'CL';

select 
    *
from
    old_issues
where
    returndate = '2014-02-10'
        and date_due < returndate
        and branchcode = 'CL';



SELECT 
    *
FROM
    issues
WHERE
    date_due < '2014-02-06'
        and branchcode = 'CL'
        and issuedate > '2014-01-01'
order by date_due desc;

select 
    *
from
    accountlines
where
    borrowernumber = '269554'
        and itemnumber = '968429'
order by accountno
limit 1;-- not found
select 
    *
from
    accountlines
where
    borrowernumber = '326733'
        and itemnumber = '213023'
order by accountno
limit 1;-- not found
select 
    *
from
    accountlines
where
    borrowernumber = '145704'
        and itemnumber = '982308'
order by accountno
limit 1;-- found
select 
    *
from
    accountlines
where
    borrowernumber = '123354'
        and itemnumber = '226960'
order by accountno
limit 1;-- found
select 
    *
from
    accountlines
where
    borrowernumber = '208736'
        and itemnumber = '237971'
order by accountno
limit 1;-- found
select 
    *
from
    accountlines
where
    borrowernumber = '311538'
        and itemnumber = '226960'
order by accountno
limit 1;-- found
select 
    *
from
    accountlines
where
    borrowernumber = '309656';
select 
    *
from
    accountlines
where
    borrowernumber = '326733';

SELECT 
    accountno + 1
FROM
    accountlines
WHERE
    (borrowernumber = '326733')
ORDER BY accountno DESC
LIMIT 1;


SELECT 
    accountlines . *,
    issues . *,
    items.itype as itemtype,
    items.homebranch,
    items.barcode
FROM
    issues
        LEFT JOIN
    items USING (itemnumber)
        LEFT JOIN
    accountlines USING (borrowernumber)
WHERE
    issues.date_due < CURDATE()
        AND accountlines.itemnumber = issues.itemnumber
        AND issues.issuedate > '2014-01-17';


select 
    *
from
    borrowers
where
    cardnumber in ('ACL014183' , 'ACL016997',
        'ACL021074',
        'ACL017563',
        'ACL019651',
        'ACL018833',
        'ACL016595',
        'ACL015219',
        'ACL020867',
        'ACL017798',
        'ACL014840',
        'ACL020136',
        'ACL020932',
        'ACL020285',
        'ACL015894');

select 
    borrowers.cardnumber, items . *
from
    items
        left join
    old_issues ON (items.itemnumber = old_issues.itemnumber)
        left join
    borrowers ON (old_issues.borrowernumber = borrowers.borrowernumber)
where
    borrowers.cardnumber in ('ACL014183' , 'ACL016997',
        'ACL021074',
        'ACL017563',
        'ACL019651',
        'ACL018833',
        'ACL016595',
        'ACL015219',
        'ACL020867',
        'ACL017798',
        'ACL014840',
        'ACL020136',
        'ACL020932',
        'ACL020285',
        'ACL015894')
        and old_issues.returndate > '2014-02-01'
ORDER BY borrowers.cardnumber;




select 
    issues.branchcode,
    issues.borrowernumber,
    issues.itemnumber,
    issues.issuedate,
    issues.date_due,
    accountlines.amount,
    accountlines.amountoutstanding,
    accountlines.accounttype,
    accountlines.date,
    accountlines.description
from
    issues
        left join
    accountlines USING (borrowernumber)
where
    accountlines.accounttype in ('F' , 'FU')
        and issues.date_due between '2014-01-17' and CURDATE()
        and accountlines.timestamp > '2014-01-01 00:00:00'
group by issues.borrowernumber
order by issues.branchcode , issues.borrowernumber;



SELECT 
    issues . *,
    items.itype as itemtype,
    items.homebranch,
    items.barcode
FROM
    issues
        LEFT JOIN
    items USING (itemnumber)
WHERE
    date_due < CURDATE();

SELECT 
    borrowers.cardnumber,
    borrowers.borrowernumber,
    borrowers.categorycode,
    items.itemnumber,
    items.itype,
    old_issues.date_due,
    old_issues.returndate,
    items.homebranch,
    items.holdingbranch,
    accountlines.amount,
    accountlines.amountoutstanding,
    accountlines.accounttype,
    accountlines.date,
    accountlines.description,
    biblio.title
FROM
    borrowers
        LEFT JOIN
    old_issues USING (borrowernumber)
        LEFT JOIN
    items ON (old_issues.itemnumber = items.itemnumber)
        LEFT JOIN
    biblio ON (items.biblionumber = biblio.biblionumber)
        LEFT JOIN
    accountlines USING (borrowernumber)
WHERE
    borrowers.cardnumber IN ('ACL014183' , 'ACL016997',
        'ACL021074',
        'ACL017563',
        'ACL019651',
        'ACL018833',
        'ACL016595',
        'ACL015219',
        'ACL020867',
        'ACL017798',
        'ACL014840',
        'ACL020136',
        'ACL020932',
        'ACL020285',
        'ACL015894')
        AND old_issues.date_due < old_issues.returndate
        AND old_issues.returndate > '2014-01-25'
ORDER BY borrowers.borrowernumber;



SELECT 
    borrowers.cardnumber,
    borrowers.borrowernumber,
    accountlines.itemnumber,
    accountlines.date,
    accountlines.description,
    accountlines.accounttype,
    accountlines.amount,
    accountlines.amountoutstanding
FROM
    accountlines
        LEFT JOIN
    borrowers USING (borrowernumber)
WHERE
    borrowers.cardnumber IN ('ACL014183' , 'ACL016997',
        'ACL021074',
        'ACL017563',
        'ACL019651',
        'ACL018833',
        'ACL016595',
        'ACL015219',
        'ACL020867',
        'ACL017798',
        'ACL014840',
        'ACL020136',
        'ACL020932',
        'ACL020285',
        'ACL015894')
        AND accountlines.date > '2014-01-01'
ORDER BY borrowers.borrowernumber;





SELECT 
    old_issues.borrowernumber,
    borrowers.cardnumber,
    borrowers.categorycode,
    old_issues.itemnumber,
    old_issues.branchcode,
    items.itype,
    items.homebranch,
    items.holdingbranch,
    old_issues.date_due as 'Due Date',
    old_issues.returndate as 'Return Date'
FROM
    old_issues
        LEFT JOIN
    borrowers USING (borrowernumber)
        LEFT JOIN
    items USING (itemnumber)
        LEFT JOIN
    accountlines USING (borrowernumber)
WHERE
    old_issues.date_due BETWEEN '2014-01-25' AND '2014-02-10'
        AND old_issues.date_due < old_issues.returndate
        AND items.homebranch = 'CL'
        AND accountlines.amount = 0
ORDER BY old_issues.borrowernumber , old_issues.returndate DESC;


select 
    issues.borrowernumber,
    issues.itemnumber,
    issues.issuedate,
    issues.date_due
from
    issues
where
    issues.branchcode = 'CL'
        and issues.date_due BETWEEN '2014-01-25' AND '2014-02-09'
order by issues.borrowernumber;


SELECT 
    *
FROM
    accountlines
WHERE
    borrowernumber = '119452'
        AND itemnumber = '268345';


SELECT 
    *
FROM
    accountlines
WHERE
    borrowernumber = '313062';




select 
    borrowernumber
from
    borrowers
where
    cardnumber = 'ACL016997';





SELECT 
    old_issues.borrowernumber,
    borrowers.cardnumber,
    borrowers.categorycode,
    old_issues.itemnumber,
    old_issues.branchcode,
    items.itype,
    items.homebranch,
    items.holdingbranch,
    FORMAT(DATE(old_issues.date_due), 'dd MMM YYYY') as 'Issue Date',
    FORMAT(DATE(old_issues.returndate),
            'dd MMM YYYY') as 'Return Date'
FROM
    old_issues
        LEFT JOIN
    items USING (itemnumber)
        LEFT JOIN
    borrowers USING (borrowernumber)
        LEFT JOIN
    accountlines USING (borrowernumber)
WHERE
    old_issues.date_due BETWEEN '2014-01-25' AND '2014-02-10'
        AND old_issues.date_due < old_issues.returndate
        AND items.homebranch = 'CL'
        AND accountlines.amount = 0
ORDER BY old_issues.borrowernumber , old_issues.returndate DESC;


SELECT 
    borrowernumber,
    itemnumber,
    issuingbranch,
    issuedate,
    date_due,
    returndate
FROM
    old_issues
WHERE
    date_due < returndate
        AND date_due BETWEEN '2014-01-25' AND '2014-02-10'
        AND branchcode = 'CL'
ORDER BY returndate DESC;



SELECT 
    borrowers.borrowernumber,
    borrowers.surname,
    borrowers.firstname,
    borrowers.phone,
    borrowers.email,
    biblio.title,
    biblio.biblionumber,
    issues.date_due,
    issues.returndate,
    issues.branchcode,
    branches.branchname,
    items.barcode,
    items.itemcallnumber,
    items.location,
    items.itemnumber,
    accountlines.notify_id,
    accountlines.notify_level,
    accountlines.amountoutstanding
FROM
    accountlines
        LEFT JOIN
    issues ON issues.itemnumber = accountlines.itemnumber
        AND issues.borrowernumber = accountlines.borrowernumber
        LEFT JOIN
    borrowers ON borrowers.borrowernumber = accountlines.borrowernumber
        LEFT JOIN
    items ON items.itemnumber = issues.itemnumber
        LEFT JOIN
    biblio ON biblio.biblionumber = items.biblionumber
        LEFT JOIN
    biblioitems ON biblioitems.biblioitemnumber = items.biblioitemnumber
        LEFT JOIN
    branches ON branches.branchcode = issues.branchcode
WHERE
    (accountlines.amountoutstanding != '0.000000')
        AND (accountlines.accounttype = 'FU')
        AND (issues.branchcode = 'CL')
        AND (issues.date_due < CURDATE());