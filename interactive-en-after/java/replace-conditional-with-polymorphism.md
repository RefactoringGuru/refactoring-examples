Let's take a look at this refactoring in the context of code for calculating payroll for different types of employees (see <a href="/replace-type-code-with-state-strategy">Replace Type Code with State/Strategy</a>).

See that big conditional inside the <code>payAmount()</code> method? Let's try to get rid of it.

First, extract the implementation of <code>payAmount</code> to a new method in a class like <code>EmployeeType</code>. This gives us a common access point for this method in the subclasses.

We need data from the <code>Employee</code> object. For this reason we create a parameter in method and pass the <code>Employee</code> object in it.

Now, we can set up delegation from the <code>Employee</code> class.

Then start moving code to subclasses. Create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant employee types.

Now that the methods have been created, we can make the <code>payAmount</code> method in <code>EmployeeType</code>  abstract.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.