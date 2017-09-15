<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example\Checkboxes;

class MacOSCheckbox implements CheckboxInterface
{
    public function paint()
    {
        echo 'You have created MacOSCheckbox.';
    }
}
