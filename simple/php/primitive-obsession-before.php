<?php
class contactUs {  
  public function addressCountryA() {
    $address = new Array();
    $address['streetNum'] = 10;
    $address['streetName'] = 'Oregon St';
    $address['zipCode'] = '7726128';

    return $address['streetName'].' '.$address['streetNum'].','.$address['zipCode'];
  }

  public function addressCountryB() {
    $address = new Array();
    $address['streetNum'] = 22;
    $address['streetName'] = 'Berlin St';
    $address['zipCode'] = '882719';

    return $address['streetName'].' '.$address['streetNum'].','.$address['zipCode'];
  }
  // ...
}
