For this example, we take an object that controls resources which are expensive to create but reusable. A good example of this situation is database connections.

The administrator has two pools. One of them contains resources available for use…

…and the other pool contains already allocated resources.

When a client needs a resource, the administrator provides it from the pool of available resources and moves it to the allocated pool. When the client frees up the resource, the administrator returns it back.

If a client requests a resource and no free resources remain, the administrator creates a new resource.

Insufficient resources are not an unexpected event, so using an exception is not truly justified.

So we can get rid of the exception. First, in the beginning of the method, create a conditional whose condition coincides with the condition for throwing an exception. Place all the remaining code in <code>else</code>.

Then copy the code from the <code>catch</code> section to inside the borderline conditional.

The code so obtained should not reach the <code>catch</code> section. But to be 100% sure, insert a check inside the section and run all the tests.

Let's run the autotests.

Now we can remove the try/catch section without worrying about possible errors.

After this, it is usually possible to tidy up the conditional code. In this case, we can apply <a href="/consolidate-duplicate-conditional-fragments">Consolidate Duplicate Conditional Fragments</a>.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.