<?php

namespace RefactoringGuru\Patterns\FactoryMethod\Example\Logistics;

abstract class AbstractLogistics
{
    abstract public function createVehicle();

    public function deliver($package, $destination)
    {
        $transport = $this->createVehicle();
        $transport->deliver($package, $destination);
    }
}
