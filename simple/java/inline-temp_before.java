double hasDiscount(Order order) {
  double basePrice = order.basePrice();
  return (basePrice > 1000);
}