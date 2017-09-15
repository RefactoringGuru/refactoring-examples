<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example\Buttons;

class MacOSButton implements ButtonInterface
{
    public function paint()
    {
        echo 'You have created MacOSButton.';
    }
}
