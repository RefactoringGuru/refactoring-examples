A method exists for getting the phone number of a certain person. The method is not redefined anywhere so we do not need to track changes in subclasses.

We decided to rename the method to <code>getOfficeTelephoneNumber</code>, a more descriptive name.

Start by creating a new method and copying the body to the new method.

The old method changes and now calls the new method.

We find the places where the old method is called, modifying them to call the new method instead.

After all changes have been made, go ahead and delete the old method.

Let's start the final testing.

Now refactoring is complete. If you like, you can compare the old and new code.