SELECT cardnumber 
FROM borrowers 
JOIN user_permissions USING (borrowernumber) 
JOIN permissions USING (code) 
UNION (
      SELECT cardnumber
      FROM borrowers 
      WHERE flags > 0);


SELECT  cardnumber, borrowernumber FROM borrowers WHERE cardnumber IN ('ADL013916', 'ADL013913', 'ADL013684', 'ADL013679', 'ADL013681', 'ADL013682', 'ADL013693', 'ADL013688', 'ADL013687', 'ADL013707', 'ADL013694', 'ADL013708', 'ADL013678', 'ADL013695', 'ADL013705', 'ADL013685', 'ADL013690', 'ADL013703', 'ADL013689', 'ADL013692', 'ADL013704', 'ADL013706', 'ADL007857', 'ADL013683', 'ADL013696', 'ADL004050', 'ADL82484', 'ADL008452', 'ADL001512', 'ADL002649', 'ADL004093', 'ADL006336', 'ADL006353', 'ADL006364', 'ADL007857', 'ADL007858', 'ADL008026', 'ADL008112', 'ADL012108', 'ADL012109', 'ADL008466', 'ADL013029', 'ADL003137', 'ADL004530', 'ADL002800', 'ADL002766', 'ADL000001', 'ADL000325', 'ADL012113', 'ADL010292', 'ADL012112', 'APN000001', 'APN000005', 'APN000007', 'APN000009', 'APN000010', 'APN000011', 'APN000013', 'APN000014', 'APN000015', 'APN000016', 'APN000019', 'APN000021', 'APN000022', 'APN000023', 'APN000961', 'APN001589', 'APN003094', 'APN009732', 'APN010935', 'APN012812', 'ACB017449', 'ACB022669', 'ACB023562', 'ACB023157', 'ACB017000', 'ACB022867', 'ACB023565', 'ACB021198', 'ACB018686', 'ACB020023', 'ACB017523', 'ACB022150', 'ACB022675', 'ACB022497', 'ACB018853', 'ACB019219', 'ACB020291', 'ACB020574', 'ACB021689', 'ACB022671', 'ACB016740', 'ACB020548', 'ACB021855', 'ACB023564', 'ACB023563', 'AMA013744', 'AMA016976', 'AMA017791', 'AMA014401', 'AMA016318', 'AMA018581', 'AMA018580', 'GUE000001', 'GUE000002', 'GUE000003', 'GUE000004', 'GUE000005', 'AMA005102', 'AMA008265', 'AMA019099', 'AMA008578', 'AMA018987', 'AMA006208', 'AMA017453', 'AMA017452', 'AKD004939', 'AKD003623', 'MKY36666', 'AKD004444', 'akd003787', 'AKD003559', 'MKY34348', 'AKD003146', 'AKD003553', 'AKD000001', 'ACL005622', 'ECL63585', 'ECL71573', 'ACL017001', 'ECL84510', 'ECL84649', 'ECL01028', 'MCL00817', 'MCL00852', 'ACL011586', 'ACL011514', 'ACL004745', 'ACL015941', 'ACL016696', 'MCL36053', 'ECL33360', 'MCH37611', 'ACH001100', 'MCH37', 'MCH41408', 'ACH005270', 'MCH46297', 'MCH00888', 'ACH002814', 'MCH37610', 'MCH37614', 'ADL001848', 'ADL000862', 'ADL008158', 'ADL78193', 'ADL67306', 'ADL000915', 'ADL76135', 'ADL63236', 'ADL008616', 'ADL002052', 'ADL013021', 'ABL009342', 'BBL13627', 'BBL13620', 'ABL002001', 'BBL13617', 'ABL008060', 'ABL002397', 'BBL13618', 'ABL008620', 'ABL009118', 'MHD90005', 'AHD010631', 'AHD012301', 'AHD000688', 'MHD90035', 'AHD012425', 'MHD90021', 'AHD012318', 'AHD004788', 'MHD90013', 'MHD90010', 'AHD010844', 'AHD004416', 'AHD006179', 'MHD90007', 'AHD012063', 'AAH000164', 'AAH002847', 'AAH000186', 'AAH000182', 'AAH000165', 'AAH000180', 'AAH000167', 'AAH005739', 'AAH005738', 'AAH003782 ')