void printOwing() {
  if ($this->validateUser($user)) {
    ligIn($user);
  }
}

boolean validateUser($user) {
  $this->middleware = (new ThrottlingMiddleware($user))
    ->linkWith(new UserExistsMiddleware($user))
    ->linkWith(new RoleCheckMiddleware($user));
  return $this->midleware->check();
}
