private int _low, _high;
boolean includes (int arg) {
  return arg >= getLow() && arg <= getHigh();
}
int getLow() {return _low;}
int getHigh() {return _high;}