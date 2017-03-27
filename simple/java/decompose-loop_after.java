void printProperties(List users) {
  for (User user : users) {
      System.out.println(getProperties(user));

      // ...
    }
}

String getProperties(User user)  {
  return user.getName() + " " + user.getAge();
}
