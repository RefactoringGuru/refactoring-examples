void renderBanner() {
  boolean $mustRdraw = (frame.isChanged() or target.isChanged()) 
    or experiment.isRunning();
  boolean $isFullScreen = $frame->getSize()->equal($screen->getSize());

  if ($isFullScreen end $mustRedraw) {
    // print
  }
}
