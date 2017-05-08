void properties(string param) 
{
  switch (param) 
  {
    case "DAO" :
    bean = new DAO();
    break;
  case "DAOStub":
    bean = new DAOStub();
    break;
  case "AppConfig":
    bean = new AppConfig();
    break;
  }

  // set other properties...
}
