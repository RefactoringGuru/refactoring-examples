Let's look at <i>Duplicate Observed Data</i> by looking at a class that creates a window for editing numeric intervals.

The window consists of three parts: Start value, End value, and Length. <br/><img src="/images/refactoring/gui-window_csharp.png">

Recalculations of new values occur when the element loses focus. When a change occurs in <code>Start</code> or <code>End</code>, <code>Length</code> is calculated. When <code>Length</code> changes, <code>End</code> is calculated.

The specific calculations take place in utility methods.

These methods call calculation of the new length (<code>CalculateLength</code>) or new end value (<code>CalculateEnd</code>) depending on what has changed in the window.

Our task is to separate all recalculations of length and end value into a separate domain class. Start by creating such a class.

After a domain class is created, place a reference to it from the window class.

Now we need to implement the Observer pattern. So we'll use the <code>IObservable&lt;T&gt;</code> and <code>IObserver&lt;T&gt;</code> interfaces.

In our case, the <code>Interval</code> class will be implemented by the <code>Observable</code> interface and the data for observation will be stored in it as well. Start with class inheritance from the necessary interface.

Then add the class responsible for storing observers to this class.

Initialize it in the class constructor.

as well as the  <code>IObservable&lt;T&gt;.Subscribe()</code> method, which allows observers to subscribe to notifications.

Note that the method should return an implementation of <code>IDisposable</code> allowing the observer to unsubscribe from notifications at any time. In this example such functionality is not needed, so we simply return <code>null</code>.

Now implement the <code>Observer</code> interface. For this, we will inherit the window class from the <code>IObserver&lt;T&gt;</code> interface.

We'll implement the <code>IObserver&lt;T&gt;.OnNext()</code>, <code>IObserver&lt;T&gt;.OnError()</code> and <code>IObserver&lt;T&gt;.OnCompleted()</code> methods,

The first method is responsible for handling incoming notifications. We will "flesh it out" later.

The remaining two are responsible for error handling and ending notification-related work. They are not used in our example so we will not be filling them in, as noted in the comments.

At this point, create code to initialize a field containing an instance of the <code>Interval</code> domain class as well as to subscribe the window class to notifications. Place all this code in the <code>IntervalWindow</code> constructor.

Here, the call of the <code>OnNext</code> method guarantees that the window object (GUI) will be filled with data from the domain object.

Compile and test. While we have not yet made any "real" changes, mistakes can be often made in the simplest things and it is best to keep all code checked at all times.

Now it's time for the text fields. We will <a href="/self-encapsulate-field">self-encapsulate them</a>. For this, we will create string properties for the content of each field. Start with the interval start field.

Do the same thing for the two remaining fields.

Then we can replace all direct references to field content with references to the relevant properties.

Note that in our case, unlike ordinary self-encapsulation, the user can independently change the values of text fields in the window. Therefore we must take care that these changes are saved. So when the field loses focus, we will force a call to the setter for the relevant property, passing the contents of the text field to it.

During the last step, we change these setters so that they change values in the instance of the domain class, which in turn causes recalculation of the intervals and sending of a notification with the new values to our window. The result is that our text fields receive up-to-date (calculated) values. So let's make the necessary changes.

Excellent! Once the <code>End</code> field is fully encapsulated, we can add the relevant field to the domain class.

Initialize the field with the same value as the field in the GUI.

Create public properties for accessing fields.

The setters of these properties must change the values of the relevant fields but also notify observers about the changes made, if the value set differs from the previous one. Implement this logic in a separate method in order to avoid code duplication.

Then add a call to this method in our setters.

After the duplicate properties are added to the domain class, change the setters and getters for the encapslating properties of the observer class so that they delegate access to properties of an instance of the domain class.

As you see, when a property value of domain object is changed, we notify the window observer that it is time to update its text fields...

...which leads us to calling the <code>OnNext()</code> method in the window class. It does not have anything in it yet, so let's add the necessary code to make everything work.

This method requires direct access to text fields, since use of setters that encapsulate properties would lead to infinite recursion.

After finishing all field rearrangements, compile and test.

To finish refactoring, move the <code>CalculateLength()</code> and <code>CalculateEnd()</code> methods to a domain class.

Let's move the method which calculates length.

Change the call code to match.

We perform the equivalent actions on the method for calculating the final value.

Ultimately, we get the domain class, which contains all behaviors and calculations on the source data separate from the GUI code.

Let's run the final compile and test.

Now refactoring is complete. If you like, you can compare the old and new code.