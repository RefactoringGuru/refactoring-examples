class Soldier 
{
  public int Health
  { get; set; }
  public Weapon Weapon
  { get; set; }
  
  public void Attack() 
  {
    // ...
  }
}

class Weapon {
  public int Damage
  { get; set; }
  public int WeaponStatus
  { get; set; }
  
  public int GetDamage() 
  {
    // ...
  }
}