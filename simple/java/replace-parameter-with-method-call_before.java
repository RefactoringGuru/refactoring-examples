int basePrice = quantity * itemPrice;
double seasonDiscount = store.getSeasonalDiscount();
double fees = store.getFees();
double finalPrice = discountedPrice(basePrice, seasonDiscount, fees);