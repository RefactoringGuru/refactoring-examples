Imagine a payroll system with special rules for employees who have passed away, live on their own, or have retired. These cases are unusual but do occur.

This code for checking special conditions…

…is concealing performance of ordinary actions. So using borderline operators will make the code more obvious.

Continue performing replacements, one at a time.

And the last one.

After these changes, you can get rid of the <code>result</code> variable entirely.

Inline conditionals are often written by programmers taught that a method should contain only one exit point. But in reality, this rule is simplistic and obsolete.

If a method is no longer of interest at runtime, it is best to exit it as soon as possible. Forcing the reader to go over an empty <code>else</code> block only throws up roadblocks to understanding your intentions.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.