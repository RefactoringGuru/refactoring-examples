public double getPayAmount() {
  if (_isDead)
    result = deadAmount();
  else {
    if (_isSeparated)
      result = separatedAmount();
    else {
      if (_isRetired)
        result = retiredAmount();
      else
        result = normalPayAmount();
    }
  }
  return result;
}