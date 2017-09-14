<?php

require_once __DIR__ . '/Logistics/AbstactLogistics.php';
require_once __DIR__ . '/Logistics/Road.php';
require_once __DIR__ . '/Logistics/Sea.php';
require_once __DIR__ . '/Transport/TransportInterface.php';
require_once __DIR__ . '/Transport/Truck.php';
require_once __DIR__ . '/Transport/Ship.php';

use RefactoringGuru\Patterns\FactoryMethod\Example\Logistics\Road;
use RefactoringGuru\Patterns\FactoryMethod\Example\Logistics\Sea;

$logistics = isset($_GET['location']) && $_GET['location'] == 'Europe'
    ? new Road()
    : new Sea();

$logistics->deliver('chocolate', 'New York');
