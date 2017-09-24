class Order {
  // ...

  public double calculateTotal() {
    double total = 0;
    for (Product product : getProducts()) {
      total = product.quantity * product.price;
    }

    // Apply regional discounts.
    switch (user.getCountry()) {
      case "US": total *= 0.85; break;
      case "RU": total *= 0.75; break;
      case "CN": total *= 0.9; break;
      // ...
    }

    return total;
  }