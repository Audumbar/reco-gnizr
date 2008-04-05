/* 
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Title : Reco-gnizr frame content creator
Author : Kishor Datar
URL : 

Description : this file gets bookmarks from the server and places them into
              the widget

Created : see svn
Modified : see svn

- - - - - - - - - - - - - - - - - - - - - - - - - - - - -

Star image borrowed from
http://www.flickr.com/photos/eggie/323587586/ 
*/

MAX_BOOKMARKS = 4;
bookmarks = null;
xmlHttp = null;
bookmarkSource = "../test/reco-gnizr-get-bookmarks.html";


function attachHtml(bookmark, style, container, hidden)
{
  switch (style)
  {
    default:
      var div = document.createElement("div");
      
      var tbl = document.createElement ("table");
      var tb  = document.createElement ("tbody"); // IE specific
      var tr  = document.createElement ("tr");
      var td1 = document.createElement ("td");
      var td2 = document.createElement ("td");
      var td3 = document.createElement ("td"); 
      
      var a = document.createElement("a");
      var up = document.createElement("img");
      var dn = document.createElement("img");
      var star = document.createElement("img");

      var a1 = document.createElement("a");
      var a2 = document.createElement("a");
      var span1 = document.createElement("span"); // text "Rating : "
      var span2 = document.createElement("span"); // rating
      
      a1.setAttribute("href", "#");
      addEvent (a1, "click", function (){return false});
      a2.setAttribute("href", "#");
      addEvent (a2, "click", function (){return false});
      a.setAttribute("href", bookmark.link);
      a.setAttribute("target", "_blank");
      a.innerText = a.innerHTML = a.innerHtml = bookmark.description;
      up.setAttribute("src", getRootDirectory() + "../img/up.gif");
      dn.setAttribute("src", getRootDirectory() + "../img/dn.gif");
      star.setAttribute("src", getRootDirectory() + "../img/star.gif");
      star.setAttribute("width", 25 * 100/resizex);
      star.setAttribute("height", 25 * 100/resizey);
      up.thumbsup = true;
      dn.thumbsup = false;
      up.setAttribute("border", 0);
      dn.setAttribute("border", 0);
      span1.style.color = "000888";
      span2.style.color = "000FFF";
      //span1.innerText = span1.innerHTML = span1.innerHtml = "Rating : ";
      span1.appendChild(star);
      span2.innerText = span2.innerHTML = span2.innerHtml = bookmark.rating;      

      a1.appendChild(up);
      a2.appendChild(dn);
      tbl.appendChild (tb);
      tb.appendChild(tr);
      tr.appendChild(td1);
      tr.appendChild(td2);
      tr.appendChild(td3);
      td1.appendChild(a);
      td2.appendChild(a1);
      td2.appendChild(a2);
      td3.appendChild(span2);
      td3.appendChild(span1);
      tbl.setAttribute("width", "100%");
      td1.setAttribute("width", "40%");
      td2.setAttribute("width", "30%");
      td3.setAttribute("width", "15%");
      div["bookmark"] = bookmark; // save reference to the bookmark object here
      tbl["bookmark"] = bookmark; // save reference to the bookmark object here
      
      if(hidden)
      {
        div.style.display = "none";
        tbl.style.display = "none";
      }
      tbl.setAttribute("border", "0");
      container.appendChild(tbl);
  }
}

function displayBookmarks(bookmarks)
{
  var div = document.getElementById("bookmark_container");
  hidden = false;
  for(var i=0; i<bookmarks.length; i++)
  {
    if(MAX_BOOKMARKS <= i)
      hidden = true;
    attachHtml(bookmarks[i], "square", div, hidden);
  }
  
  // for some reason, when the cache was empty, the imgages would appear in
  // small size. To avoid this, I had to wait for some time after images are
  // loaded before I could resize them. Hence the following timeout.
  window.setTimeout(bookmarksLoaded, "100");
}


function sortBookmarks(bookmarks)
{
  var temp = null;
  for (var i=0; i<bookmarks.length; i++){
    for (var j=i+1; j<bookmarks.length; j++){
      if(bookmarks[i]["rating"] < bookmarks[j]["rating"])
      {
        temp = bookmarks[i];
        bookmarks[i] = bookmarks[j];
        bookmarks[j] = temp;
      }
    }
  }  
}

function gotBookmarks ()
{
  if(xmlHttp.readyState == 4)
  {
    try{
      // TODO : use json parser here. eval is dangerous
      var code = "obj = " + xmlHttp.responseText + "";
      eval(code);
      bookmarks = obj.bookmarks;
      sortBookmarks(bookmarks);
      displayBookmarks(bookmarks);
    }
    catch (e)
    {
      throw e;
    }
  }
  else
  {
  }
}

function loadBookmarks ()
{
  var url = getRootDirectory() + bookmarkSource;
  xmlHttp = getXmlHttpObject();
  xmlHttp.onreadystatechange = gotBookmarks;
  xmlHttp.open("GET", url, true);
  xmlHttp.send(null);
}

// loadBookmarks (); is called on onload
