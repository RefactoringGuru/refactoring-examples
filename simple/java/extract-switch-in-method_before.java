void properties(String param) {
  switch (param) {
    case "DAO" :
    bean = new DAO();
    break;
  case "DAOStub":
    bean = new DAOStub();
    break;
  case "AppConfig":
    bean = new AppConfig();
    break;
  case "ServiceX":
    bean = new ServiceX();
    break;
  }

  // set other properties...
}
