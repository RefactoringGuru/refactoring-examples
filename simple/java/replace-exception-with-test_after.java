double getValueForPeriod (int periodNumber) {
  if (periodNumber >= _values.length) return 0;
  return _values[periodNumber];
}