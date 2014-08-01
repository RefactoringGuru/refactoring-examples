class Bird:
    #...
    def getSpeed(self):
        pass

class European(Bird):

    def getSpeed(self):
        return self.getBaseSpeed()
    
    
class African(Bird):

    def getSpeed(self):
        return self.getBaseSpeed() - self.getLoadFactor() * self.numberOfCoconuts


class NorvegianBlue(Bird):

    def getSpeed():
        return 0 if self.isNailed else self.getBaseSpeed(self.voltage)

# Somewhere in client code
speed = bird.getSpeed()