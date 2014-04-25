<HTML>
<HEAD>
<TITLE>Time Report</TITLE>
</HEAD>
<BODY>
<FORM action="add_timerecord" method="POST">
<CENTER>
<H1>Report Time to the System</H1>
<TABLE bgcolor="cyan" align="center">
	<TR>
		<TD>Date</TD>
		<TD><INPUT type="text" name="date" maxlength="10"></TD>
	</TR>
	<TR>
		<TD>Project Name</TD>
		<TD>
		<SELECT name="project">
		<OPTION value="1">Project 1</OPTION>
		<OPTION value="2">Project 2</OPTION>					
		<OPTION value="3">Project 3</OPTION>					
		<OPTION value="4">Project 4</OPTION>					
		</SELECT>
		</TD>
	</TR>
	<TR>
		<TD>Number of Hours</TD>
		<TD><INPUT type="text" name="hours" maxlength="2"></TD>
	</TR>
	<TR>
		<TD>Description</TD>
		<TD><textarea name="description" rows="5"></textarea></TD>
	</TR>
</TABLE>
<BR>
	<INPUT type="submit" value="Save">
	<INPUT type="reset" value="Reset">
<BR>
<BR>
<%if(request.isUserInRole("manager")){%>
<A href="../manager/">Add Employees</A>
<BR>

<%}%>
<A href="../login/logout.jsp">Logout</A>
</CENTER>
</FORM>
</BODY>
</HTML>
