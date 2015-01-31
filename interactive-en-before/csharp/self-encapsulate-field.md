Let's look at <i>Self-Encapsulate<i> using a class of ranges as an example.<br/><br/>Self-encapsulation consists of implementing access to fields through properties, including in the methods of the class itself.

To start, create properties that encapsulate our fields. These properties can be made private if external access to them is not needed.

Our example has several methods that use direct access to fields.

Complete self-encapsulation by replacing all references to fields in these methods with references to the corresponding properties.

As you may have noted, we have not yet replaced the fields in the constructor. This is because sometimes setters may have logic that works differently than simple assignment (which is what the constructor needs).

The refactoring is now technically complete, but we can tighten up the code a bit by converting our properties to <i>auto-implemented properties</i>.

So we remove getter and setter bodies in the properties…

Hurray! Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.