class Order {
  // ...

  calculateTotal(): number {
    // ...
    total = Discounts.applyRegionalDiscounts(total, user.getCountry());
    total = Discounts.applyCoupons(total);
    // ...
  }


class Discounts {
  // ...

  static applyRegionalDiscounts(total: number, country: string): number {
    let result = total;
    switch (country) {
      case "US": result *= 0.85; break;
      case "RU": result *= 0.75; break;
      case "CN": result *= 0.9; break;
      // ...
    }
    return result;
  }

  static applyCoupons(total: number): number {
      // ...
  }
