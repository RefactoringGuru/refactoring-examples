void renderBanner() {
  boolean isChanged = frame.isChanged || target.isChanged;
  boolean mustRedraw = isChanged || experiment.isRunning();
  boolean isFullScreen = frame.getSize() == screen.getSize();

  if (isFullScreen && mustRedraw) {
    // print banner
  }
}
