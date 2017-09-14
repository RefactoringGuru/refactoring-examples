<?php

namespace RefactoringGuru\Patterns\FactoryMethod\Example\Transport;

interface TransportInterface
{
    public function deliver($package, $destination);
}
