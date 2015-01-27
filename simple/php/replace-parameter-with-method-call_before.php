$basePrice = $this->quantity * $this->itemPrice;
$seasonDiscount = $store->getSeasonalDiscount();
$fees = $store->getFees();
$finalPrice = $this->discountedPrice($basePrice, $seasonDiscount, $fees);