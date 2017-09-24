<?php
function getExpenseLimit() {
  // should have either expense limit or a primary project
  return ($this->expenseLimit != NULL_EXPENSE) ?
    $this->expenseLimit:
    $this->primaryProject->getMemberExpenseLimit();
}