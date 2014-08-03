string FoundPerson(string[] people)
{
  for (int i = 0; i < people.Length; i++) 
  {
    if (people[i].Equals("Don"))
    {
      return "Don";
    }
    else if (people[i].Equals("John"))
    {
      return "John";
    }
    else if (people[i].Equals("Kent"))
    {
      return "Kent";
    }
  }
  return string.Empty;
}