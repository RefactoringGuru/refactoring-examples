function getValueForPeriod($periodNumber) {
  if ($periodNumber >= count($this->values)) {
    return 0;
  }
  return $this->values[$periodNumber];
}