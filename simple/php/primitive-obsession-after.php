<?php
class contactUs {  
  private $streetNum;
  private $streetName;
  private $zipCode;

  public function addressCountryA() {
    $address = new Array();
    $this->streetNum = 10;
    $this->streetName = 'Oregon St';
    $this->zipCode = '7726128';

    return $this->streetName.' '.$this->streetNum.','.$this->zipCode;
  }

  public function addressCountryB() {
    $address = new Array();
    $this->streetNum = 22;
    $this->streetName = 'Berlin St';
    $this->zipCode = '882719';

    return $this->streetName.' '.$this->streetNum.','.$this->zipCode;
  }
  // ...
}
