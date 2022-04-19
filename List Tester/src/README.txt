****************
* Double Linked List
* CS 221
* 4/8/22
* Thomas Lonowski
**************** 

OVERVIEW:

 IUDoubleLinkedList.java contains a node-based, doubly-linked list implementation
 of an indexed unsorted list. ListTester.java is a test class for indexed unsorted lists.


INCLUDED FILES:

 List the files required for the project with a brief
 explanation of why each is included.

 e.g.
 * ListTester.java - test (and driver) class
 * IUDoubleLinkedList.java - a doubly-linked list implementation of an indexed unsorted list
 * DoubleNode.java - defines the underlying node structure used in IUDoubleLinkedList
 * IndexedUnsortedList.java - interface defining the methods of an indexed unsorted list
 * README - this file


COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac ListTester.java

 Run the compiled class file with the command:
 $ java ListTester (-a)(-s)(-m)
 
 Valid command line arguments include:
 -a : print results from all tests (default is to print failed tests, only)
 -s : hide Strings from toString() tests
 -m : hide section summaries in output

 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 ListTester.java is this program's driver class. It uses lambda references
 to create list scenarios following the format: original list -> change using
 list method(s) -> resulting list. This resulting list is then passed into
 a testing method. The particular testing method used depends on how many
 elements the resulting list contains (One-element lists are passed into the
 testSingleElementList() method, and so on...) The testing method runs a test
 suite on the list and prints the results to the console. The number of scenarios
 can be increased for greater testing accuracy.
 
 IUDoubleLinkedList.java is the class that is actually being tested by ListTester.java.
 It is a node-based, doubly linked list implementation of an indexed unsorted list (the
 methods of which are defined in IndexedUnsortedList.java). This class contains all the
 expected double-linked list methods.
 
 IUDoubleLinkedList.java also defines a private inner class, IUDLLiterator,
 which implements the ListIterator interface. This inner class defines all ListIterator
 methods. Of particular interest is the IUDLLiterator indexed constructor, which considers
 the index passed in and determines whether it is more efficient to work from the front or
 the back of the list when placing the iterator at index.
 
 All of the double-linked list methods could be rewritten to use the ListIterator's methods.
 However, I chose not to do this because I spent so much time writing the methods normally,
 and I am confident in their ability to pass the test suites. Someone looking to streamline
 the efficiency of this program could rewrite the list methods to use ListIterator's methods.
 

TESTING:

 To test the accuracy of my program, I started by adding more change scenarios.
 This gave me a broader scope of testing, and more confidence that my methods
 would truly be correct once they passed all the tests. Currently, I have
 implemented 31 change scenarios, and plan to add more before submitting the 
 program. From now on, I will focus on adding scenarios that use ListIterator's
 methods, as these are the most under-tested in my program.
 
 I also expanded the test suite to two- and three-element lists. I added additional
 tests, but eventually decided that they were unnecessarily redundant, and removed
 them.
 
 The only apparent bug remaining in the program is shown by the fact that many of
 the concurrent modification tests for the ListIterator class are failing. This
 likely means that modCount or iterModCount aren't being updated correctly somewhere
 in the code. I hope to fix this bug by the time of submission.


DISCUSSION:
 
 A significant issue with my program was apparent right away during testing. After
 implementing most of the double-linked list's methods, only 231 tests were being
 run when I ran ListTester.java. For reference, when I submitted my single-linked
 list program, around 1100 tests were being run. This number should have been even
 higher, since the double-linked list implements a ListIterator, which allows for
 significantly more tests.
 
 After asking about the problem in class, I scrolled to the top of the console
 output and saw that many of the tests were failing for an empty list. So I
 drew some pictured for visualization and thought through my methods as they
 would work when testing an empty list. After finding the bugs and fixing them,
 not only did the tests pass for an empty list, the number of tests being run
 skyrocketed! Every time I fixed an empty list or single element list bug, 
 several hundred more tests were run. I guess it makes sense that if the methods
 can't correctly edit an empty list, then none of the scenarios that build bigger
 lists would work, so their tests wouldn't be run. This was the cause of the low
 number of tests to begin with. I am now around 4500 tests.
 
 One other challenge I faced was with IUDLLiterator's indexed constructor. It was
 failing the test for an empty list (and several others). After drawing out the
 problem and tracing through the code, I realized that I had an incorrect if statement
 condition. Once I fixed the issue, the tests passed.

 
EXTRA CREDIT:

 This project had no extra credit opportunities.


----------------------------------------------------------------------------
