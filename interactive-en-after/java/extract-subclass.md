We start with the <code>JobItem</code> class, which tracks the time and materials used to fix a client's car in a local garage. This class is also responsible for calculating the price client should pay.

The price usually consists of several items. First, it's the fixed cost of certain parts. Second, it's the cost of a mechanic's time, multiplied by his rate (that can be taken directly from the <code>Employee</code> class).

So, the price is calculated in several ways, all of which sit in a single class. And that starts to smell as a <i>Large Class</i>.

As a solution, we could extract the <code>LaborItem</code> subclass and move all behaviors, which are associated with manual work, to that subclass. Then we could leave only fixed amounts in the original class.

Awesome, let's create a new class.

Now we should start to push down the labor-related methods.

Above all, we need a constructor because <code>JobItem</code> does not have the constructor we need, one that would accept only the employee object and number of hours spent.

For now, we will copy the signature of the parent constructor.

That is enough to make the new subclass stop displaying errors. However, this constructor is unwieldy: some arguments are necessary for <code>LaborItem</code> and others are not. We will fix this a little later.

During the next step, we need to search for references to the <code>JobItem</code> constructor and cases when the <code>LaborItem</code> constructor should be called instead.

At this point we will not touch the variable type, changing only the type of the constructor.

That is because the new type should be used only where necessary. We do not yet have a specific interface for the subclass, so it is better to not declare any varieties.

That is the perfect time to perform housekeeping on the lists of constructor parameters. Let's apply <a href="/remove-parameter">Remove Parameter</a> to each of them.

First, we need to refer to the parent class. We create a new constructor and declare the previous one protected (the subclass still needs it).

External constructor calls should now use the new constructor.

Let's compile and test, just in case any errors appeared.

Now, we apply <a href="/remove-parameter">Remove Parameter</a> to the subclass constructor to get rid of unnecessary parameters.

Subsequently we can push down parts of <code>JobItem</code> to the subclass. First look at the methods.

Start with applying <a href="/push-down-method">Push Down Method</a> to <code>getEmployee</code>. 

Since the <code>employee</code> field will be pushed down to the subclass later, for now we will declare it protected.

Once the <code>employee</code> field is protected, we can clean up the constructors so that <code>employee</code> is initialized only in the subclass.

The <code>isLabor</code> field is used to indicate information now implied by the hierarchy, so the field can be removed<br/><br/>The best way to do so is to first use <a href="/self-encapsulate-field">Self-Encapsulate Field</a> and then override the getter in subclasses so that it return own fixed value (such methods usually called "polymorphic constant method").

So we declare <code>isLabor</code> getters in both classes.

Now replace use of the field with calls to the getters.

Remove the <code>isLabor</code> field.

After the changes, the constructors in <code>JobItem</code> are identical and, for this reason, could be put together.

Now look at the uses of the <code>isLabor</code> methods. They should be refactored using <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.

Then it becomes clear that <code>isLabor</code> methods are not used anywhere and can be safely removed from all classes.

After pushing methods down to a subclass, you can consider moving some of the fields as well. We can apply <a href="/push-down-field">Push Down Field</a> to these fields. In some cases, this is impossible because the fields are still used in the context of superclass.

In our case, everything is ready for us to move the <code>employee</code> field to <code>LaborItem</code>.

Compile and test, just in case any errors appeared.

So extraction of <code>LaborItem</code> is complete. But one more thing remains. Since the price of spare parts (<code>unitPrice</code>) is used only in the <code>JobItem</code> class and is not needed in <code>LaborItem</code>, we can go one step further and apply <a href="/extract-subclass">Extract Subclass</a> to <code>JobItem</code> again and create a class that represents spare parts.

The <code>JobItem</code> class becomes abstract as a result.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.