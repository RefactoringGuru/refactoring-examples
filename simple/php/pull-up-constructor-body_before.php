<?php
class Manager extends Employee {
  public __construct($name, $id, $grade) {
    $this->name = $name;
    $this->id = $id;
    $this->grade = $grade;
  }
  ...
}