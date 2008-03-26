/* 
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Title : Reco-gnizr widget manipulation and event handling
Author : Kishor Datar
URL : 

Description : Event handlers, UI manipulation and ajax request dispatcher

Created : see svn
Modified : see svn

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
*/

var resizex = 50;
var resizey = 50;
var oldx;
var oldy;
var urlToChangeRating = "../test/reco-gnizr-change-rating.html";
var refreshAfter = 8;
var nChanges= 0;

function imgMouseOver(e)
{
  img = getEventTarget(this);
  img.width = oldx / 1.5;
  img.height = oldy / 1.5;
}

function imgMouseOut (e, img)
{
  if(img == null)
    img = getEventTarget(this); // this event resulted due to actual mouse out

  img.width = oldx * (resizex/100);
  img.height = oldy * (resizex/100);
}

function adjustBookmarkVisibility(container)
{
  str = "";
  var nxt = container.firstChild;
  for(i=0; i<bookmarks.length && nxt; )
  {
    if(nxt["bookmark"])
    {
      i++;
      nxt.style.display = "none";
      nxt.style.pixelLeft += 100;
      if(i <= MAX_BOOKMARKS)
        nxt.style.display = "";
      else
        nxt.style.display = "none";
      str += nxt["bookmark"]["description"] + " " + nxt["bookmark"]["rating"] + "\n";
    }
    nxt = nxt.nextSibling;
  }
  //alert(str);
}

function adjustBookmarkSequence (div, raise)
{
  var containerDiv = document.getElementById("bookmark_container");
  if(raise) // user favourited the bookmark
  {
    var prev = div.previousSibling;
    
    while (prev && prev["bookmark"] != void(0))
    {
      if(prev["bookmark"]["rating"] <= div["bookmark"]["rating"])
      {
        prev = prev.previousSibling;
      }
      else
      {
        break;
      }
    }
    
    if(prev && prev.nextSibling)
    {
      containerDiv.removeChild(div);
      containerDiv.insertBefore(div, prev.nextSibling);
    }
  }
  else
  {
    var next = div.nextSibling;
    
    while (next && next["bookmark"] != void(0))
    {
      if(next["bookmark"]["rating"] >= div["bookmark"]["rating"])
      {
        next = next.nextSibling;
      }
      else
      {
        //alert(next["bookmark"]["description"] + " " + div["bookmark"]["description"])
        break;
      }
    }
    
    if(next)
    {
      containerDiv.removeChild(div);
      containerDiv.insertBefore(div, next);
    }
    else
    {
      containerDiv.appendChild(div);
    }
  }
  
  adjustBookmarkVisibility(containerDiv);
}

// dRating is difference in rating i.e. +1 or -1
function changeRating (bookmark, dRating)
{
  var xmlHttp = getXmlHttpObject();
  var url = getRootDirectory() + urlToChangeRating + "?" + 
                  "bookmark_id=" + escape(bookmark["id"]) + "&" +
                  "change="+escape(dRating);
  url = url.replace("+", "%2B");
  xmlHttp.onreadystatechange = function (){return true;}
  xmlHttp.open("GET", url, true);
  xmlHttp.send(null);
}

// image has attribute thumbsup attached to it. if true, it means it is 
// thumbs up image else down
function imgClick(e)
{
  var img = getEventTarget(this);
  var div = img.parentNode.parentNode; // <div><a><img>
  if(img.thumbsup)
  {
    if(div["bookmark"]["rating"] < 5)
    {
      div["bookmark"]["rating"]++;
      changeRating(div["bookmark"], "+1");
      nChanges++;
    }
    // TODO: bad practice! change the following two lines immediately
    span = div.firstChild.nextSibling.nextSibling.nextSibling.nextSibling;
    span.innerText = span.innerHTML = span.innerHtml = div["bookmark"].rating;
    adjustBookmarkSequence(div, true); // raise
    imgMouseOut(null, img); // adjust the image size again
  }
  else
  {
    if(div["bookmark"]["rating"] > 0)
    {
      div["bookmark"]["rating"]--;
      changeRating(div["bookmark"], "-1");
      nChanges++;
    }
    // TODO: bad practice! change the following two lines immediately
    span = div.firstChild.nextSibling.nextSibling.nextSibling.nextSibling;
    span.innerText = span.innerHTML = span.innerHtml = div["bookmark"].rating;
    adjustBookmarkSequence(div, false); // raise
    imgMouseOut(null, img); // adjust the image size again
  }
  
  if (nChanges >= refreshAfter)
    document.location.reload();
}


function bookmarksLoaded()
{
  var imgs = document.getElementsByTagName("IMG");
  oldx = imgs[0].width * (resizex/100);
  oldy = imgs[0].height * (resizex/100); // TODO: see if there are images. check indexes

  for (var i=0; i<imgs.length; i++)
  {
      var img = imgs[i];
      img.style.marginRight = "5px";
      img.style.marginLeft  = "5px";
      img.align = "absmiddle";
      imgMouseOut(null, img);
      addEvent (img, "mouseover", imgMouseOver);
      addEvent (img, "mouseout", imgMouseOut);
      addEvent (img, "click", imgClick);
  }
}

