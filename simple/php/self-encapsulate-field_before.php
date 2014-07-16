private $low;
private $high;

function includes($arg) {
  return $arg >= $this->low && $arg <= $this->high;
}