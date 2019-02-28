<?php
class Order {
  // ...
  public function price() {
    return (new PriceCalculator($this))->compute();
  }
}

class PriceCalculator {
  private $primaryBasePrice;
  private $secondaryBasePrice;
  private $tertiaryBasePrice;
  
  public function __construct(Order $order) {
      // Copy relevant information from the
      // order object.
  }
  
  public function compute() {
    // Perform long computation.
  }
}