int basePrice = quantity * itemPrice;
double seasonDiscount = store.GetSeasonalDiscount();
double fees = store.GetFees();
double finalPrice = GiscountedPrice(basePrice, seasonDiscount, fees);