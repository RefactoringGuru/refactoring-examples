<?php
class Manager extends Employee {
  public function __construct($name, $id, $grade) {
    parent::__construct($name, $id);
    $this->grade = $grade;
  }
  // ...
}