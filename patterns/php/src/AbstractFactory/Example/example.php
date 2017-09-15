<?php

require_once __DIR__ . '/Buttons/ButtonInterface.php';
require_once __DIR__ . '/Buttons/MacOSButton.php';
require_once __DIR__ . '/Buttons/WindowsButton.php';
require_once __DIR__ . '/Checkboxes/CheckboxInterface.php';
require_once __DIR__ . '/Checkboxes/MacOSCheckbox.php';
require_once __DIR__ . '/Checkboxes/WindowsCheckbox.php';
require_once __DIR__ . '/Factories/GUIFactoryInterface.php';
require_once __DIR__ . '/Factories/MacOSFactory.php';
require_once __DIR__ . '/Factories/WindowsFactory.php';
require_once __DIR__ . '/Application.php';

use RefactoringGuru\Patterns\AbstractFactory\Example\Factories\MacOSFactory;
use RefactoringGuru\Patterns\AbstractFactory\Example\Factories\WindowsFactory;
use RefactoringGuru\Patterns\AbstractFactory\Example\Application;

$factory = isset($_ENV['os']) && $_ENV['os'] == 'MacOS'
    ? new MacOSFactory()
    : new WindowsFactory();

$application = new Application($factory);
$application->paint();