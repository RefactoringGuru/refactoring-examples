void PrintOwing() 
{
  PrintBanner();
  PrintDetails(getOutstanding());
}

void PrintDetails(double outstanding) 
{
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}