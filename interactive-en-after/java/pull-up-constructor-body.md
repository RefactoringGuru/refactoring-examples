Start with the manager and employee classes. In this case, <code>Employee</code> does not have any constructor and its fields are filled in the <code>Manager</code> class, which actually is used in the program.

So if we want to create another <code>Employee</code> subclass, we must duplicate parts of the constructor in order to initialize the <code>Employee</code> fields.

Instead, we can pull up part of the body of the <code>Manager</code> constructor to its superclass.

Define the constructor and make it protected. This lets other programmers know that it needs to be called in subclasses.

After the new constructor becomes available, it can be called from the <code>Manager</code> constructor.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.