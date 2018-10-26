printOwing(): void {
  printBanner();

  //print details
  console.log("name: " + name);
  console.log("amount: " + getOutstanding());
}