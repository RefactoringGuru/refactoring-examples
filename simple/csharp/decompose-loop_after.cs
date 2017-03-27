void printProperties(IList users) 
{
  for (User user : users) 
  {
    Console.WriteLine(getProperties(user));

    // ...
  }
}

string getProperties(User user)  
{
  return user.getName() + " " + user.getAge();
} 
