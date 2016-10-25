<?php

/**
 * Component interface.
 */
interface HasClimate {
    public function temperature();
}

/**
 * Concrete component class
 */
class Planet implements HasClimate {
    private $temperature;

    public function __construct($temperature) {
        $this->temperature = $temperature;
    }

    public function temperature() {
        return $this->temperature;
    }
}

/**
 * Base decorator class
 */
class ClimateDecorator implements HasClimate {
    private $planet; // HasClimate

    public function __construct(HasClimate $planet) {
        $this->planet = $planet;
    }

    public function temperature() {
        return $this->planet->temperature();
    }
}

/**
 * Concrete decorator A
 */
class GlobalWarming extends ClimateDecorator {
    public function temperature() {
        $temp = parent::temperature();
        return $temp * 1.2;
    }
}

/**
 * Concrete decorator B
 */
class NuclearWinter extends ClimateDecorator {
    public function temperature() {
        $temp = parent::temperature();
        return $temp * 0.5;
    }
}

/**
 * Example app code.
 */
$earth = new Planet(30);
print($earth->temperature()); // 30

$earth = new GlobalWarming($earth);
print($earth->temperature()); // 36

$earth = new NuclearWinter($earth);
print($earth->temperature()); // 18

$earth = new NuclearWinter($earth);
print($earth->temperature()); // 9