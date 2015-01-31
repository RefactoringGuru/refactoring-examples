Let's look at <i>Extract Variable</i> using this simple method as an example.

As you see, the method contains a single enormous expression capable of throwing off both police dogs and the most determined programmers.

Let's split this expression into separate parts, placing each part in a separate variable.

First define the <code>basePrice</code> as the number of products in the order, multiplied by the unit cost…

…and use the new variable in the formula. The expression is repeated several times so we will replace all identical calculations with the variable.

Compile and verify that nothing has gone astray.

Now replace the remaining parts of the complex expression with variables.

Define the <code>quantityDiscount</code> and move calculation to a new variable.

All done. Let's compile and check for errors.

The final part of calculation is <code>shipping</code>. We use a separate variable here as well.

Since the expression is now obvious and intuitive, we can remove the comment.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.