<?php

/**
 * Client class.
 */
class RoundHole {
    private $diameter;

    public function __construct($diameter) {
        $this->diameter = $diameter;
    }

    public function getDiameter() {
        return $this->diameter;
    }

    public function fits(Peg $peg) {
        return $peg->maxDimension() <= $this->getDiameter();
    }

    public function doFit(Peg $peg) {
        if (!$this->fits($peg)) {
            $peg->makeFit($this);
        }
        print("Perfect, the peg is inside the hole!");
    }
}

/**
 * Interface desired by Client.
 */
interface Peg {
    public function maxDimension();
    public function makeFit(RoundHole $roundHole);
}


/**
 * Existing *legacy* class (does not implement client interface)
 */
class SquarePeg {
    private $width;
    public function __construct($width) {
        $this->width = $width;
    }
    public function getWidth() {
        return $this->width;
    }
    public function setWidth($width) {
        $this->width = $width;
    }
}

/**
 * Adapter class.
 */
class SquarePegAdapter implements Peg {
    private $squarePeg;

    public function __construct(SquarePeg $squarePeg) {
        $this->squarePeg = $squarePeg;
    }

    public function maxDimension() {
        return sqrt(2 * pow($this->squarePeg->getWidth(), 2));
    }

    public function makeFit(RoundHole $roundHole) {
        $maxWidth = $roundHole->getDiameter() / sqrt(2);

        if ($maxWidth < $this->squarePeg->getWidth()) {
            print("Reducing SquarePeg width of " . $this->squarePeg->getWidth() . " to " . $maxWidth);
            $this->squarePeg->setWidth($maxWidth);
        }
    }
}

/**
 * Example app code.
 */
$roundHole = new RoundHole(5);
for ($i = 3; $i < 10; $i++) {
    $peg = new SquarePegAdapter(new SquarePeg($i));
    $roundHole->doFit($peg);
}