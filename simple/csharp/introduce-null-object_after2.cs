class NullConnection extends Connection 
{
  boolean isNull() 
  {
    return true;
  }

  Connection getStandartConnection() 
  {
    return new HighSpeedConnection();
  }
  // Some other NULL functionality.
}

// Replace null values with Null-object.
connection = (session.connection != null) ? 
  session.connection : new NullConnection();
