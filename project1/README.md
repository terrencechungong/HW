# Project 1: Property-Based Testing

Due: September 12th, 2024 at 11:59 PM

**This is an individual assignment. You must work on this project alone.**

## Rules

You may import any ```java.util``` and ```junit-quickcheck``` utils you need to 
implement the project. Anything else is disallowed. Helper functions are allowed 
but we will not explicitly call them. You will only get one submission, so it is 
in your best interest to thoroughly test your code with property-based tests.

## Part 1: Warmup

There are three parts: Tree.java, MinHeap.java, and DeskCollection.java.

### a. Balanced Binary Search Tree

There are three functions you must implement: 

```insert```, which inserts an integer into the tree in BST order. If the integer 
is already in the tree, do nothing.

```makeBalancedBST```, which takes a list of integers and creates a balanced BST. 
A BST is balanced if the left and right subtrees differ in height by at most one, 
the left subtree is balanced, and the right subtree is balanced.

```balanceBST```, which takes a node representing the root of a BST and returns
the balanced version of the BST with the same values. 

### b. Min Heap

For this part, you will implement a min heap as a Java ArrayList. Recall that
a min heap is a data structure where each node is smaller than or equal to its children.
In a list implementation of a min heap, the children of the value at index i
are at indices ```2 * i``` for the left child and ```2 * i + 1``` for the right child. The
parent of index i is ```(i - 1) // 2```, where ```//``` is floor division.

Most of the functions are self-explanatory, but ```siftUp``` and ```siftDown``` may be confusing.

```siftUp``` takes an index and shifts the element at that index up the heap until it is 
in the correct position, i.e. both of its children are greater than it. ```siftDown``` does the
same but downwards instead. Note that both functions are marked as ```private```, so 
they will not be explicitly tested, but you may find it helpful to implement them.

### c. Desk Collection
Anwar is an avid collector of Herman Miller desks (not actually). He wants to
display his collection in his desk room, but because he doesn't want to stack his 
precious desks, he needs to make sure a given arrangement of desks (given by their 
top left and bottom right coordinates) do not overlap. Note that if the edges of desk 
touch this is not overlap.

```addDesk``` takes a desk and adds it to the collection. If the desk does not
overlap with any other desks, the desk is added and true is returned. Otherwise, 
simply return false.

```doOverlap``` is a helper function that checks if two desks overlap. Implement
the overloaded function that takes 4 points (the top left and bottom right coordinates
of each of the two desks)

## Part 2: Word Frequency 

In this part, we use a linked list to count the words from a given text file or URL. 
The link list is maintained in descending order of frequency. The frequency class 
contains a private Node class that has been implemented for your use. You will 
need to make these nodes comparable. Each node can hold a key (the word), a count 
(the frequency of the word), and a reference to the next Node in the linked list.

There are only four public methods in the frequency class: insert, getCount, 
getWords and iterator. The rest are private methods and therefore we cannot test 
them directly. However, we strongly suggest you implement them to reduce code 
duplication and make your implementation clearer.

The public insert method of the frequency class will be used to insert a word 
into the linked list. When a new word is inserted, it adds the word into the list. 
If the word exists in the list, we simply increment the value of "count" for the 
word. We want to keep the words in the linked list by their frequency order 
(in descending order of their count). Therefore, when the frequency of a word 
has changed, we have to move the word to the correct position in the linked list. 
If two words have the same frequency, they are sorted alphabetically. 
For example, if we insert three words Alice, Bob and Cathy, we will have:

```(Alice, 1) -> (Bob, 1) -> (Cathy, 1) -> null```

If we insert another word, Bob, then we will have

```(Bob, 2) -> (Alice, 1) -> (Cathy, 1) -> null```

If we insert the word, Cathy twice, then we will have

```(Cathy, 3) -> (Bob, 2) -> (Alice, 1) -> null```

When multiple words have the same frequency, they should be arranged in ascending 
alphabetical order as shown above.

## Part 3: Find the bugs

A train company has decided to make it easier for enterprise users to reserve seats 
and to search their reservations by adding search by trainID and date range. 


To improve performance, they have decided to create an LFU (least frequently used) 
cache, so frequent searches with the same parameter are cached. 

If a new item needs to be added to the cache and the cache is at capacity, 
the least accessed item is removed from the cache. If there are multiple such items, 
the oldest item is removed.
For example, if the cache capacity is 2 and we add ```"x"```, add ```"y"```, then add ```"z"```, ```"x"``` 
will be removed since it was added before ```"y"```.

There are three subtle bugs in the code. Find the bugs with property based tests
and fix them!

## Submission

In order to submit your work, you need to compress the source file and submit the zipfile to Gradescope. You are allowed only ONE submission on Gradescope, so ensure you have thoroughly tested all of your solutions before submitting.
