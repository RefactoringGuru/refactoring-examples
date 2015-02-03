Start refactoring by searching for repeating code.

In our case, this is the code for increasing salaries, which differs only by the increase coefficient.

Let's start by creating a new method with the parameter. Later on, we will send the salary increase coefficient there.

Replace the repeating code with calls to our method with the correct parameter.

Now, let's get rid of "lazy" methods that only delegate to the method with parameter.

First, find all their calls and replace them with calls to our new method with parameter.

After the changes are complete, you can remove the methods themselves.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.