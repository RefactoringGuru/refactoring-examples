boolean hasDiscount(Order order) {
  return order.basePrice() > 1000;
}