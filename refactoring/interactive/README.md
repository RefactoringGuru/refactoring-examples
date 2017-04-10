# Interactive example scenario syntax

Scenario consists of 4 sections, separated with ###

1. Scenario ID and language, separated by a colon.
2. List of steps (one step per line).
3. Starting code (surrounded with ```).
4. Resulting code (surrounded with ```).
5. List of actions.

#### Example:

<pre><code>
extract-method:java

###

1. Some step.

2. Another step.

###

```
class Example {

}
```

###

```
class Example {
  public int field;
}
```

###

Set step 1

# Here's the first popover
</code></pre>

## Available actions

### Popovers (tooltips)

```
# Some text
```

Popovers attached to the selected text or cursor by default. If there are multiple things selected, it will attach to the first selected chunk text.

There are several options you can insert right after dash character (#) to adjust popover position, delay and others:

1. **Direction.** Use arrow character after or V,^,<,> to point the popover at desired direction (default is down ↓).
2. **Delay.** By default, popover waits for user to click on it, but you can proceed execution after specific amount of time by passing number of milliseconds as a desired delay.
3. **Remain visible after proceeding.** Popover can remain visible after proceeding to new action. To achieve this, type plus character (+). Note that you will need to hide it by using equals (=) character in one of the following popovers.
4. **Attachment to steps.** Mostly useful for final popover, which you want to attach to the step lists. To achieve this, put the "Q" letter in options.

#### Examples:

```
# Simple popover

#<2000+ Popover to the right of selected text, which will fire next action in 2 seconds, but will remain visible.

#= Close all visible popovers and show new one.

#Q Show final popover, attached to steps.
```

### Select

Selecting text should be used if you want to point user's attention to something or attach popover to some text. It's also used to replace something (along with following Print command).

There are number of ways to use select action. Here's some examples:

1. Simple select action
````
Select "private int field;"
````

2. If your action selects several chunks of text, but you need only specific one, you can pass the item number to select just that item. This should also works with all following select methods.
````
Select 3rd "getSomething()"
````


3. Sometimes you need to point origin of selection (like class or method), since select might be too broad. Origin may be just a name (this way it script will try to guess the location) or more broad keyword (see below). This should also works with all following select methods. Two origins may be passed if needed.

```
Select "private int field;" in "ExampleClass"
Select "private int field;" in "class ExampleClass"
Select "doSomething()" in "int someMethod"
Select "doSomething()" in "someMethod" of "ExampleClass"
```

It is also possible to target certain parts of method or class, for example;

```
Select "interval" in parameters of "doSomething"
Select "interval" in body of "doSomething" in "ExampleClass"
Select "interval" in whole "doSomething"
Select name of "doSomething"
Select visibility of "doSomething" in "ExampleClass"
Select type of "doSomething"
Select parameters of "doSomething"
Select body of "doSomething"
Select whole of "doSomething"
```

4. To select multiline text, use this syntax. Remember, you can still pass item number or `` in "Something"`` to target specific selection.

<pre><code>
Select:
```
Multiple lines
go here
```
</code></pre>

5. Most powerful way to select things is using sub-selection. The following command will try to find specific text and then select only fraction of it (surrounded with triple colon character). You can even pass several sub-selection regions at once.

<pre><code>
Select:
```
Multiple |||lines|||
go here. I also wan to select |||this|||.
```
</code></pre>

6. Each select command will deselect previously selected text. However, you can add new selection by putting plus sign before select command like this:
````
Select "something"
+ Select "soemthing else"
````

### Go to

This command is used to move cursor to specific location. Mostly useful to target the Print action. It can also serve as target for popover (however, selecting text is more preferable way to do this). Note that using this command will deselect selected text.

There are multiple ways to use this action.

1. Simple goto action. Scripts searches for the text, then puts cursor at the position of ``|||``.
```
Go to "private |||int field;"
```

2. There's also multiline variant of the same:
<pre><code>
Go to:
```
class Example {
  private |||int field;"
}
```
</code></pre>

3. There's a lot of named targets for go to. Here's all available options:
```
Go to the end of file
Go to start of "someMethod"
Go to end of "someMethod"
Go to before "someMethod" in "ExampleClass"
Go to after "someMethod"
Go to parameters of "someMethod"
Go to the end of parameters of "someMethod" in "ExampleClass"
```
- Note that you can use method names ("someMethod"), class names ("Example"), or two-word identifiers in case neither of this gives correct destination ("class Example" or "int someMethod").
- Also note that cursor is inserted right after opening brace ({) or on the end of previous line before closing brace (}). This is important to calculate correct number of new lines in the following print command if any.


### Print (or Type)

This action prints something at cursor or replaces selected text. In case if there are multiple selected regions, it will print in each of it.

<pre><code>
Print "some text"

Print:
```
Multiline text
goes here.
```
</code></pre>

### Replace

Same as Print, but has slight delay before execution, making it perfect for using with Select (because user gets better understanding of what's going on on the screen).

### Remove selected

Simple command, which removes selected text. This command includes slight delay, so that user would see what's being deleted after select.

### Wait

Sometimes, when you do a series of selecting-typing it's desired to place few delays so that user could catch-up with changes. Usually 500ms-1000ms delay is sufficient.

```
Select "something"

Wait 500ms

Select "something else"
```

### Compilation

First of all, there's no real compilation, we fake it.

Compilation actions is pretty much a combination of 2 popover actions.

1. First you put a ``#C`` with a message to explain compilation.
2. Then you put either ``#S`` or ``#F`` depending on successful or failed compilation, and put the success/error text.

Example:
```
#C Let's compile this baby.

#S Everything works fine!

…

#C Let's compile this baby.

#F Error! Unknown variable <code>b</code> in method "someMethod"

Select "b" in "someMethod"

```

### Set step

To advance steps, use following code.

```
Set step 1

…

Set final step
```

### Indent

Use `Indent` or `Indent N times` to add indentation in selected text, where `N` is a number of tab stops.


### Deindent

Use `Deindent` or `Deindent N times` to reduce indentation in selected text, where `N` is a number of tab stops.

