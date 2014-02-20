SELECT cardnumber 
FROM borrowers 
JOIN user_permissions USING (borrowernumber) 
JOIN permissions USING (code) 
UNION ( SELECT cardnumber FROM borrowers WHERE flags > 0);
