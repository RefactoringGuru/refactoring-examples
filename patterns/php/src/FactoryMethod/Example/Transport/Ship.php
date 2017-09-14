<?php

namespace RefactoringGuru\Patterns\FactoryMethod\Example\Transport;

class Ship implements TransportInterface
{
    public function deliver($package, $destination)
    {
        printf('The ship with %s is swimming to %s!', $package, $destination);
    }
}
