class NullConnection extends Connection {
  function isNull() {
    return true;
  }

  function getStandartConnection() {
    return new HighSpeedConnection();
  }
  // Some other NULL functionality.
}

// Replace null values with Null-object.
$connection = ($session.connection != null) ? 
  $session->connection : 
  new NullConnection();
