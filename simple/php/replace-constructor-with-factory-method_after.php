<?php
class Employee {
  ...
  static public function create($type) {
    $employee = new Employee($type);
    // do some heavy lifting.
    return $employee;
  }
  ...
}