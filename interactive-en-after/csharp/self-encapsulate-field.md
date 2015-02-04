Let's look at <i>Self-Encapsulation</i> using the example of a range class.<br/><br/>Self-encapsulation differs from regular encapsulation by requiring even its own class to access fields through getters and setters.

To start, let's create properties that encapsulate our fields. These properties can be made private if external access to them is not needed.

Our example has several methods that use direct access to fields.

Complete self-encapsulation by replacing all references to fields in these methods with references to the corresponding properties.

As you may have noted, we have not yet replaced the fields in the constructor. This is because sometimes setters may have logic that works differently than simple assignment (which is what the constructor needs).

For this reason, we make sure that our setters have standard assignment logic before performing replacement in the constructor.

The refactoring is now technically complete, but we can tighten up the code a bit by converting our properties to <i>auto-implemented properties</i>.

So we remove getter and setter bodies in the properties…

…and then remove <code>low</code> and <code>high</code> fields that are no longer necessary.