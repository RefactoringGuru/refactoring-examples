$basePrice = $this->quantity * $this->itemPrice;
$discountLevel = $this->getDiscountLevel();
$finalPrice = $this->discountedPrice($basePrice, $discountLevel);