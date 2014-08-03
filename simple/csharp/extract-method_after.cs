void PrintOwing() 
{
  PrintBanner();
  PrintDetails(GetOutstanding());
}

void PrintDetails(double outstanding) 
{
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}