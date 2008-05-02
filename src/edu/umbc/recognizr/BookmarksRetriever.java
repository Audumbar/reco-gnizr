/**
 * 
 */
package edu.umbc.recognizr;

import java.util.List;

import com.gnizr.db.dao.Bookmark;
import com.gnizr.db.dao.DaoResult;
import com.gnizr.db.dao.Link;
import com.gnizr.db.dao.bookmark.BookmarkDBDao;

import edu.umbc.textcat.TextCategorizer;

/**
 * @author audumbar
 *
 */
public class BookmarksRetriever {
	
	private BookmarkDBDao bmarkDao;
	private GnizrDBProvider gdbProvider;
	private BookmarkRatingHandler bmarkRatingDao;
	
	public BookmarksRetriever() {
		gdbProvider = new GnizrDBProvider();
		bmarkDao = new BookmarkDBDao(gdbProvider.getDataSource());
		bmarkRatingDao = new BookmarkRatingHandler(gdbProvider.getDataSource());
	}

	public void getBookmarks(InstanceData p_instanceData) {
			String pageContext = null;
			
			pageContext = getPageContext(p_instanceData.clientURL);
			//getBookmarks(pageContext, p_instanceData);
			getBookmarksByCategory(pageContext, p_instanceData);
			
	}
	public void getBookmarksByCategory(
			String p_category
		, 	InstanceData p_instanceData) {
		
		String bmResult = "";
		StringBuffer bmListStr = new StringBuffer(1024);
		
		DaoResult<BookmarkRating> bmRatings = bmarkRatingDao.getBookmarkbyCategory(p_category);
		if(bmRatings != null && bmRatings.getSize() > 0)
		{
			List<BookmarkRating> bmList = bmRatings.getResult();
			int cnt = 0;
			
			for (BookmarkRating bookmarkRating : bmList) {
				
				int id = bookmarkRating.getBookmark_id();
				int rating = bookmarkRating.getBookmark_rating();
				String category = bookmarkRating.getBookmark_category();
				Bookmark bookmark = bmarkDao.getBookmark(id); 
				
				Link link = bookmark.getLink();
				String linkStr = link.getUrl();
				
				//String description = bookmark.getTitle().trim() + "-"+bookmark.getNotes().trim();
				String description = bookmark.getTitle().trim();
				bmListStr.append("{\"id\": ");
				bmListStr.append(id);
				bmListStr.append(", \"link\": ");
				bmListStr.append("\""+linkStr+"\"");
				bmListStr.append(", \"description\": ");
				bmListStr.append("\""+description+"\"");
				bmListStr.append(", \"rating\": ");
				bmListStr.append(rating);
				bmListStr.append(", \"category\": ");
				bmListStr.append(category);				
				bmListStr.append("},");
				cnt++;
				
				if (cnt == 10) {
					break;
				}
			}
		}
		if(bmListStr.charAt(bmListStr.length()-1) == ',') {
			bmListStr.setLength(bmListStr.length()-1);
		}
		bmResult = "{\"bookmarks\" : ["+ bmListStr.toString()+"] };";
		
		p_instanceData.clientResponse = bmResult;
	}
	
	public void getBookmarks(
		String p_searchQuery
	, 	InstanceData p_instanceData) {
		
		String bmResult = "";
		StringBuffer bmListStr = new StringBuffer(1024);
		
		
		DaoResult<Bookmark> result = bmarkDao.searchCommunityBookmarks(p_searchQuery,0, 10);
		if(result != null && result.getSize() > 0)
		{
			List<Bookmark> bmList = result.getResult();
			int rating = 10;
			
			for (Bookmark bookmark : bmList) {
				
				int id = bookmark.getId();
				
				Link link = bookmark.getLink();
				String linkStr = link.getUrl();
				
				String description = bookmark.getTitle().trim() + "-"+bookmark.getNotes().trim();
				bmListStr.append("{\"id\": ");
				bmListStr.append(id);
				bmListStr.append(", \"link\": ");
				bmListStr.append("\""+linkStr+"\"");
				bmListStr.append(", \"description\": ");
				bmListStr.append("\""+description+"\"");
				bmListStr.append(", \"rating\": ");
				bmListStr.append(rating);
				rating--;
				if (rating == 0) break;
				else bmListStr.append("},");
			}
		}
		if(bmListStr.charAt(bmListStr.length()-1) == ',') {
			bmListStr.setLength(bmListStr.length()-1);
		}
		bmResult = "{\"bookmarks\" : ["+ bmListStr.toString()+"] };";
		
		p_instanceData.clientResponse = bmResult;
	}
	
	public String getPageContext (String p_webpageURL) {
		//do web content scraping for p_webpageURL
		//and return the context of the page as search query for community bookmark search
		//return "searchengine";
		String pageCategory = "";
		
		HttpClientWrapper httpClient = new HttpClientWrapper();
		String pageContents = httpClient.getPageContents(p_webpageURL);
		pageCategory = getPageCategory(pageContents);
		
		return pageCategory;
	}
	
	public String getPageCategory(String p_pageContents) {
		String pageCategory = "";
		
		TextCategorizer guesser = new TextCategorizer();         
		guesser.setConfFile("WEB-INF/textcat.conf");
		pageCategory = guesser.categorize(p_pageContents);
		return pageCategory;
	}

}