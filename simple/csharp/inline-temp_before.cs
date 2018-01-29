bool HasDiscount(Order order)
{
  double basePrice = order.BasePrice();
  return basePrice > 1000;
}