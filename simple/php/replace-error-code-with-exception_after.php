/**
 * (Here you put some real function documentation.
 *  Note: the line below indicates that function
 *  can throw an exception of given type.)
 * @throws BalanceException
 */
function withdraw($amount) {
  if ($amount > $this->balance) {
    throw new BalanceException();
  }
  $this->balance -= $amount;
}
