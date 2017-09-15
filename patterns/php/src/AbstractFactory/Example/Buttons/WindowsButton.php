<?php

namespace RefactoringGuru\Patterns\AbstractFactory\Example\Buttons;

class WindowsButton implements ButtonInterface
{
    public function paint()
    {
        echo 'You have created WindowsButton.';
    }
}
