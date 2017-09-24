<?php
class Soldier {
  public $health; // int
  public $weapon; // Weapon class
  public function attack() { ... };
}

class Weapon {
  public $damage; // int
  public $weaponStatus; // int
  public function getDamage() { ... };
}