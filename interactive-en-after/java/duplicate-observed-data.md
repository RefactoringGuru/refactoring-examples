Let's look at <i>Duplicate Observed Data</i> using the class that creates a window for editing numeric intervals.

The window consists of three parts: Start value, End value, and Length. <br/><img src="/images/refactoring/gui-window.png">

Recalculations of new values occur when the element loses focus. When a change occurs in <code>Start</code> or <code>End</code> text fields, <code>length</code> is calculated. When <code>length</code> changes, <code>End</code> is calculated.

The specific calculations take place in utility methods for each of the fields.

These methods call calculation of the new length (<code>calculateLength</code>) or new end value (<code>calculateEnd</code>) depending on what has changed in the window.

Our task is to separate all recalculations of length and end value into a separate domain class. Let's start by creating such class.

After creation of a domain class, let's place a reference to it from the window class.

Then we create the code for initializing this reference field and make the window class an observer of the domain class. We should place all this code in the <code>IntervalWindow</code> constructor.

Here, the call to the <code>update</code> function guarantees that the window object (GUI) will be filled with data from the domain object. But we need some other things in order for this to work.

First, declare the <code>IntervalWindow</code> class as the one, which implements <code>Observer</code> interface.

Second, create the <code>update()</code> method to provide the actual implementation.

Compile and test. While we have not yet made any "real" changes, mistakes can be often made in the simplest things, and it is best to keep all code checked at all times.

Now let's take care of the fields. Perform <a href="/self-encapsulate-field">Self-Encapsulate Field</a> on each of the fields of the interval window. For each field, create a getter and setter that return and accept a string value. Start with the field of the end of the interval:

Then we can replace all references to <code>endField</code> with calls to the relevant methods.

In our case, unlike ordinary self-encapsulation, the user can independently change the value of the <code>End</code> field in the window. So we make sure that this change is saved if it is made.

Note that in this call we are accessing the field directly. This is because after continuing the refactoring, <code>getEnd()</code> will be getting its value from the domain object, not the field. And in this particular case, we need the value of the field in the window (GUI).

Otherwise, when the user changes the value of the field, this code would always return the old value. That's why we need a direct access.

Excellent! Once the <code>End</code> field is fully encapsulated, we can add the relevant field to the domain class.

We should initialize the field with the same value as the field in the GUI.

Then add methods for accessing the field.

As you see, when a value gets changed, we notify the window observer that it is time to update its value from the domain object…

…which leads us to calling the <code>update()</code> method in the window class. It does not have anything in it yet, so let's add the necessary code to make everything work.

That is another place where direct access is needed, since calling a setter would lead to infinite recursion.

After finishing all the field rearrangements, we'd better compile and test.

Now we do the same thing with the remaining fields, <code>Start</code> and <code>Length</code>

Add the field to the interval class.

Now we deal with the length field.

Add the field to the interval class.

At this point, it would be a good time to move the <code>calculateEnd()</code> and <code>calculateLength()</code> methods to the interval class.

But to do this, you must first configure the setters of the fields of the <code>IntervalWindow</code> class to fill values in the <code>Interval</code> class.

We removed the value assignment in the GUI interface field because the value will still be set when the setter of the interval class is called (remember about implementation of Observer in the <code>update</code> method).

We should do the same with getters.

Now we can start moving <code>calculateEnd()</code> and <code>calculateLength()</code> to the interval class.

Let's start by moving <code>calculateLength</code>.

Ultimately, we get the domain class, which contains all behaviors and calculations on the source data separate from the GUI code.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.