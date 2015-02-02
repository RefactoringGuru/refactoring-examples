Let's look at <i>Consolidate Conditional Expression</i>, using the method for calculating workman's injury compensation.

As you see, there are a number of conditions that return an identical result.

We can merge these checks into a single expression using the <code>OR</code> operator.

This condition looks too long and hard to comprehend. So we can <a href="/extract-method">Extract Method</a> and make more clear what the conditional is looking for (no compensation to be paid).

Let's run the compiler and auto-tests.

The previous example demonstrated the <code>OR</code> operation but the same thing can be done using <code>AND</code>.

These conditions can be replaced as follows:

If the code only checks a condition and returns a value, we can simplify it to a greater degree by using a ternary operator.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.