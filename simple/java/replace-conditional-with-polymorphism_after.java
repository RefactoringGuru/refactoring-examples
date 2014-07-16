abstract class Bird {
  ...
  abstract double getSpeed();
  ...
}

class European extends Bird {
  double getSpeed() {
    return getBaseSpeed();
  }
}
class African extends Bird {
  double getSpeed() {
    return getBaseSpeed() - getLoadFactor() * _numberOfCoconuts;
  }
}
class NorvegianBlue extends Bird {
  double getSpeed() {
    return (_isNailed) ? 0 : getBaseSpeed(_voltage);
  }
}

// Somewhere in Client code.
speed = bird.getSpeed();