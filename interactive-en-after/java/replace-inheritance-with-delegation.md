Let's try one more refactoring method, using as our example a <code>Car</code> class that is inherited from the <code>Engine</code> class.

At first inheritance seemed a good and noble idea… but later we found that cars use only one engine property (volume, to be precise).

So it would have been more efficient to delegate to the <code>Engine</code> class for getting the necessary properties or methods.

Start refactoring by creating a field for storing a reference to the superclass.

For now we will fill this field with the current object (this can be done in the constructor).

Then we should change all references to superclass fields and methods so that they refer to the newly created field. In our case, this happens in only one place. 

We now remove the inheritance declaration from the <code>Car</code> class.

All that's left to do is create a new engine object for filling the field of the associated object.

Let's start the final testing.

The refactoring is complete! You can compare the old and new code if you like.