class Order {
  // ...

  public double calculateTotal()
  {
    // ...
    total = Discounts.applyRegionalDiscounts(total, user.getCountry());
    total = Discounts.applyCoupons(total);
    // ...
  }


class Discounts {
  // ...

  public static double applyRegionalDiscounts(double total, string country)
  {
    double result = total;
    switch (country)
    {
      case "US": result *= 0.85; break;
      case "RU": result *= 0.75; break;
      case "CN": result *= 0.9; break;
      // ...
    }
    return result;
  }

  public static double applyCoupons(double total) {
      // ...
  }
