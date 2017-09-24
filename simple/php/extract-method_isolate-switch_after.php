<?php
class Order {
  // ...

  public function calculateTotal() {
    $total = 0;
    foreach ($this->getProducts() as $product) {
      $total = $product->quantity * $product->price;
    }
    $total = $this->applyRegionalDiscounts($total);
    return $total;
  }

  public function applyRegionalDiscounts($total) {
    $result = $total;
    switch ($this->user->getCountry()) {
      case "US": $result *= 0.85; break;
      case "RU": $result *= 0.75; break;
      case "CN": $result *= 0.9; break;
      // ...
    }
    return $result;
  }