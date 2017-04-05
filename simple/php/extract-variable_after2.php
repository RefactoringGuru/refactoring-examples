void renderBanner() {
  $isChanged = $frame->isChanged || $target->isChanged;
  $mustRedraw = $isChanged || $experiment->isRunning();
  $isFullScreen = $frame->getSize() == $screen->getSize();

  if ($isFullScreen && $mustRedraw) {
    // print banner
  }
}
