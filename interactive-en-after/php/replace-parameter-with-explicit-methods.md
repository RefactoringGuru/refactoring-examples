Let's look at this technique using an order class as an example.

This class has a method for applying discounts that handles both fixed discounts and percentage-based ones.

Start refactoring by extracting each version to a separate method.

Now find all places where the original method is called, replacing them with calls to our new methods.

Let's launch autotests to check for errors in code.

Once changes are complete, remove the original method and the now-superfluous constants.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code if you like.