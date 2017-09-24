void printProperties(IList users) 
{
  foreach (User user in users)
  {
    Console.WriteLine(getProperties(user));

    // ...
  }
}

string getProperties(User user)  
{
  return user.getName() + " " + user.getAge();
}