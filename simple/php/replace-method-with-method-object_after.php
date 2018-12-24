<?php
class Order {
  ...
  public function price() {
    return (new PriceCalculator($this))->compute();
  }
}

class PriceCalculator {
  private $primaryBasePrice;
  private $secondaryBasePrice;
  private $tertiaryBasePrice;
  
  public function __construct(Order $order) {
    // copy relevant information from order object.
    ...
  }
  
  public function compute() {
    // long computation.
    ...
  }
}