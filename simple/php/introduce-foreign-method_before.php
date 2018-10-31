<?php
class Report {
  //...
  function sendReport() {
    $previousDate = clone $this->previousDate;
    $paymentDate = $previousDate->modify("+7 days");
    //...
  }
}