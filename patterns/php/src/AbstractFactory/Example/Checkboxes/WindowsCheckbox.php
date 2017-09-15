<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example\Checkboxes;

class WindowsCheckbox implements CheckboxInterface
{
    public function paint()
    {
        echo 'You have created WindowsCheckbox.';
    }
}