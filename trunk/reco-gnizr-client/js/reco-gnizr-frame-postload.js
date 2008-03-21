

function imgMouseOver(e)
{
  img = this;
  img.width = oldx;
  img.height = oldy;
}

function imgMouseOut(e, img)
{
  if(img == null)
    img = this; // this event resulted due to actual mouse out

  img.width = oldx * (resizex/100);
  img.height = oldy * (resizex/100);
}

// image has attribute thumbsup attached to it. if true, it means it is 
// thumbs up image else down
function imgClick(img)
{
  if(this.thumbsup)
  {
    alert(bookmarks)
  }
  else
  {
  }
}



var resizex = 50;
var resizey = 50;

var oldx;
var oldy;


function bookmarksLoaded()
{
  var imgs = document.getElementsByTagName("IMG");
  oldx = imgs[0].width * (resizex/100);
  oldy = imgs[0].height * (resizex/100); // TODO: see if there are images. check indexes

  for (var i=0; i<imgs.length; i++){
      var img = imgs[i];
      img.style.paddingRight = "10px";
      img.style.paddingLeft = "10px";
      img.align = "absmiddle";
      imgMouseOut(null, img);
      img.addEventListener ("mouseover", imgMouseOver, false);
      img.addEventListener ("mouseout", imgMouseOut, false);
      img.addEventListener ("click", imgClick, false);
  }
}

