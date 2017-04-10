class Report {
  //...
  void sendReport() {
    $previousDate = clone $this->previousDate;
    $paymentDate = $previousDate->modify('+7 days');
    //...
  }
}