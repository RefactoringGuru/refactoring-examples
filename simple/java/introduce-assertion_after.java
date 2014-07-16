double getExpenseLimit() {
  Assert.isTrue (_expenseLimit != NULL_EXPENSE || _primaryProject != null);

  return (_expenseLimit != NULL_EXPENSE) ?
    _expenseLimit:
    _primaryProject.getMemberExpenseLimit();
}