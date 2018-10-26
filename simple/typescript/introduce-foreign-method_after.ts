class Report {
  //...
  sendReport() {
    let newStart: Date = nextDay(previousEnd);
    //...
  }
  private static nextDay(arg: Date): Date {
    return new Date(arg.getFullYear(), arg.getMonth(), arg.getDate() + 1);
  }
}