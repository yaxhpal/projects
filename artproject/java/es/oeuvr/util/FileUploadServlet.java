package es.oeuvr.util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
//IOStream
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;



//Servlet3.0 specific annotations
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@WebServlet(description = "FileUploadServlet Description", urlPatterns = { "/views/FileUploadServlet" })
@MultipartConfig(location = "/home/yashpal/git/landfill/uploads")
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public FileUploadServlet() {
		super();
		System.out.println("FileUploadServlet Initialized & Instantiated.");
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><head><body>");
		for (Part part : request.getParts()) {
			String partHeader = part.getHeader("content-disposition");
			long partSize = part.getSize();
			out.println("<BR>Part Name = " + part.getName());
			out.println("<BR>Part Header = " + partHeader);
			out.println("<BR>Part Size = " + partSize);
			System.out.println("part.getHeader(\"content-disposition\") = " + part.getHeader("content-disposition"));
		}
		out.println("<center><h1>File Upload Completed Successfully</h1></center></body></html>");

		// **** Custom Option ****//
		System.out.println("Custom Way To Upload File with Actual FileName.");
		fileUploadWithDesiredFilePathAndName(request);
		System.out.println("File Uploaded using custom Way.");
	}

	/*
	 * Following method allows us to place the uploaded file in a desired
	 * Location on the server along with the desired fileName
	 */
	public void fileUploadWithDesiredFilePathAndName(HttpServletRequest request) throws IOException, ServletException {
		/******** Following part of code is not needed ********/
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			for (Part part : request.getParts()) {
				inputStream = request.getPart(part.getName()).getInputStream();
				int i = inputStream.available();
				byte[] b = new byte[i];
				inputStream.read(b);
				String fileName = "";
				for (String temp : part.getHeader("content-disposition").split(";")) {
					if (temp.trim().startsWith("filename")) {
						fileName = temp.substring(temp.indexOf('=') + 1).trim().replace("\"", "");
					}
				}

				// Writing contents to desired FilePath & FileName //
				String uploadDir = "/home/yashpal/git/landfill/uploads";
				outputStream = new FileOutputStream(uploadDir + "/" + fileName);
				outputStream.write(b);
				inputStream.close();
			}
		} catch (Exception e) {
			System.out.println("Unable to Upload File: " + e);
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		final String path = "/home/yashpal/git/landfill/uploads";
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