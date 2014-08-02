double HasDiscount(Order order) 
{
  return (order.BasePrice() > 1000);
}