<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>

<BODY>
<CENTER>
   <H1>Please login ...</H1>

   <FORM method="post" action="j_security_check">
   <TABLE bgcolor="cyan">
      <TR>
      <TD align="left">
      Username:
      </TD>

      <TD align="left">
      <INPUT type="text" name="j_username" size="15" maxlength="15">
      </TD>
      </TR>

      <TR>
      <TD align="left">
      Password:
      </TD>

      <TD align="left">
      <INPUT type="password" name="j_password" size="15" maxlength="15">
      </TD>
      </TR>
   </TABLE>
   <BR>
   <INPUT type="submit" value="Login">
   <INPUT type="reset" value="Reset"> 
   </FORM>
</CENTER>
</BODY>
</HTML>
