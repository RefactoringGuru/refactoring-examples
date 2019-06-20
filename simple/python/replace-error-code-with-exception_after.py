def withdraw(self, amount):
    if amount > self.balance:
        raise BalanceException()
    self.balance -= amount