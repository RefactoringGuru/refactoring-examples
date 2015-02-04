There is a method for getting the phone number of a certain person. The method is not overridden anywhere so we do not need to track changes in subclasses.

Let's change it's name to <code>getOfficeTelephoneNumber</code>, a more descriptive name.

Start by creating a new method and copying the body to the new method.

Then we change the old method so that it call the new one. That might look to you as a useless step, but it will help to keep the code working while you search for all calls of the old method and replace them with the new method calls.

So, we find the places where the old method is called, modifying them to call the new method instead.

After all changes have been made, we can go ahead and delete the old method.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.