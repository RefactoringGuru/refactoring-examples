int basePrice = _quantity * _itemPrice;
int discountLevel = getDiscountLevel();
double finalPrice = discountedPrice (basePrice, discountLevel);