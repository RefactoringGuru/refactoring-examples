<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example\Factories;

use RefactoringGuru\Patterns\AbstractFactory\Example\Buttons\ButtonInterface;
use RefactoringGuru\Patterns\AbstractFactory\Example\Checkboxes\CheckboxInterface;

interface GUIFactoryInterface
{
    /**
     * @return ButtonInterface
     */
    public function createButton();

    /**
     * @return CheckboxInterface
     */
    public function createCheckbox();
}
