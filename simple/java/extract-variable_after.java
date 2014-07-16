final boolean isMacOs = platform.toUpperCase().indexOf("MAC") > -1;
final boolean isIE = browser.toUpperCase().indexOf("IE")  > -1;
final boolean wasResized = resize > 0;

if (isMacOs && isIE && wasInitialized() && wasResized)
{
  // do something
}