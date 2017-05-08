class Superclass {
  $box;
  // some methods
  void calculate() {
    $n = $this->$box->geValue() + $i;
    // othe operations with box
  }
}

class FOO extends Superclass {
  // some methods
  void invoke() {
    // other operations
  }  
}

class BAR extends Superclass {
  void equal() {
    // other operations
  }
  // some methods
}
