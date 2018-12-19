<?php
class Report {
  //...
  public function sendReport() {
    $paymentDate = self::nextWeek($this->previousDate);
    //...
  }
  /**
   * Foreign method. Should be in Date.
   */
  private static function nextWeek(DateTime $arg) {
    $previousDate = clone $arg;
    return $previousDate->modify("+7 days");
  }
}