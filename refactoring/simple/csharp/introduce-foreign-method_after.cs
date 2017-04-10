class Report 
{
  //...
  void SendReport() 
  {
    DateTime nextDay = NextDay(previousEnd);
    //...
  }
  private static DateTime NextDay(DateTime date) 
  {
    return date.AddDays(1);
  }
}