int basePrice = quantity * itemPrice;
int discountLevel = getDiscountLevel();
double finalPrice = discountedPrice(basePrice, discountLevel);