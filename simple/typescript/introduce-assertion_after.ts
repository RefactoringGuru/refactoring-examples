getExpenseLimit(): number {

  return (expenseLimit != NULL_EXPENSE) ?
    expenseLimit:
    primaryProject.getMemberExpenseLimit();
}