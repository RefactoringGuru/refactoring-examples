<?php
function getValueForPeriod($periodNumber) {
  try {
    return $this->values[$periodNumber];
  } catch (ArrayIndexOutOfBoundsException $e) {
    return 0;
  }
}