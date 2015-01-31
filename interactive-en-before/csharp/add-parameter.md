We have a Calendar class that stores records about planned meetings.

A method in this class returns the values of meetings for a particular date.

We would like for this method to be able to filter visitors by name as well.

We could simply add a new parameter to the method description, but that would cause a large risk of breaking calls involving this method in other code fragments.

So we will proceed very carefully, creating a new method with the desired parameter. To start, we will copy the body of the existing method.

Then we change the method body as needed for the new method.

Now the body of the old method can be replaced with a call to the new method.

Then find all references to the old method and replace them with references to the new one.

Here is one of them. Since we have nothing to “give” to the new parameter, we write in the value <code>null</code>.

After all changes have been made, go ahead and delete the old method.

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.