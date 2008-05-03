package edu.umbc.recognizr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.gnizr.db.dao.DBUtil;
import com.gnizr.db.dao.DaoResult;

public class BookmarkRatingHandler {

	private DataSource dataSource;
	

	BookmarkRatingHandler(DataSource ds)
	{
		this.dataSource = ds;		
	}
	
	DataSource getDataSource() {
		return dataSource;
	}
	
	public DaoResult<BookmarkRating> getBookmarkbyCategory(String category)
	{
		Connection conn = null;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		List<BookmarkRating> bmarks = new ArrayList<BookmarkRating>();
		DaoResult<BookmarkRating> result = null;

		try 
		{
			conn = getDataSource().getConnection();
			stmt1 = conn.createStatement();
			rs1 = stmt1.executeQuery("SELECT * FROM gnizr_db.bookmark_rating where category = '" + category + "'");
			while(rs1.next())
			{	
				BookmarkRating br = new BookmarkRating(rs1.getInt(1),rs1.getString(2),rs1.getInt(3));
				bmarks.add(br);
			}
			
			Collections.sort( bmarks );
			result = new DaoResult<BookmarkRating>(bmarks,rs1.getFetchSize());

		} 
		catch (SQLException e)
		{
			System.out.println("Fatal Exception"  + e);
		}
		finally
		{
			try
			{
				DBUtil.cleanup(conn, stmt1);
			} 
			catch (SQLException e)
			{
				System.out.println("Fatal Exception"  + e);
			}
		}
		return result;
	}
	
	
	public void update_rating(int id,String category,int rating)
	{
		Connection conn = null;
		Statement stmt1 = null;
		
		if(rating > 5)
		{
			System.out.println("Error:Rating scale 0-5");
			return;
		}
		try 
		{
			conn = getDataSource().getConnection();
			stmt1 = conn.createStatement();
			stmt1.executeUpdate("INSERT INTO gnizr_db.bookmark_rating (id,category,rating) VALUES("+ id + ",'" + category + "'," + rating  + ") ON DUPLICATE KEY UPDATE rating="+ rating);

		} 
		catch (SQLException e)
		{
			System.out.println("Fatal Exception"  + e);
		}
		finally
		{
			try
			{
				DBUtil.cleanup(conn, stmt1);
			} 
			catch (SQLException e)
			{
				System.out.println("Fatal Exception"  + e);
			}
			
		}
	}
	
	public int getBookmarkRating(int id,String category)
	{
		Connection conn = null;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		int rating =0;
		try 
		{
			conn = getDataSource().getConnection();
			stmt1 = conn.createStatement();
			rs1 = stmt1.executeQuery("SELECT rating FROM gnizr_db.bookmark_rating where id = "+ id + " and  category ='" + category + "'");			
			 //Issue 1:     New value for category throws SQLException exception  
			if(rs1.getFetchSize()>0)
			{
				rs1.first();
				rating = rs1.getInt(1);
			}
			 //Issue 1:     New value for category throws SQLException exception  
		} 
		catch (SQLException e)
		{
			System.out.println("Fatal Exception"  + e);
		}
		finally
		{
			try
			{
				DBUtil.cleanup(conn, stmt1);
			} 
			catch (SQLException e)
			{
				System.out.println("Fatal Exception"  + e);
			}
		}
		return rating;
	}
	
	
	// Issue 2 Enhancement Request - Increment rating function 
	//Owner:  uday.mit  
	public void incrementRating(int id,String category)
	{
		int current_rating = getBookmarkRating(id, category);
		int new_rating = current_rating + 1; 
		if(new_rating<0)
			new_rating =0;
		 //Issue 4:  Bookmark rating set more than 5 
		if(new_rating > 5)
			new_rating = 5;
		update_rating(id, category, new_rating);
	}
	
	public void decrementRating(int id,String category)
	{
		int current_rating = getBookmarkRating(id, category);
		int new_rating = current_rating - 1; 
		if(new_rating<0)
			new_rating =0;
		 //Issue 4:     Bookmark rating set more than 5 
		 if(new_rating > 5)
				new_rating = 5;
		update_rating(id, category, new_rating);
	}

	public static void main(String args[])
	{
		GnizrDBProvider gDBProvider = new GnizrDBProvider();
		BookmarkRatingHandler rating = new BookmarkRatingHandler(gDBProvider.getDataSource());
		
		//DaoResult<BookmarkRating> bms = rating.getBookmarkbyCategory("technology");
		rating.update_rating(2, "sports", 3);
		System.out.println( rating.getBookmarkRating(3, "music"));
		//rating.incrementRating(3, "music");
		rating.decrementRating(3, "music");
		System.out.println("END");
	}
}


