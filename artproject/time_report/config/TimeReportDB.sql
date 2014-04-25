CREATE TABLE users(
	userid VARCHAR(15) PRIMARY KEY,
	password VARCHAR(15),
	name VARCHAR(40)
);

CREATE TABLE usergroups(
	userid VARCHAR(15),
	groupname VARCHAR(20),
	PRIMARY KEY (userid, groupname)
);

INSERT INTO users VALUES('emp1', 'pass1', 'Employee 1');
INSERT INTO users VALUES('emp2', 'pass2', 'Employee 2');
INSERT INTO users VALUES('mgm1', 'pass3', 'Manager 1');
INSERT INTO users VALUES('mgm2', 'pass4', 'Manager 2');

INSERT INTO usergroups VALUES('emp1', 'EmployeeGroup');
INSERT INTO usergroups VALUES('emp2', 'EmployeeGroup');
INSERT INTO usergroups VALUES('mgm1', 'ManagerGroup');
INSERT INTO usergroups VALUES('mgm2', 'ManagerGroup');



