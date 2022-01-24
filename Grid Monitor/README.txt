*************************
* Grid Monitor Assignment
* CS 221
* 18 January 2022
* Thomas Lonowski
************************* 

OVERVIEW:

 This program reads in data from a text file representing a solar array
 (hereafter "grid"). It then performs calculations on that data and
 reports if the grid is in danger of exploding.

----------------------------------------------------------------------------

INCLUDED FILES:

 * GridMonitor.java - source file
 * GridMonitorTest.java - source file
 * GridMonitorInterface.java - source file
 * negatives.txt - testing data
 * oneByOne.txt - testing data
 * sample.txt - testing data
 * sample4x5.txt - testing data
 * sampleDoubles.txt - testing data
 * wideRange.txt - testing data
 * README - program information

----------------------------------------------------------------------------

COMPILING AND RUNNING:
 
 Open command prompt or the terminal, depending on your operating system.
 From the directory containing all source files, compile the driver class
 with the following command (dependent classes are automatically compiled):
 $ javac GridMonitorTest.java

 Run the compiled class file with the following command:
 $ java GridMonitorTest

 The program's output will be displayed in the console.

----------------------------------------------------------------------------

PROGRAM DESIGN AND IMPORTANT CONCEPTS:
 
 GridMonitor implements the GridMonitorInterface, which defines the methods
 necessary to determine if a grid is in danger of exploding. A grid is in 
 danger of exploding if one or more of its elements has a value that differs
 from the average of its neighboring elements by more than 50%. Each method
 in GridMonitor performs a individual calculation necessary to determine if
 the grid is in danger of exploding.
 
 In the GridMonitor constructor, two scanners are used to read input from a
 text file, which is then directed into a 2D array, the base grid. All of the
 calculations performed in the later methods are dependent on this initial grid.
 
 The calculations necessary to determine if a grid is in danger are:
 
 1) getSurroundingSumGrid(): calculates the sums of the four elements surrounding each
    element in the base grid. Try/catch statements are used to check if any of the 
    surrounding elements are out of bound. If they are out of bounds (this happens
    for elements on the grid border), then the value of the current base element is
    added to the sum. The sums are stored in a new 2D array, the sum grid.
 
 2) getSurroundingAvgGrid(): calculates the average of the sums by dividing each
 	element in the sum grid by 4. The averages are stored in a new 2D array,
 	the average grid.
 
 3) getDeltaGrid(): calculates the maximum delta value each base element is allowed
 	before it becomes in danger of exploding (50% of the average). This is obtained
 	by dividing each element in the average grid by 2. The deltas are stored in a new
 	2D array, the delta grid.
 
 ///////////////////
 // Continue here //
 ///////////////////
 
 4) getDangerGrid(): the final calculation, this determines if the original element
 	is within the acceptable delta range defined by the previous method. If the element
 	falls within the delta range, it is safe (not in danger of exploding). If it falls
 	outside of the delta range, it is unsafe (in danger of exploding). Safe elements
 	are assigned 'false' in the boolean 2D array dangerGrid, while unsafe elements are
 	assigned 'true'.
 	
 	*Note: since each of these methods requires the grid calculated by the previous method,
 		   the first line is a call to the previous method, which ensures that the previous
 		   grid has been assigned values.
 		   
 5) toString(): prints each grid (base grid, sum grid, average grid, delta grid, and
 	danger grid) for user understanding. Then checks to see if any of the elements in
 	the danger grid are true (in danger of exploding), and prints an appropriate String
 	informing the user of the result.
 	
 The GridMonitorTest Class contains several methods that test the output of the GridMonitor
 Class for correctness. Notably, the GridMonitorTest Class is the driver class for the 
 project and contains the main method. The assignment is graded based off of the test results
 from the GridMonitorTest Class. 
 
 ----------------------------------------------------------------------------

TESTING:

 The GridMonitor Class was tested with six different input files of varying sizes and data
 types. Positive and negative integers and doubles were tested. The GridMonitorTest Class
 performs additional tests, such as encapsulation tests. Whenever a grid  must be returned,
 a clone is generated and returned instead of the original grid. This ensures that the user
 cannot manipulate the original grid. If they were able to do so, they could alter the grid
 values, thus endangering the lives of Star Fleet crewmen and women.
 
 The program will not work when the input file contains non-numeric values, such as booleans.
 This could easily be fixed with an if statement in the GridMonitor constructor, but I have
 neglected to do so because the instructor has assured me that the program will not receive
 such input. If this is true, then there is no need to slow down the program with an 
 unnecessary if statement.

----------------------------------------------------------------------------

DISCUSSION:
 
 The most significant challenge I faced was figuring out how pass the encapsulation tests.
 Originally, I thought I could just create a copy variable and assign it to the original grid.
 However, I quickly realized that the copy just pointed to the original due to the nature
 of reference types. To fix this, I researched and discovered the .clone() method for
 arrays. I tried to use it on the 2D arrays, but this also led to unexpected issues.
 Researching further, I discovered that .clone() works on 2D arrays only if you use a for
 loop to iterate over the 2D array and clone each 1D array individually. This allowed me to
 pass the encapsulation tests.
 
 Another challenge I faced was the fact that the values in my sum grid ended up being twice
 their expected values. To solve this issue, I had to trace through my code for quite a while
 before finding the problematic line. It turned out that the getSurroundingSumGrid() method
 was called consecutively and sumGrid wasn't reset between each call. To solve this, I just
 re-initialized sumGrid at the start of the getSurroundingSumGrid().
 
 During this project I learned how to use try/catch statements as well as the 'throws' keyword.
 Since I didn't take CS 121 at BSU, I was unfamiliar with those concepts. I also learned how
 to navigate the Eclipse IDE, at least at a basic level. Coming from IntelliJ, I find
 that Eclipse doesn't have as good of an auto-complete feature. But this isn't a significant
 issue - more of an inconvenience. I'm also trying to sync my Eclipse workspace on multiple
 devices using Git, but this is proving quite difficult. If something goes wrong with my
 assignment submission, it's likely a result of physically transferring my Eclipse workspace
 between computers while working on this project. 
 
 I really enjoyed this assignment. I haven't gotten to take a straight programming course for
 years, so it was wonderful to get back into the swing of things. It reminded me of why I
 wanted to be a CS major in the first place. Thank you.
 
----------------------------------------------------------------------------