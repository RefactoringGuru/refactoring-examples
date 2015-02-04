Let's look at <i>Replace Array with Object</i>, using a class that stores the name of an athletic team, number of wins and losses as our example.

Converting an array to an object starts with creating a class.

Then, we add the same array field to the new class as in the original. Don't worry, this is a temporary measure that we will take care of later.

Now we should find all code, which works with the array and replace it with calls to your new class.

Create the instance of our data class in the place where the array had been initialized.

Now replace the code that uses the array.

Add getters and setters with more self-explanatory names to the data class. Start with the field containing the team name.

Now we need to replace the code of assignment values to array elements with appropriate setters of the <code>Performance</code> class.

Do the same with the second element.

Having done so for all elements, we can declare the array private.

The main part of this refactoring – replacing the interface – is now complete. But it will also be useful to replace the array inside the data class.

To do this, we add fields for all array elements and reorient the access methods to use them. First convert the team name field.

Then convert the field that stores the team win/loss history.

After finishing replacements for all the elements of the array, we can remove the array declaration from the class.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.