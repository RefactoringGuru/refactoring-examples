Let's look at <i>Replace Temp with Query</i> using a simple method as an example.

Replace the variables <code>basePrice</code> and <code>discountFactor</code> one by one with calls to the respective methods.

First make sure that the variables are assigned a value within the method only once.

In this example, there is no potential confusion. But to make sure, you can temporarily declare these variables to be constants (using the <code>const</code> keyword). Then the compiler will indicate all places where attempts to assign values are made.

Note that if a variable was not initialized at declaration time, when it turns into a constant you need to perform this initialization (or else a constant declaration error will occur). So assign any temporary value to it.

Let's compile and see whether our variables are assigned other values.

The compiler now draws our attention to two lines where an attempt to change the value of the constant is made. The errors relate only to <code>discountFactor</code>. Remember that we assigned a temporary value to it, meaning that there should be one assignment error in any case. But why does the compiler indicate two places, not one? After careful review, it becomes clear that initialization of the variable takes place in mutually exclusive <code>if-else</code> branches, so a value will be assigned only once. That being true, we can proceed to refactoring.

After we made sure that the variables of interest get their values once and only once, we need to return them to their original state.

Compile and make sure that we have not made any stray changes.

For the second step, we create the <code>BasePrice()</code> method and move the expression that forms the <code>basePrice</code> variable to it.

Now we can use a method call instead of the initial expression. Thus we now have a new method and all of the old code still works.

This is the perfect time to replace the variable with a direct method call.

Replace the first variable and then compile to make sure that nothing is broken.

Let's run the compiler and auto-tests.

Perform the following replacement.

Let's run the compiler and auto-tests.

The previous replacement was the last one, so we can remove the variable declaration.

All is done with the first variable. We can repeat all this to extract <code>discountFactor</code>.

Create a new method…

…use it to initialize the variable…

Note how difficult it would have been to extract <code>discountFactor</code> if we had not first replaced <code>basePrice</code> with a method call.

Ultimately the <code>getPrice()</code> method comes to look as follows.

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.