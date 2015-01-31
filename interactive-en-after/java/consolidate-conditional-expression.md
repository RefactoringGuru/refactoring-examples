Let's look at <i>Consolidate Conditional Expression</i>, using as our example the method for calculating workman's compensation for an injured worker.

As you see, there are a number of conditions that return an identical result.

These checks can be merged into a single expression using the <code>or</code> operator.

This condition looks too long and hard to comprehend at first glance. So we can <a href="/extract-method">Extract Method</a> and say what the conditional is looking for (no compensation to be paid).

Let's run the compiler and auto-tests.

The previous example demonstrated the <code>or</code> operation but the same thing can be done using <code>and</code>.

These conditions can be replaced as follows:

If the procedure you are reviewing only checks a condition and returns a value, we can simplify the code to a greater degree by using a ternary operator.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code, if you like.