<HTML>
   <HEAD>
   <TITLE>Logout</TITLE>
   </HEAD>
   <BODY>
   
   <% session.invalidate(); %>

   <CENTER>
   <H1>Good Bye</H1>
   <H2>You have now logged out.</H2>

   <A href="/timereport/">Return to Main Page</A>
   <CENTER>
   </BODY>
</HTML>