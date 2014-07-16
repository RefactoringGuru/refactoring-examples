double getExpenseLimit() {
  // should have either expense limit or a primary project
  return (_expenseLimit != NULL_EXPENSE) ?
    _expenseLimit:
    _primaryProject.getMemberExpenseLimit();
}