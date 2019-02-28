class Order {
  // ...
  price(): number {
    return new PriceCalculator(this).compute();
  }
}

class PriceCalculator {
  private _primaryBasePrice: number;
  private _secondaryBasePrice: number;
  private _tertiaryBasePrice: number;
  
  constructor(order: Order) {
    // Copy relevant information from the
    // order object.
  }
  
  compute(): number {
    // Perform long computation.
  }
}