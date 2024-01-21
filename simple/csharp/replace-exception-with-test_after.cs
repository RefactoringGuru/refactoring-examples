double GetValueForPeriod(int periodNumber) 
{
  if (periodNumber >= values.Length || periodNumber < 0) 
  {
    return 0;
  }
  return values[periodNumber];
}
