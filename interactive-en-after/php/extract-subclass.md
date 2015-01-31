Start with the class of works performed, which determines the price of operations performed in the local garage.

The price of work can consist of either a fixed amount (such as payments for ordering certain parts) or payment for a mechanic's time, the price of which can be taken directly from the <code>Employee</code> class.

The price is calculated in various ways within a single class, which is a bit offputting.

It would be great to isolate a <code>LaborItem</code> subclass from the class and move to that subclass all behaviors that are associated with manual work. Then we could leave only fixed amounts in the original class.

Create a new class.

Now start to push down the subclass-only methods.

Above all, we need a constructor for this class since <code>JobItem</code> does not have the constructor we need, one which would accept only the employee object and number of hours spent.

For now, we will copy the signature of the parent constructor.

This is enough to make the new subclass stop displaying errors. However, this constructor is unwieldy: some arguments are necessary for <code>LaborItem</code> and others are not. We will fix this a little later.

During the next step, search for references to the <code>JobItem</code> constructor and cases when the <code>LaborItem</code> constructor should be called instead.

At this point we will not touch the variable type, changing only the type of the constructor.

This is because the new type should be used only where necessary. We do not yet have a specific interface for the subclass so it is better to not declare any varieties.

This is the perfect time to perform housekeeping on the lists of constructor parameters. Apply <a href="/remove-parameter">Remove Parameter</a> to each of them.

First refer to the parent class. Create a new constructor and declare the previous one protected (the subclass still needs it).

External constructor calls should now use the new constructor.

Let's run testing to make sure there are no errors.

Apply <a href="/remove-parameter">Remove Parameter</a> to the subclass constructor to get rid of unnecessary parameters.

Subsequently we can push down parts of <code>JobItem</code> to the subclass. First look at the methods.

Start with applying <a href="/push-down-method">Push Down Method</a> to <code>getEmployee</code>. 

Since the <code>employee</code> field will later be pushed down into a subclass, for now we will declare it protected.

Once the <code>employee</code> field is protected, we can clean up the constructors so that <code>employee</code> is initialized only in the subclass to which it is being pushed down.

The <code>isLabor</code> field is used to indicate information now implied by the hierarchy, so the field can be removed<br/><br/>The best way to do so is to first use <a href="/self-encapsulate-field">Self-Encapsulate Field</a> and then, after changing the access method, use a polymorphic constant method (a method via which each implementation returns its own fixed value).

So we declare <code>isLabor</code> getters in both classes.

Now replace use of the field with calls to the getters.

Remove the <code>isLabor</code> field.

Now look at the users of the <code>isLabor</code> methods. They should be refactored using <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.

It then becomes clear that the <code>isLabor</code> methods are not used anywhere now and can be removed from all classes.

After the group of methods that use particular data is moved to a subclass, you can apply <a href="/push-down-field">Push Down Field</a> to this data. In some cases this is impossible because the data is used by a method. This state of affairs means that you should continue working with the methods, by using <a href="/push-down-method">Push Down Method</a> or <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.

In our case, everything is ready for us to move the <code>employee</code> field to <code>LaborItem</code>.

Let's run testing to make sure there are no errors.

So extraction of <code>LaborItem</code> is complete. But one more thing remains. Since the price of spare parts (<code>unitPrice</code>) is used only in the <code>JobItem</code> class and is not needed in <code>LaborItem</code>, we can go one step further and apply <a href="/extract-subclass">Extract Subclass</a> to <code>JobItem</code> again and create a class that represents spare parts.

The <code>JobItem</code> class is abstract as a result.

Let's start the final testing.

Now refactoring is complete. If you like, you can compare the old and new code.