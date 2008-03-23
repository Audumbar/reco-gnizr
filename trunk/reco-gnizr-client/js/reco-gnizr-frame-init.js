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
      var a = document.createElement("a");
      var up = document.createElement("img");
      var dn = document.createElement("img");
      var a1 = document.createElement("a");
      var a2 = document.createElement("a");
      var span1 = document.createElement("span"); // text "Rating : "
      var span2 = document.createElement("span"); // rating
      
      a1.href = "#";
      addEvent (a1, "click", function (){return false});
      a2.href = "#";
      addEvent (a2, "click", function (){return false});
      a.href = bookmark.link;
      a.target = "_blank";
      a.innerText = a.innerHTML = a.innerHtml = bookmark.description;
      up.src = getRootDirectory() + "../img/up.jpg";
      dn.src = getRootDirectory() + "../img/dn.jpg";
      up.thumbsup = true;
      dn.thumbsup = false;
      up.border = 0;
      dn.border = 0;
      span1.style.color = "000888";
      span2.style.color = "000FFF";
      span1.innerText = span1.innerHTML = span1.innerHtml = "Rating : ";
      span2.innerText = span2.innerHTML = span2.innerHtml = bookmark.rating;      

      a1.appendChild(up);
      a2.appendChild(dn);
      div.appendChild(a);
      div.appendChild(a1);
      div.appendChild(a2);
      div.appendChild(span1);
      div.appendChild(span2);
      
      div["bookmark"] = bookmark; // save reference to the bookmark object here
      
      if(hidden)
        div.style.display = "none";
      container.appendChild(div);
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
  bookmarksLoaded();
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
      alert(e);
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

loadBookmarks ();
