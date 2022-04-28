********************
* Circuit Tracer
* CS 221
* 4/26/2022
* Thomas Lonowski
******************** 

OVERVIEW:

 Concisely explain what the program does. If this exceeds a couple
 of sentences, you're going too far. The details go in other sections.


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
 4. Does using either of the storage structures guarantee that the first solution
    found will be a shortest path?
 5. How is memory use (the maximum number of states in Storage at one time) affected
    by the choice of underlying structure?
 6. What is the Big-Oh runtime order for the search algorithm? Does it reflect the
    maximum size of Storage? Does it reflect the number of board positions? Does
    it reflect the number of paths explored? Does it reflect the maximum path length?
    Is it something else? What is 'n'? What is the primary input factor that increases
    the difficulty of the task?
 
Answers:
 1. Using a stack makes the algorithm behave in following way: from the starting
    point on the board, all possible first moves are made. Then, from one of those
    moves, a second move is made, then a third, etc., until either a solution or a
    dead end is found. Then, the algorithm "backs up" to the last unexplored path
    and repeats the process. This can be thought of as a person mapping a cave network:
    you start at the entrance and walk down a tunnel until you either find the exit or
    hit a dead end. If you find an exit, you record which route you took to get there.
    If you hit a dead end, however, you backtrack until you reach another unexplored
    tunnel, and then explore it. You repeat this process until all the tunnels in the
    cave network have been explored and mapped. 
    
    Using a queue makes the algorithm behave in the following way: from the starting
    point on the board, all possible first moves are made. Then, from all of those
    first moves, every possible second move is made, then every possible third move,
    etc. This can be thought of as a whole team of explorers working to map a cave
    network. They start at the cave entrance, and each person picks a tunnel then
    walks down it at the same pace. When an exit is found, that person records the
    route and then stays there. When a dead end is found, that person lets the rest
    of the team know and then just stays there and dies. Bad luck.
    
    Summary: stacks make the program tunnel down one path until the end is reached,
    and then backtrack to another path and repeat the process. Queues, on the other
    hand, make the program establish all possible paths and then progress along each
    one evenly. 
 
 2. Usage of a stack vs. a queue does not change the number of possible paths
    and solutions. The same search states are identified with each data type.
    
 3. When using a stack, the program follows one path until a solution or dead
    end is found. With a queue, however, every possible first move is explored,
    then every possible second move, etc. Since most solutions are found after
    several moves on the board, it is likely that a stack will allow the program
    to find a single solution more quickly. This holds true for the given board:
    
    	1 0 0 0
    	0 0 0 2
    
    The program locates a solution faster with a stack configuration than with
    a queue configuration (five while loop iterations compared to six). Unfortunately,
    although the stack configuration locates a solution faster, it is not necessarily
    the best solution (least number of moves). In the previous example, the stack
    configuration's solution is five moves on the board, whereas the queue
    configuration's solution is only three moves.
    	
    While this pattern holds true most of the time, it is not always true. 
    Sometimes a solution is found very close to the starting point, in which
    case a queue configuration has the advantage. For example, using the board:
    
    	1 0 0 2
    	0 0 0 0
    
    The program locates a solution faster with a queue configuration than with
    a stack configuration (three while loop iterations compared to four). And
    once again, the queue configuration finds the best solution (two moves), 
    whereas the stack configuration finds the worst solution (four moves).
    		 
 4. Since usage of a queue makes the program explore every possible first move,
    then every possible second move, etc., it guarantees that the first solution
    that is found will be the fastest solution.
 
 5. Because usage of a queue causes the program to explore each path at the same
    time, it results in more trace states being held in storage at one time. For
    example, using given the board:
    	
    	1 0 0 0
    	0 0 0 2
    
    a queue configuration will hold a maximum of six trace states in storage during
    the program's duration, whereas a stack configuration will only hold a maximum
    of four states in storage.
    
 6. The Big-Oh runtime order of the algorithm is'n' is the board size. As board size increases, everything else increases
    too: maximum path length, number of possible moves/paths, and maximum required
    storage size. Board size is directly related to the complexity of the problem.

TESTING:

 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?
 
 During development, I made a main method in the CircuitBoard class itself to
 debug my exception handling code and better identify where invalid files
 weren't being caught. This allowed me to fine tune my program to catch all
 invalid file types.


DISCUSSION:
 
 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
 
 
 One problem I had during the early stages of development was identifying
 where the try/catch blocks and throws statements should go. It was a bit
 confusing because we had to work with three classes: CircuitBoard,
 CircuitTracer, and CircuitTracerTester. Eventually I was able to figure
 that all Exceptions should be thrown from CircuitBoard to the driver class,
 CircuitTracer, where they would be caught with a try/catch block.
 
 I had to look through the testCircuitTracerInvalidFile() method in CircuitTracerTester to
 figure out how it was determining whether an exception was being thrown or not. I found out
 that the test it just looked for console output containing the name of the expected exception.
 This helped me understand what my exception handling code was expected to do.
 
 It took me a while to understand how to instantiate a new Storage object. After
 reading through the class and its method documentations, I had a much better
 idea of how it works. Similarly, I looked through the Javadocs 8 to figure out
 what Point objects were and how I could use the class's methods in my code. Once
 I understood these things, converting the pseudo code algorithm to Java came
 to me more easily. 
 
EXTRA CREDIT:

 If the project had opportunities for extra credit that you attempted,
 be sure to call it out so the grader does not overlook it.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
