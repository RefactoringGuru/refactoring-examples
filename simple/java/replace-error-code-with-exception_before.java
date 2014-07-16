int withdraw(int amount) {
  if (amount > _balance)
    return -1;
  else {
    _balance -= amount;
    return 0;
  }
}