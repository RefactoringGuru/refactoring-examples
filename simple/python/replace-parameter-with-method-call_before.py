basePrice = quantity * itemPrice
seasonalDiscount = self.getSeasonalDiscount()
fees = self.getFees()
finalPrice = discountedPrice(basePrice, seasonalDiscount, fees)
