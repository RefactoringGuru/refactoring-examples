The following function checks whether the list of people contains anybody suspicious; these suspicious names (Don and John) are hard-coded.

In this function, the variable <code>found</code> is a control flag. It is initialized by one value…

…which changes as the function is run…

…after which the code does not do anything more until the loop is finished.

This refactoring starts with us looking for any assignments to the control variable that affect the execution flow of the program. In our case, this is assignments of the <code>true</code> value.

According to the logic of this method, we can simply replace assignments to control flags with <code>break</code> operator.

Then we can remove all other mentions of the control flag.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.