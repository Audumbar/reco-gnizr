package edu.umbc.recognizr;

import java.io.Serializable;

public class BookmarkRating implements Comparable<BookmarkRating>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5131247364040300199L;
	
	int bookmark_id;
	String bookmark_category;
	int bookmark_rating;
	
	BookmarkRating(int id, String category, int rating)
	{
		this.bookmark_category = category;
		this.bookmark_id = id;
		this.bookmark_rating = rating;
	}

	public int getBookmark_id() {
		return bookmark_id;
	}

	public void setBookmark_id(int bookmark_id) {
		this.bookmark_id = bookmark_id;
	}

	public String getBookmark_category() {
		return bookmark_category;
	}

	public void setBookmark_category(String bookmark_category) {
		this.bookmark_category = bookmark_category;
	}

	public int getBookmark_rating() {
		return bookmark_rating;
	}

	public void setBookmark_rating(int bookmark_rating) {
		this.bookmark_rating = bookmark_rating;
	}

	@Override
	public int compareTo(BookmarkRating arg0) 
	{
		return arg0.bookmark_rating - this.bookmark_rating; 
	}
}
