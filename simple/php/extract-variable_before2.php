void renderBanner() {
  // When all the parameters are set, counted 
  // the boundaries of the frame, we print the 
  // result on the screen.
  if ((($frame->isChanged() or $target->isChanged()) or 
        $experiment->isRunning()) and
        $frame->getSize()->equal($screen->getSize())) {
    // print
  }
}
