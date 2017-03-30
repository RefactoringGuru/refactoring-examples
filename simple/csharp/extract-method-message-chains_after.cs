void printOwing() 
{
  if (validateUser(user)) 
  {
    ligIn(user);
  }
}

boolean validateUser(User user) 
{
  middleware = new ThrottlingMiddleware(user)
    .linkWith(new UserExistsMiddleware(user))
    .linkWith(new RoleCheckMiddleware(user));
  return midleware.check();
}
