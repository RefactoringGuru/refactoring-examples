public abstract class Bird 
{
  // ...
  public abstract double GetSpeed();
}

class European: Bird 
{
  public override double GetSpeed() 
  {
    return GetBaseSpeed();
  }
}
class African: Bird 
{
  public override double GetSpeed() 
  {
    return GetBaseSpeed() - GetLoadFactor() * numberOfCoconuts;
  }
}
class NorwegianBlue: Bird
{
  public override double GetSpeed() 
  {
    return isNailed ? 0 : GetBaseSpeed(voltage);
  }
}

// Somewhere in client code
speed = bird.GetSpeed();