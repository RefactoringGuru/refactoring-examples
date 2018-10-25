class Order {
  //...
  price(): number {
    return new PriceCalculator(this).compute();
  }
}

class PriceCalculator {
  private _primaryBasePrice: number;
  private _secondaryBasePrice: number;
  private _tertiaryBasePrice: number;
  
  constructor(order: Order) {
    // copy relevant information from order object.
    //...
  }
  
  compute(): number {
    // long computation.
    //...
  }
}