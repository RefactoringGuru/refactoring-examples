class Bird:
    # ...
    def getSpeed(self):
        if self.type == EUROPEAN:
            return self.getBaseSpeed()
        elif self.type == AFRICAN:
            return self.getBaseSpeed() - self.getLoadFactor() * self.numberOfCoconuts
        elif self.type == NORWEGIAN_BLUE:
            return 0 if isNailed else self.getBaseSpeed(self.voltage)
        else:
            raise Exception("Should be unreachable")