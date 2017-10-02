class Order
{
  // ...

  public double calculateTotal()
  {
    double total = 0;
    foreach (Product product in getProducts())
    {
      total = product.quantity * product.price;
    }
    total = applyRegionalDiscounts(total);
    return total;
  }

  public double applyRegionalDiscounts(double total)
  {
    double result = total;
    switch (user.getCountry())
    {
      case "US": result *= 0.85; break;
      case "RU": result *= 0.75; break;
      case "CN": result *= 0.9; break;
      // ...
    }
    return result;
  }