<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example;

use RefactoringGuru\Patterns\AbstractFactory\Example\Factories\GUIFactoryInterface;

class Application
{
    private $button;

    private $checkbox;

    public function __construct(GUIFactoryInterface $factory)
    {
        $this->button = $factory->createButton();
        $this->checkbox = $factory->createCheckbox();
    }

    public function paint()
    {
        $this->button->paint();
        $this->checkbox->paint();
    }
}
