public class Order 
{
  //...
  public double Price() 
  {
    return new PriceCalculator(this).Compute();
  }
}

public class PriceCalculator 
{
  private double primaryBasePrice;
  private double secondaryBasePrice;
  private double tertiaryBasePrice;
  
  public PriceCalculator(Order order) 
  {
    // copy relevant information from order object.
    //...
  }
  
  public double Compute() 
  {
    // long computation.
    //...
  }
}