void PrintOwing()
{
  PrintBanner();
  PrintDetails(GetOutstanding());
}

void PrintDetails(double outstanding)
{
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
}