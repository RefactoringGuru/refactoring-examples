<?php
function withdraw($amount) {
  if ($amount > $this->balance) {
    throw new BalanceException();
  }
  $this->balance -= $amount;
}
