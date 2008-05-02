package edu.umbc.recognizr;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: EntryServlet
 *
 */
 public class EntryServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

   public static final String ACTIONCODE_GETBOOKMARKS = "GETBOOKMARKS";
   public static final String ACTIONCODE_UPDATERATINGS = "UPDATEBMRATINGS";
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public EntryServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InstanceData l_instanceData = null;
		processRequest(request, response, l_instanceData);
	}  
	

	protected void processRequest (
		HttpServletRequest	p_request
	,	HttpServletResponse	p_response
	,	InstanceData p_instanceData
	) throws ServletException, IOException {
		String l_actionCode = "";
		String l_clientURL = "";
		String l_bookmarkId = "";
		String l_category = "";
		String l_rating = "";
		
		
//		parseRequest();
		try { l_actionCode = p_request.getParameter("actioncode"); } catch (Exception e) { e.printStackTrace(); }
//		try { l_clientURL = p_request.getParameter("clientURL"); } catch (Exception e) { e.printStackTrace(); }
		try { l_clientURL = p_request.getHeader("REFERER"); } catch (Exception e) { e.printStackTrace(); }
		try { l_bookmarkId = p_request.getParameter("bookmark_id"); } catch (Exception e) { e.printStackTrace(); }
		try { l_category = p_request.getParameter("category"); } catch (Exception e) { e.printStackTrace(); }
		try { l_rating = p_request.getParameter("change"); } catch (Exception e) { e.printStackTrace(); }
		
//		validateRequest();
		
		p_instanceData = new InstanceData();
		p_instanceData.actionCode = l_actionCode;
		p_instanceData.clientURL = l_clientURL;
		p_instanceData.bookmarkId = l_bookmarkId;
		p_instanceData.category = l_category;
		p_instanceData.rating = l_rating;
		
//		fireAppComponent();
		if (l_actionCode.equalsIgnoreCase(ACTIONCODE_GETBOOKMARKS)) {

			BookmarksRetriever bmRetriever = new BookmarksRetriever();
			bmRetriever.getBookmarks(p_instanceData);
		}
		else  if (l_actionCode.equalsIgnoreCase(ACTIONCODE_UPDATERATINGS)) {
			
		}
		
		
//		writeResponse();		
		p_response.setContentType("text/html");
		PrintWriter pw = new PrintWriter (p_response.getOutputStream());
	    pw.println(p_instanceData.clientResponse);
	    pw.close();
	}
	
}