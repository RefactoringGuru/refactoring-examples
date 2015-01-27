basePrice = quantity * itemPrice
seasonalDiscount = store.getSeasonalDiscount()
fees = store.getFees()
finalPrice = discountedPrice(basePrice, seasonalDiscount, fees)