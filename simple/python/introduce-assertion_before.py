def getExpenseLimit(self):
    # should have either expense limit or a primary project
    return self.expenseLimit if self.expenseLimit != NULL_EXPENSE else \
        self.primaryProject.getMemberExpenseLimit()