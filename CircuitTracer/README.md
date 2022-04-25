********************
* Circuit Tracer
* CS 221
* 4/24/2022
* Thomas Lonowski
******************** 

OVERVIEW:

 Concisely explain what the program does. If this exceeds a couple
 of sentences, you're going too far. The details go in other
 sections.


INCLUDED FILES:

 List the files required for the project with a brief
 explanation of why each is included.

 e.g.
 * Class1.java - source file
 * Class2.java - source file
 * README - this file


COMPILING AND RUNNING:

 Give the command for compiling the program, the command
 for running the program, and any usage instructions the
 user needs.
 
 These are command-line instructions for a system like onyx.
 They have nothing to do with Eclipse or any other IDE. They
 must be specific - assume the user has Java installed, but
 has no idea how to compile or run a Java program from the
 command-line.
 
 e.g.
 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac Class1.java

 Run the compiled class file with the command:
 $ java Class1

 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 This is the sort of information someone who really wants to
 understand your program - possibly to make future enhancements -
 would want to know.

 Explain the main concepts and organization of your program so that
 the reader can understand how your program works. This is not a repeat
 of javadoc comments or an exhaustive listing of all methods, but an
 explanation of the critical algorithms and object interactions that make
 up the program.

 Explain the main responsibilities of the classes and interfaces that make
 up the program. Explain how the classes work together to achieve the program
 goals. If there are critical algorithms that a user should understand, 
 explain them as well.
 
 If you were responsible for designing the program's classes and choosing
 how they work together, why did you design the program this way? What, if 
 anything, could be improved? 

ANALYSIS:

Questions:
 1. How does the choice of Storage configuration (stack vs queue) affect the
    sequence in which paths are explored in the search algorithm? (This requires
    more than a "stacks are LIFOs and queues are FIFOs" answer.)
 2. Is the total number of search states (possible paths) affected by the choice
    of stack or queue?
 3. Is using one of the storage structures likely to find a solution in fewer
    steps than the other? Always?
 4. Does using either of the storage structures guarantee that the first solution found will be a shortest path?
 5. How is memory use (the maximum number of states in Storage at one time) affected by the choice of underlying structure?
 6. What is the Big-Oh runtime order for the search algorithm? Does it reflect the maximum size of Storage? Does it reflect the number of board posisions? Does it reflect the number of paths explored? Does it reflect the maximum path length? Is it something else? What is 'n'? What is the primary input factor that increases the difficulty of the task?
 
Answers:
 1. Using a stack as the underlying storage configuration results in the paths
    being explored like the program is going down a rabbit hole. From the 
    starting point on the board, a move is made. Then, from that move, every
    other possible move is explored until either a solution or a dead end is
    found. Then, the program backs up to the last unexplored move and continues
    exploring every possible move from that point. Using a queue...
 2. Usage of a stack vs. a queue does not change the number of possible paths
    and solutions. The same search states are identified with each data type,
    only they are explored in different orders.
 3. When using a stack, the program follows one path until a solution or dead
    end is found. With a queue, however, every possible first move is explored,
    then every possible second move, etc. Since most solutions are found after
    several moves on the board, it is likely that a stack will allow the program
    to find a single solution more quickly. This is not always true, as sometimes
    the solution is found very close to the starting point, in which case a queue
    has the advantage.
    
    For example, using the board,
    
    	1 0 0 2
    	0 0 0 0
    
    the program with a stack configuration finds the first solution after six
    trace states, whereas with a queue configuration it locates a solution after
    only five trace states. Not only that, the queue configuration finds the best
    solution (two moves), while the stack configuration finds a worst solution
    (four moves).
    		 
 4. Since usage of a queue makes the program explore every possible first move,
    then every possible second move, etc., it guarantees that the first solution
    that is found will be the fastest solution.
 5.  

TESTING:

 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?


DISCUSSION:
 
 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
 
 What parts of the project did you find challenging? Is there anything
 that finally "clicked" for you in the process of working on this project?
 
 
EXTRA CREDIT:

 If the project had opportunities for extra credit that you attempted,
 be sure to call it out so the grader does not overlook it.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
