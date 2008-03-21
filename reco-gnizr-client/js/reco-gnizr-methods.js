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
