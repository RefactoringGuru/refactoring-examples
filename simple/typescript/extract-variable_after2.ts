renderBanner(): void {
  let isChanged = frame.isChanged || target.isChanged;
  let mustRedraw = isChanged || experiment.isRunning();
  let isFullScreen = frame.getSize() == screen.getSize();

  if (isFullScreen && mustRedraw) {
    // print banner
  }
}