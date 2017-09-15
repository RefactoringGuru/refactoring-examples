function getExpenseLimit() {
  assert($this->expenseLimit != NULL_EXPENSE || isset($this->primaryProject));

  return ($this->expenseLimit != NULL_EXPENSE) ?
    $this->expenseLimit:
    $this->primaryProject->getMemberExpenseLimit();
}