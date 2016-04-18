class Order:
    #...
    def price(self):
        return PriceCalculator(self).compute()


class PriceCalculator:
    def __init__(self, order):
        self._primaryBasePrice = 0
        self._secondaryBasePrice = 0
        self._tertiaryBasePrice = 0
        # copy relevant information from order object.
        #...

    def compute(self):
        # long computation.
        #...
