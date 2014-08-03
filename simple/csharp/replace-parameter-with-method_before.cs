int basePrice = quantity * itemPrice;
int discountLevel = GetDiscountLevel();
double finalPrice = DiscountedPrice(basePrice, discountLevel);