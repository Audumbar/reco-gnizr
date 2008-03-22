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

