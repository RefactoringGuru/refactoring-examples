<?php
class NullCustomer extends Customer {
  public function isNull() {
    return true;
  }
  public function getPlan() {
    return new NullPlan();
  }
  // Some other NULL functionality.
}

// Replace null values with Null-object.
$customer = ($order->customer != null) ?
  $order->customer :
  new NullCustomer;

// Use Null-object as if it's normal subclass.
$plan = $customer->getPlan();