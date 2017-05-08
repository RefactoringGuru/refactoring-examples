function check() {
  $this->logIn($user);

  //check user
  (new UserExistsMiddleware($user))->isExist();
  (new UserExistsMiddleware($user))->count();
  (new UserExistsMiddleware($user))->isAdmin();
}
