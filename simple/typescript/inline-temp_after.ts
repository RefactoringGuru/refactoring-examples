hasDiscount(order: Order): boolean {
  return order.basePrice() > 1000;
}