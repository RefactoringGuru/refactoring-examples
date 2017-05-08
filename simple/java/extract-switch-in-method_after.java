void properties(String param) {
  bean = setSetting(param);

  // set other properties...
}

public Bean setSetting(String param) {
  Bean bean;
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
    return bean;
  }
}