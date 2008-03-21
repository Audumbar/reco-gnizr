MAX_BOOKMARKS = 4;
bookmarks = null;


function attachHtml(bookmark, style, container)
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
      
      a1.href = "#";
      a1.addEventListener("click", function (){return false}, false);
      a2.href = "#";
      a2.addEventListener("click", function (){return false}, false);
      a.href = bookmark.link;
      a.target = "_blank";
      a.innerText = a.innerHTML = a.innerHtml = bookmark.description;
      up.src = getRootDirectory() + "../img/up.jpg";
      dn.src = getRootDirectory() + "../img/dn.jpg";
      up.thumbsup = true;
      dn.thumbsup = false;
      up.border = 0;
      dn.border = 0;
      
      a1.appendChild(up);
      a2.appendChild(dn);
      div.appendChild(a);
      div.appendChild(a1);
      div.appendChild(a2);
      
      container.appendChild(div);
  }
}

function displayBookmarks(bookmarks)
{
  var div = document.getElementById("bookmark_container");
  for(var i=0; i<bookmarks.length; i++)
  {
    if(MAX_BOOKMARKS == i)
      break;
    attachHtml(bookmarks[i], "square", div);
  }
  bookmarksLoaded();
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
      displayBookmarks(bookmarks);
    }
    catch (e)
    {
      alert(e);
    }
  }
  else
  {
  }
}

xmlHttp = null;
bookmarkSource = "../test/reco-gnizr-get-bookmarks.html";

function loadBookmarks ()
{
  var url = getRootDirectory() + bookmarkSource;
  xmlHttp = getXmlHttpObject();
  xmlHttp.onreadystatechange = gotBookmarks;
  xmlHttp.open("GET", url, true);
  xmlHttp.send(null);
}


loadBookmarks ();


function getXmlHttpObject()
{
  var xmlHttp;
  try
  {
    // Firefox, Opera 8.0+, Safari
    xmlHttp=new XMLHttpRequest();
  }
  catch (e)
  {
    // Internet Explorer
    try
    {
      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch (e)
    {
      try
      {
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
      catch (e)
      {
        alert("Your browser does not support AJAX!");
        return null;
      }
    }
  }
  return xmlHttp;
}
