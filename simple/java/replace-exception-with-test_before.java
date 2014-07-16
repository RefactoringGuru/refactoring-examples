double getValueForPeriod (int periodNumber) {
  try {
    return _values[periodNumber];
  } catch (ArrayIndexOutOfBoundsException e) {
    return 0;
  }
}