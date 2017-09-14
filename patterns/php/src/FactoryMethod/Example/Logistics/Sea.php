<?php

namespace RefactoringGuru\Patterns\FactoryMethod\Example\Logistics;

use RefactoringGuru\Patterns\FactoryMethod\Example\Transport\Ship;

class Sea extends AbstractLogistics
{
    public function createVehicle()
    {
        return new Ship();
    }
}
