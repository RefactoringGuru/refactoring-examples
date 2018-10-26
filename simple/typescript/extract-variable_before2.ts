renderBanner():void {
  // Render banner only if we're in fullscreen mode
  // and a change is requested either in frame or 
  // target, or experiment is active.
  if (((frame.isChanged || target.isChanged) || 
         experiment.isRunning()) &&
         (frame.getSize() == screen.getSize()))  {
    // print banner
  }
}