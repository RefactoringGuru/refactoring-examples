void setValue (String name, int value) {
  if (name.equals("height")) {
    _height = value;
    return;
  }
  if (name.equals("width")) {
    _width = value;
    return;
  }
  Assert.shouldNeverReachHere();
}