function properties($param) {
  $bean = $this->setSetting($param);

  // set other properties...
}

function setSetting($param) {
  $bean = null;
  switch ($param) {
    case "DAO":
      $bean = new DAO();
      break;
    case "DAOStub":
      $bean = new DAOStub();
      break;
    case "AppConfig":
      $bean = new AppConfig();
      break;
    return $bean;
  }
}