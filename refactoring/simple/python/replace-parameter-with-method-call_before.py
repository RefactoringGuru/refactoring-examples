basePrice = quantity * itemPrice
seasonalDiscount = this.getSeasonalDiscount()
fees = this.getFees()
finalPrice = discountedPrice(basePrice, seasonalDiscount, fees)