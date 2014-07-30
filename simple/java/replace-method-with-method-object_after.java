class Order {
  //...
  public double price() {
    return new PriceCalculator(this).compute();
  }
}

class PriceCalculator {
  private double primaryBasePrice;
  private double secondaryBasePrice;
  private double tertiaryBasePrice;
  
  public PriceCalculator(Order order) {
    // copy relevant information from order object.
    //...
  }
  
  public double compute() {
    // long computation.
    //...
  }
}