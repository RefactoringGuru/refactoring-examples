class Order:
    #...
    def price(self):
        return PriceCalculator(self).compute()


class PriceCalculator:
    self._primaryBasePrice = 0
    self._secondaryBasePrice = 0
    self._tertiaryBasePrice = 0

    def __init__(self, order):
        # copy relevant information from order object.
        #...

    def compute(self):
        # long computation.
        #...