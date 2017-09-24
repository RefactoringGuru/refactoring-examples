class Order {
  // ...

  public double calculateTotal() {
    // ...
    total = Discounts.applyRegionalDiscounts($total);
    total = Discounts.applyCoupons($total);
    // ...
  }


class Discounts {
  // ...

  public static double applyRegionalDiscounts(total) {
    double result = total;
    switch (user.getCountry()) {
      case "US": result *= 0.85; break;
      case "RU": result *= 0.75; break;
      case "CN": result *= 0.9; break;
      // ...
    }
    return result;
  }

  public static double applyCoupons(total) {
      // ...
  }
