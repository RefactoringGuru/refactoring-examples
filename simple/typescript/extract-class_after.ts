class Soldier {
  health: number;
  weapon: Weapon;
  attack(): void {
    //...
  }
}

class Weapon {
  damage: number;
  weaponStatus: number;
  getDamage(): number {
    //...
  }
}