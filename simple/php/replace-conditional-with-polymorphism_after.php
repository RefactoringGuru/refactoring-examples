<?php
abstract class Bird {
  // ...
  abstract function getSpeed();
  // ...
}

class European extends Bird {
  public function getSpeed() {
    return $this->getBaseSpeed();
  }
}
class African extends Bird {
  public function getSpeed() {
    return $this->getBaseSpeed() - $this->getLoadFactor() * $this->numberOfCoconuts;
  }
}
class NorwegianBlue extends Bird {
  public function getSpeed() {
    return ($this->isNailed) ? 0 : $this->getBaseSpeed($this->voltage);
  }
}

// Somewhere in Client code.
$speed = $bird->getSpeed();