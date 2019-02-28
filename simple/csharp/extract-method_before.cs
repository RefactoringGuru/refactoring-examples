void PrintOwing() 
{
  PrintBanner();

  // Print details.
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + GetOutstanding());
}