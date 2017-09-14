<?php

namespace RefactoringGuru\Patterns\FactoryMethod\Example\Transport;

class Truck implements TransportInterface
{
    public function deliver($package, $destination)
    {
        printf('The truck with %s is going to %s!', $package, $destination);
    }
}
