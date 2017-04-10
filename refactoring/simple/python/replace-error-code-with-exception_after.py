def withdraw(self, amount):
    if amount > self.balance:
        raize BalanceException()
    self.balance -= amount