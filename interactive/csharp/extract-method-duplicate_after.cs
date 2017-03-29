class Order 
{
  void validate() 
  {
    // validate customer
    getInfo(customer);
  }

  void dataProcess() 
  {
    getInfo(customer);
    // data process
  }

  void getInfo(Customer customer) 
  {
    Console.WriteLine(cusomer.getName);
    Console.WriteLine(cusomer.getEmail);
  }
}
