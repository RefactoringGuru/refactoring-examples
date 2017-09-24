<?php
class Order {
  // ...

  public function calculateTotal()
  {
    // ...
    $total = Discounts::applyRegionalDiscounts($total);
    $total = Discounts::applyCoupons($total);
    // ...
  }


class Discounts {
  // ...

  public static function applyRegionalDiscounts($total) {
    $result = $total;
    switch ($this->user->getCountry()) {
      case "US": $result *= 0.85; break;
      case "RU": $result *= 0.75; break;
      case "CN": $result *= 0.9; break;
      // ...
    }
    return $result;
  }

  public static function applyCoupons($total) {
    // ...
  }
