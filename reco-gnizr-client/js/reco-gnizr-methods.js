/* 
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Title : Reco-gnizr library methods
Author : Kishor Datar
URL : 

Description : Contains utility methods used by reco-gnizr js files

Created : see svn
Modified : see svn

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
*/

function getRootDirectory ()
{
  rootDir = new String(document.location.href);
  if(rootDir.indexOf('#') >= 0)
    rootDir = rootDir.substring(0, rootDir.indexOf('#'));
  if(rootDir.indexOf('?') >= 0)
    rootDir = rootDir.substring(0, rootDir.indexOf('?'));
  rootDir = rootDir.substring(0, rootDir.lastIndexOf('/')+1);
  return rootDir;
}



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

function delay(n)
{
  for(var i=0; i<n*500; i++);
}

function getEventTarget (obj)
{
  if (obj.event) // test if event object is present
     o = obj.event.srcElement;
  else
    o = obj;
  return o;
}


function addEvent (object, e, method)
{
  if(!object || !e || !method)
    return null;

  var func = null;
  if(object.attachEvent)
  {
    e = "on" + e; // click for mozilla is onclick for ie
    func = "attachEvent";
  }
  else if(object.addEventListener)
  {
    func = "addEventListener";
  }
  else
  {
    return null;
  }
  object[func](e, method, false);

  return object;
}

