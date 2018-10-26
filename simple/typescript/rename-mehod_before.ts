class Customer {
  private _name = 'name';
  private _lastName = 'lastname';

  /**
   * Method returns customer's lastname.
   */
  get lnm(): string {
    return this._lastName;
  }

  // other methods ...
}