<?php
function printOwing() {
  $this->printBanner();

  //print details
  print("name:  " . $this->name);
  print("amount " . $this->getOutstanding());
}