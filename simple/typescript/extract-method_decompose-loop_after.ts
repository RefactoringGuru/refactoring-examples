printProperties(users: User[]): void {
  for (let user of users) {
    console.log(getProperties(user));

    // ...
  }
}

getProperties(user: User): string  {
  return user.getName() + " " + user.getAge();
}