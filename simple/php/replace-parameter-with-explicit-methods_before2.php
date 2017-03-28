function setValue($name, $value) {
  switch ($name) {
    case "height":
      $height = $value;
      break;
    case "width":
      $width = $value;
    }
    assert("Should never reach here");
}