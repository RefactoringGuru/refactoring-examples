Let's take a look at <i>Extract Method</i> using this function as an example.

We begin with a very, very simple case. The code for displaying a banner can be easily extracted via copy and paste.

After that, we replace the code in the original method with a call to the new method.

At last, we should compile the code to check for possible errors.

Now things get trickier. The problem with extracting complex methods is buried in local variables.

Let's try to extract the method for displaying the details.

Let's launch the compiler.

Ah... Yes, we really did move the <code>outstanding</code> variable out of the original method but no value is assigned to it in the new method.

The better solution is to convert that variable to a method parameter and pass its value from the original method.

Let's launch the compiler.

Now on to the extraction of the code for calculating amounts outstanding.

In this case, additional difficulties are caused by local variables to which new values are assigned. It is quite possible that these values are used in the remaining code of the main method.

If a value is assigned to the parameter, you can get rid of this by using <i>Remove Assignments to Parameters</i> refactoring.

Let's check each variable.

Here, the problem is caused by the <code>outstanding</code> variable, which is then used in the <code>PrintDetails()</code> call.

Pass it back to the original method.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.