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
	
	public String bookmarkId;
	
	public String category;
	
	public String rating;
	
	public InstanceData() {
		actionCode = "";
		clientURL = "";
		clientResponse = "";
		bookmarkId = "";
		category = "";
		rating = "";
	}

}

