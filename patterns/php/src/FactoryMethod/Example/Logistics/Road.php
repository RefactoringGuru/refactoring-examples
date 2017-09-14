<?php

namespace RefactoringGuru\Patterns\FactoryMethod\Example\Logistics;

use RefactoringGuru\Patterns\FactoryMethod\Example\Transport\Truck;

class Road extends AbstractLogistics
{
    public function createVehicle()
    {
        return new Truck();
    }
}
