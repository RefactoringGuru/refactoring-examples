class Employee {
  static create(type: number): Employee {
    let employee = new Employee(type);
    // do some heavy lifting.
    return employee;
  }
  //...
}