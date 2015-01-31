Say that we have a class for creating employees…

The client code calls the constructor directly.

So what if we wanted to create subclasses for each type of employee –

what would happen with the code then? We would have to rewrite it, since we cannot return anything from the <code>Employee</code> constructor other than <code>Employee</code> objects (and we need <code>Engineer</code>).

But if something changes again later, we will have to create even more subclasses and may well have to adjust the constructor calls… again.

The alternative is to create a <b>factory method</b> – a special static method that returns objects of different classes depending on particular parameters.

The <code>Employee</code> class is the best place to store the factory method, since it will probably survive any changes in the subclasses.

At this stage, the factory method will call the current constructor but we will soon change this.

Now find all direct calls to the constructors and replace them with calls to the factory method.

Once the changes are complete, you can hide the constructor from outside eyes by making it private.

In addition, you can create a conditional in the factory method to return an object of the necessary class depending on the parameter passed.

Let's start the final testing.

Now refactoring is complete. If you like, you can compare the old and new code.