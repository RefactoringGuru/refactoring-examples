function initConnection() {
  if (channel == null) {
    throw new InvalidArgumentException('Communication channel must be specified');
  }

  switch ($customer) {
    case $INTERNET:
      $connection = new HihgSpeedConnection();
      break;
    case $HOME_PHONE:
      $connection = new HomeConnection();
      break;
    case $CELL_PHONE:
      $connection = new LTEConnection();
  }
}
