The following function checks whether the list of people contains anybody suspicious; these suspicious names (Don and John) are hard-coded.

In this function, the variable <code>found</code> is a control flag. It is initialized by one value…

…which changes as the function is run…

…after which the code does not do anything more until the cycle is complete.

This refactoring technique starts with us looking for assignments by the control variable that affect the execution flow of the program. In this case, this is assignments of the <code>true</code> value.

Under the logic of this method, the code should not do anything more within the cycle after these assignments. Therefore we can simply replace them with a <code>break</code> operator, saving several moot iterations in the process.

Then we can remove all mentions of the control flag.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.