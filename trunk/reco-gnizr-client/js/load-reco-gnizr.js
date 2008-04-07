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

recognizrFrame = document.createElement("IFRAME");
recognizrFrame.setAttribute("src", 
   "http://localhost:8080/reco-gnizr/html/reco-gnizr-frame.html" + options);

recognizrFrame.style.border = "none";
recognizrFrame.style.height = "150px";
recognizrFrame.style.width = "250px"; 
recognizrFrame.setAttribute("scrolling", "no");
thisElement.parentNode.appendChild(recognizrFrame);
