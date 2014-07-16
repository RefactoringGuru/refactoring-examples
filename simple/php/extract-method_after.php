function printOwing() {
  $this->printBanner();
  $this->printDetails($this->getOutstanding());
}

function printDetails ($outstanding) {
  print("name:  " . $this->name);
  print("amount " . $outstanding);
}