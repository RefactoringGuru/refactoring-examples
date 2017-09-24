<?php
function disabilityAmount() {
  if ($this->seniority < 2) return 0;
  if ($this->monthsDisabled > 12) return 0;
  if ($this->isPartTime) return 0;
  // compute the disability amount
  ...