<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example\Factories;

use RefactoringGuru\Patterns\AbstractFactory\Example\Buttons\MacOSButton;
use RefactoringGuru\Patterns\AbstractFactory\Example\Checkboxes\MacOSCheckbox;

class MacOSFactory implements GUIFactoryInterface
{
    /**
     * @inheritdoc
     */
    public function createButton()
    {
        return new MacOSButton();
    }

    /**
     * @inheritdoc
     */
    public function createCheckbox()
    {
        return new MacOSCheckbox();
    }
}
