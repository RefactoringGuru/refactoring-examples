<?php
class Order {
  // ...

  public function calculateTotal() {
    $total = 0;
    foreach ($this->getProducts() as $product) {
      $total += $product->quantity * $product->price;
    }

    // Apply regional discounts.
    switch ($this->user->getCountry()) {
      case "US": $total *= 0.85; break;
      case "RU": $total *= 0.75; break;
      case "CN": $total *= 0.9; break;
      // ...
    }

    return $total;
  }