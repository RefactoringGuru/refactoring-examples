<?php
function getRating() {
  return ($this->numberOfLateDeliveries > 5) ? 2 : 1;
}