void renderBanner() 
{
  bool isChanged = frame.isChanged || target.isChanged;
  bool mustRedraw = isChanged || experiment.isRunning();
  bool isFullScreen = frame.getSize() == screen.getSize();

  if (isFullScreen && mustRedraw) 
  {
    // print banner
  }
}