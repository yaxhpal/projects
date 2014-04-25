package org.apache.geronimo.samples.timereport.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddEmployeeServlet extends HttpServlet {

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		handleRequest(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
	
	private void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String name = req.getParameter("name");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//TODO: Save employee information to a Database
		
		System.out.println("Name = "+name);
		System.out.println("Username = "+username);
		System.out.println("Password = "+password);
		
		
		res.sendRedirect("../manager/");		
		
	}

}
