We have a <code>TimeSheet</code> class that is used for payroll.

For this, the class must know an employee's rate of pay and any special skills:

The employee has many other characteristics in addition to pay rate and special skills, but only the latter two are needed in this application. The fact that only this subset is needed can be emphasized by defining an interface for it:

Then declare Employee as implementing this interface:

When this has been done, you can change the declaration of the <code>charge</code> method to show that only part of the Employee behavior is used:

Let's perform the final compilation and testing.

In this case, a hidden benefit appears, in the form of in-code documentation. This benefit is not worth the work if talking about just one method, but if several classes start to use the <code>Billable</code> interface, this can be rather valuable.

A major payoff comes when we want to invoice for computers as well. All we need to do is implement the <code>Billable</code> interface in them, after which we can include computers in the timesheet.

The refactoring is complete! You can compare the old and new code, if you like.