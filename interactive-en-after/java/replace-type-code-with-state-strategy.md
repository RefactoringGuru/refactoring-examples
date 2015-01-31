Let's look at <i>Replace Type Code with State/Strategy</i> in the context of the employee salary class considered earlier. We have several types of employees; these types are used to calculate the salary amount for each particular employee.

Start by applying <a href="/self-encapsulate-field">Self-Encapsulate Field</a> to the employee type.

We assume that the company is progressive and enlightened and so allows its managers to ascend to engineers. So the type code can be changed and using subclasses to eliminate type coding is not possible. This causes us to use the <a href="http://sourcemaking.com/design_patterns/state">State</a> pattern.

Declare the state class (as an abstract class with an abstract method for returning type code).

Now create subclasses for each type of employee.

Create a static method in the state class. It will return an instance of the necessary subclass, depending on the value accepted.

As you can see, here we are introducing a large <code>switch</code> operator. This is not great news, but once we are done with refactoring, this operator will be the only one in the code and will be run only when a type is changed.

Let's compile and test to check for errors in code.

Now we need to connect the created subclasses to <code>Employee</code> by modifying the access methods for the type code and constructor.

The setter body and constructor are replaced with a call to the factory method.

Since access methods now return a code, not the type object itself, we should rename them to make things clear to future readers.

We complete this step by moving all type code constants from <code>Employee</code> to <code>EmployeeType</code>.

Everything is now ready for applying <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.

First extract the implementation of <code>payAmount</code> to a new method in a type class.

We need datа from the <code>Employee</code> object, so in the method we create the parameter to which the main <code>Employee</code> object will be passed.

After these actions, we can set up delegation from the <code>Employee</code> class.

Then start moving code to subclasses. Create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant employee types.

Now that the methods have been created, you can make abstract the <code>payAmount</code> method in <code>EmployeeType</code>.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.