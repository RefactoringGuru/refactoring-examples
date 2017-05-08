void properties(string param) 
{
  bean = setSetting(param);
}

public void setSetting(string param) 
{
  Bean bean;
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
    return bean;
  }
}
