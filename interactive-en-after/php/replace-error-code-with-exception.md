Let's look at refactoring in the case of withdrawals from a bank account.

If the customer attempts to withdraw more money than his or her current balance allows, an error code is generated (<code>-1</code>)…

…which is then checked in the client code.

We can replace all this by throwing an error and then "catching" it in the client code.

First we create a new exception class that will be easier to catch.

Wrap the code for calling the new method in <code>try</code>/<code>catch</code> blocks.

Then change the method so that it throws an exception instead of returning an error code.

This code can be simplified a bit if we remove <code>else</code>.

This step is inconvenient because we are forced to change all references to the method, as well as the method itself, in a single step. Otherwise, the compiler will shake its head at us in disapproval. If there are many calls, we will have to make a mammoth modification without any intermediate compilation or testing.

In these cases it is better to create a new method and place the code of the old one inside it, including exceptions. Replace the code of the old method with <code>try</code>/<code>catch</code> blocks that return error codes. After this the code will remain functional and we can replace error code handlers, one by one, with calls to the new method and <code>try</code>/<code>catch</code> blocks.

In any event, we still must update the method signature, indicating that the method now throws an error.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.