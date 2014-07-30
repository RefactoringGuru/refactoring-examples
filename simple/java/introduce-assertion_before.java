double getExpenseLimit() {
  // should have either expense limit or a primary project
  return (expenseLimit != NULL_EXPENSE) ?
    expenseLimit:
    primaryProject.getMemberExpenseLimit();
}