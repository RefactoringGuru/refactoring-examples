class Weapon:
    self.damage = 0
    self.weaponStatus = 0

    def getDamage(self):
        # ...

class Soldier:
    health = 0
    weapon = Weapon()

    def attack(self):
        # ...