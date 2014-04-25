/**
 * 
 */
package org.apache.geronimo.samples.timereport.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddTimeRecordServlet extends HttpServlet {

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		handleRequest(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
	
	private void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String date = req.getParameter("date");
		String project = req.getParameter("project");
		String hours = req.getParameter("hours");
		
		System.out.println("Date = "+date);
		System.out.println("Project = "+project);
		System.out.println("Hours = "+hours);
		
		//TODO: Save time record information to a Database
		
		
		res.sendRedirect("../employee/");		
		
	}
	
	
	

}
