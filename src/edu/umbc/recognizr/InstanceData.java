package edu.umbc.recognizr;

import java.io.Serializable;

public final class 
	InstanceData
implements
	Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4938281655475539648L;
	

	public String actionCode;
	
	public String clientURL;
	
	public String clientResponse;
	
	public int bookmarkId;
	
	public String category;
	
	public int changeInRating;
	
	public static int INCREMENT_RATING = 1;
	
	public static int DECREMENT_RATING = 0;
	
	public static final String ACTIONCODE_GETBOOKMARKS = "GETBOOKMARKS";
	
	public static final String ACTIONCODE_UPDATERATINGS = "UPDATEBMRATINGS";
	
	public static final String DEFAULT_CATEGORY = "GENERAL";
	
	public static String realWebAppPath;
	   
	public InstanceData() {
		actionCode = ACTIONCODE_GETBOOKMARKS;
		clientURL = "";
		clientResponse = "";
		bookmarkId = 0;
		category = DEFAULT_CATEGORY;
		changeInRating = INCREMENT_RATING;
	}

}

