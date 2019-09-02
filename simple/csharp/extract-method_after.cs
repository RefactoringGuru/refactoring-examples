void PrintOwing()
{
  this.PrintBanner();
  this.PrintDetails(this.GetOutstanding());
}

void PrintDetails(double outstanding)
{
  Console.WriteLine("name: " + this.name);
  Console.WriteLine("amount: " + this.outstanding);
}