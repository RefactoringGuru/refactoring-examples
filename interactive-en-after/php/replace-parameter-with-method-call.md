Let's consider this refactoring technique using yet another order price example.

The method for getting the discount (<code>discountedPrice</code>) is currently nearly impossible to use separately from the method for getting the price (<code>getPrice</code>), since prior to it you must get the values of all parameters.

But what if we eliminate all parameters in <code>discountedPrice</code>? Let's try.

To start, extract <code>discountLevel</code> to its own method.

Now we can use this method instead of this parameter in the discount calculation method.

One of the parameters is no longer needed so we can use <a href="/remove-parameter">Remove Parameter</a>

We can then remove mentions of the code of the temporary variable, which is no longer used.

Let's start testing.

One parameter, one more to go…

Let's extract the base price calculation to its own method.

Now use this method in <code>discountedPrice</code>.

As before, we can now get rid of this parameter as well.

Then clean up the code of the original method…

…or if we make it a bit more pretty:

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code if you like.