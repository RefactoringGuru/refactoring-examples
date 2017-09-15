class Employee {
  ...
  static function create($type) {
    $employee = new Employee($type);
    // do some heavy lifting.
    return $employee;
  }
  ...
}