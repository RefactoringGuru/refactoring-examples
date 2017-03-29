class Order {
  function validate() {
    // validate customer
    $this->getInfo($customer);
  }

  function dataProcess() {
    $this->getInfo($customer);
    // data process
  }

  function getInfo($customer) {
    echo $this->$cusomer->getName();
    echo $this->$cusomer->getEmail();
  }
}
