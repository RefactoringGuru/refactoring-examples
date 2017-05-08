void check() {
  logIn(user);

  //check user
  new UserExistsMiddleware(user).isExist();
  new ThrottlingMiddleware(user).count();
  new RoleCheckMiddleware(uer).isAdmin();
}
