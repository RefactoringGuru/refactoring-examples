void renderBanner() 
{
  boolean mustRdraw = (frame.isChanged() || target.isChanged()) 
    || experiment.isRunning();
  boolean isFullScreen = frame.getSize().equal(screen.getSize());

  if (isFullScreen && mustRedraw) 
  {
    // print
  }
}
