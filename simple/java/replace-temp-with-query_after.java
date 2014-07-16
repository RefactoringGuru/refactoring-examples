if (basePrice() > 1000)
  return basePrice() * 0.95;
else
  return basePrice() * 0.98;

...

double basePrice() {
  return _quantity * _itemPrice;
}