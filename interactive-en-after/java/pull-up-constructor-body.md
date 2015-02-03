Let's look at <i>Pull Up Constructor Body</i> using manager and employee classes. In this case, <code>Employee</code> does not have any constructor and its fields are filled in the <code>Manager</code> class, which is actually used in the program.

So if we want to create another <code>Employee</code> subclass, we must duplicate parts of the constructor in order to initialize the <code>Employee</code> fields.

Instead, we can pull up part of the body of the <code>Manager</code> constructor to its superclass.

Let's define the constructor and make it protected. That will work as default implementation and let subclasses call it inside their own constructors.

At this point, the new constructor can be called inside <code>Manager</code> constructor as <code>super</code>.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.