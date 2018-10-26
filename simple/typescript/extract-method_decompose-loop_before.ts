printProperties(users: User[]): void {
  for (let user of users) {
    let result = "";
    result += user.getName();
    result += " ";
    result += user.getAge();
    console.log(result);

    // ...
  }
}