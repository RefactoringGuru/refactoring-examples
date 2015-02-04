Let's try out one more refactoring using a <code>Car</code> class that is inherited from the <code>Engine</code> as our example.

At first, inheritance seemed a good and noble idea… But later we found that cars use only one engine's property (volume, to be precise).

So it would have been more efficient to delegate to the <code>Engine</code> class for getting the necessary properties or methods.

Let's start refactoring by creating a field for storing a reference to an engine object.

For now we will fill this field with the current object (this can be done in the constructor).

Then we should change all access points to the Engine's fields and methods so that they go through the newly created field. In our case, this happens in only one place. 

Now we can remove the inheritance declaration from the <code>Car</code> class.

All that's left to do is create a new engine object for filling the field of the associated object.

Let's start the final testing.

The refactoring is complete! You can compare the old and new code if you like.