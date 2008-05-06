package edu.umbc.recognizr;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author audumbar
 * Servlet implementation class for Servlet: EntryServlet
 */
 public class 
 	EntryServlet 
 extends 
 	HttpServlet 
 implements Servlet {
	 
	 
   static final long serialVersionUID = 1L;

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
		try { 
			l_actionCode = p_request.getParameter("actioncode"); 
		} catch (Exception e) { e.printStackTrace(); }
		
//		try { 
//			l_clientURL = p_request.getParameter("clientURL"); 
//		} catch (Exception e) { e.printStackTrace(); }
		try { 
			l_clientURL = p_request.getHeader("REFERER"); 
		} catch (Exception e) { e.printStackTrace(); }
		try { 
			l_bookmarkId = p_request.getParameter("bookmark_id"); 
		} catch (Exception e) { e.printStackTrace(); }
		try { 
			l_category = p_request.getParameter("category"); 
		} catch (Exception e) { e.printStackTrace(); }
		try { 
			l_rating = p_request.getParameter("change"); 
		} catch (Exception e) { e.printStackTrace(); }
		
//		validateRequest();
		p_instanceData = new InstanceData();
		if(!l_actionCode.isEmpty())
			p_instanceData.actionCode = l_actionCode;
		if(!l_clientURL.isEmpty())
			p_instanceData.clientURL = l_clientURL;
		if(!l_category.isEmpty())
			p_instanceData.category = l_category;
		
		try {
			p_instanceData.bookmarkId = Integer.parseInt(l_bookmarkId);
		} catch (NumberFormatException e) { e.printStackTrace(); }
		
		if (!l_rating.isEmpty() && l_rating.equalsIgnoreCase("+1")) {
			p_instanceData.changeInRating = InstanceData.INCREMENT_RATING;
		} else if (!l_rating.isEmpty() && l_rating.equalsIgnoreCase("-1")) {
			p_instanceData.changeInRating = InstanceData.DECREMENT_RATING;
		}
		
//		fireAppComponent();
		BookmarksRetriever bmRetriever = new BookmarksRetriever();
		
		if (l_actionCode.equalsIgnoreCase(InstanceData.ACTIONCODE_GETBOOKMARKS)) {
			bmRetriever.getBookmarks(p_instanceData);
		}
		else  if (l_actionCode.equalsIgnoreCase(InstanceData.ACTIONCODE_UPDATERATINGS)) {
			
		}
		
		
//		writeResponse();		
		p_response.setContentType("text/html");
		PrintWriter pw = new PrintWriter (p_response.getOutputStream());
	    pw.println(p_instanceData.clientResponse);
	    pw.close();
	}
	
}