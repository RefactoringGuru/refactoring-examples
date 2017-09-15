<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example\Factories;

use RefactoringGuru\Patterns\AbstractFactory\Example\Buttons\WindowsButton;
use RefactoringGuru\Patterns\AbstractFactory\Example\Checkboxes\WindowsCheckbox;

class WindowsFactory implements GUIFactoryInterface
{
    /**
     * @inheritdoc
     */
    public function createButton()
    {
        return new WindowsButton();
    }

    /**
     * @inheritdoc
     */
    public function createCheckbox()
    {
        return new WindowsCheckbox();
    }
}
