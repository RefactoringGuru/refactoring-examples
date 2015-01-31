Let's look at <i>Replace Array with Object</i>, using as our example a class that stores the name of an athletic team, number of wins, and number of losses.

Converting an array to an object starts with creating a class.

We start by adding the same array field to the new class as in the original. Don't worry, this is a temporary measure that we will dispense with later.

Now find places with references to the array and replace them with references to your new class.

Create the object itself in the place where the array had been initialized.

Now replace the code that uses the array.

Add getters and setters with more self-explanatory names to the data class. Start with the field containing the team name.

One by one, replace the code for assignment to array element values. The methods of the <code>Performance</code> class will now be used everywhere instead.

Do the same with the second element.

Having done so for all elements, we can declare the array private.

The main part of this refactoring technique – replacing the interface – is now complete. But it will also be useful to replace the array inside the data class.

To do this, add fields for all array elements and reorient the access methods to use them. First convert the team name field.

Then convert the field that stores the team win/loss history.

After finishing replacement for all the elements of the array, you can remove the array declaration from the class.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.