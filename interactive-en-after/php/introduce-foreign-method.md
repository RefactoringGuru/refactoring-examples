Let's look at <i>Introduce Foreign Method</i> using the example of a bank account class.

This class has code that opens a new billing period one week in the future from the current time.

Ideally, the <code>DateTime</code> class would have a method for getting a date seven days in the future (something resembling <code>previousDate->nextWeek()</code>) but it does not, and what's more, it is standard so we cannot change it.

What we can do though is create a "foreign" method in its own class.

To make the method more universal, add a parameter of the <code>Date</code> class to it. We are essentially broadening the functionality of the object mentioned in this parameter.

You should also declare the method static to make it accessible from other code not associated with <code>Account</code>.

The method can now be used in the remaining code.

For the finishing touch, add a comment to the "external method" about what it is. This will avoid potential confusion regarding its use. And if a new class is created in the program later for storing additional date-related functions, this method can be easily found and moved to a better place.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code if you like.