Imagine a payroll system with special rules for employees who have passed away, live apart, or have retired. These cases are unusual but do occur.

Once some of the special conditions are triggered...

...a corresponding method is called. Then it's is taken to the end of the method and returned as is. Such could be pretty difficult to understand, especially if there are lot of conditional branches. To fix it, we could place guard clauses, e.g. return the value right away if some condition is met.

Continue performing replacements, one at a time.

And the last one.

After these changes, you can get rid of the <code>result</code> variable entirely.

Multi-level sub-conditionals are often written by programmers taught that a method should contain only one exit point. But in modern programming, this rule have become obsolete.

If, during execution, method did everything, it could, it's better to exit as soon as possible. Otherwise, going over an empty <code>else</code> block only throws up roadblocks to performance and readability.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.