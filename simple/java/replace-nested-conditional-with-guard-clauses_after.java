public double getPayAmount() {
  if (_isDead)
    return deadAmount();
  if (_isSeparated)
    return separatedAmount();
  if (_isRetired)
    return retiredAmount();
  return normalPayAmount();
}