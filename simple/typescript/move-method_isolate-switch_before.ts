class Order {
  // ...

  calculateTotal(): number {
    let total = 0;
    for (let product : getProducts()) {
      total += product.quantity * product.price;
    }
    total = applyRegionalDiscounts(total);
    return total;
  }

  applyRegionalDiscounts(total: number): number {
    let result = total;
    switch (user.getCountry()) {
      case 'US': result *= 0.85; break;
      case 'RU': result *= 0.75; break;
      case 'CN': result *= 0.9; break;
      // ...
    }
    return result;
  }
}