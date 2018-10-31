foundPerson(people: string[]): string{
  let candidates = ["Don", "John", "Kent"];
  for (let person of people) {
    if (candidates.contains(person)) {
      return person;
    }
  }
  return "";
}