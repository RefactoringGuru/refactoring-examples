void renderBanner() 
{
  // When all the parameters are set, counted 
  // the boundaries of the frame, we print the 
  // result on the screen.
  if (((frame.isChanged() || target.isChanged()) || 
        experiment.isRunning()) &&
        frame.getSize().equal(screen.getSize())) 
  {
    // print
  }
}
