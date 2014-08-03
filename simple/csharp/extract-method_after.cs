void PrintOwing() 
{
  PrintBanner();
  PrintDetails(getOutstanding());
}

void PrintDetails(double outstanding) 
{
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
}