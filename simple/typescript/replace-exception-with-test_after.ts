getValueForPeriod(periodNumber: number): number {
  if (periodNumber >= values.length) {
    throw new Error();
  }
  return values[periodNumber];
}
