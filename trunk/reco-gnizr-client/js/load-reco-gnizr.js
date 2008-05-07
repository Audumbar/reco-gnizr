/* 
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Title : Reco-gnizr iframe loader
Author : Kishor Datar
URL : 

Description : This file is included in the user pages. This fetched the
              bookmark iframe

Created : see svn
Modified : see svn

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
*/

var thisElement = document.getElementById("recognizrscript");
var options = thisElement.getAttribute("src");
if(options.indexOf("#") >=0 )
  options  = options.substring(options.indexOf("#"));
else
  options = "";

var turl = document.location.href;
turl = turl.replace(/=/g, "%3D").replace(/&/g, "%26")
options += "&clientURL=" + escape(turl);

recognizrFrame = document.createElement("IFRAME");
recognizrFrame.setAttribute("src", 
   "http://localhost:8080/reco-gnizr/html/reco-gnizr-frame.html" + options);

recognizrFrame.style.border = "none";
recognizrFrame.style.height = "150px";
recognizrFrame.style.width = "250px"; 
recognizrFrame.setAttribute("scrolling", "no");

  var h = options;
  var properties = {};
  if(h != "")
  {
    h = h.substring(1);
    var t = [];
    t = h.split("&");
    for(var i=0; i<t.length; i++)
    {
      var t2 = t[i].split("=");
      properties[t2[0]] = t2[1];
    }
  }
  if(properties["width"])
    recognizrFrame.style.width = properties["width"];
  if(properties["height"])
    recognizrFrame.style.height = properties["height"];


thisElement.parentNode.appendChild(recognizrFrame);
