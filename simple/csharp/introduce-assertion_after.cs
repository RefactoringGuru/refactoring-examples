double GetExpenseLimit() 
{
  Assert.IsTrue(expenseLimit != NULL_EXPENSE && primaryProject != null);

  return expenseLimit != NULL_EXPENSE ?
    expenseLimit:
    primaryProject.GetMemberExpenseLimit();
}