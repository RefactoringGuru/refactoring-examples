void PrintOwing() 
{
  PrintBanner();

  //print details
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + GetOutstanding());
}