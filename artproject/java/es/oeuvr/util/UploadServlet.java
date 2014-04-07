package es.oeuvr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/rest/uploadfile")
public class UploadServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;  

	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		//processRequest(request, response);
		String ajaxUpdateResult = "";
		final String path = System.getProperty("jboss.server.base.dir")+"/upload";
		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);            
			for (FileItem item : items) {
				if (item.isFormField()) {
					ajaxUpdateResult += "Field " + item.getFieldName() +" with value: " + item.getString() + " is successfully read\n\r";
				} else {
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					File outputfile = new File(path + File.separator + item.getName() );
					if (outputfile.createNewFile()) {
						try {
							item.write(outputfile);
						} catch (Exception e) {
							throw new ServletException("Parsing file upload failed.", e);
						}
					}
					ajaxUpdateResult += "File: '" + item.getName() + "' is successfully uploaded\n\r";
					System.out.println(ajaxUpdateResult);
				}
			}

		} catch (FileUploadException e) {
			throw new ServletException("Parsing file upload failed.", e);
		}
		response.getWriter().print(ajaxUpdateResult);
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		final String path = System.getProperty("jboss.server.base.dir")+"/upload/";
		String filename=request.getParameter("fname");
		BufferedReader br = new BufferedReader(new FileReader(path+filename));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append('\n');
	            line = br.readLine();
	        }
	        String everything = sb.toString();
	        response.getWriter().print(everything);
	    } finally {
	        br.close();
	    }		
	}
}
