## Project 4 - Mazes 

##### Isha Chauhan & John Cooper

_Note_: There are important notes at the end of the README. 

Also, the .jar ending in ND is a fun side project that we don't want to be graded
for comments or style or anything. We just thought it would be fun to include.

---

### Overall Structure

Execution begins by reading in the values in a passed 
parameter file. A maze object, generator, and solver are
generated. Then the process of generating and solving 
occurs before anything is displayed to the screen. 
However, throughout the processed, a transcript of all 
the actions as graphical actions to be displayed later.
Once all of that has been complete, a graphicalMaze is 
created, and the animations are executed on this object.

---

### Controls

_Scroll_ - Changes the pace of the animation

---

### Main
#### MazeController

This class is the center of execution. Here is where all
the objects are controlled and passed to one another. 
This class also displays the actions of the solver and 
generator. The animation timer works in such a way that
animations are executed on a clock that doesn't depend
on the number of frames the animation is running at.

---

### Animation

This package holds all the different animations that can
affect the ```GraphicMaze```

#### AllAnimation

This animation takes every cell in the ```GraphicMaze```
and sets it to the same color.

#### Animation

The interface for a general animation that has an animation
time method (how long the animation should run) and an 
animate method, which takes a ```GraphicMaze``` and
modifies it to whatever the animation does

#### AnimationGroup

Holds a list of animations and executes them all 
simultaneously.

#### EdgeAnimation

Takes an edge and sets its color to a specified color.

#### GraphicMaze

This object has no internal maze logic. It is only an object
of graphical elements that can be modified by animations
that were created by the solver and generator on the 
```Maze``` object. Contains a subclass ```GraphicTile``` 
that holds the graphical elements of an individual tile.

#### TileAnimation

An animation that takes a tile location in the maze and sets 
the color of that tile to a specified color

#### WaitAnimation

An animation that does not change the state of the board,
but takes up time to pause the animation from executing.

---

### Generators

_Implemented Generators: DFS, Kruskal, Prim_

These generators take a blank maze (either with all walls
present or all walls cleared) and generates a maze from it.
These generators are _not_ responsible for setting the start 
and end tiles of the maze. This is delegated to the main class.

#### DFSGenerator

Takes a maze with all the walls present and removes the 
walls following the rules of depth-first-search

1) Choose an initial cell, mark it as visited and push
it onto a stack.
2) While the stack is not empty  
   * Pop a cell from the stack and make it the current cell  
   * If the current cell has any neighbours which have not be visited  
     * Push the current cell to the stack
     * Choose one of the unvisited neighbours
     * Remove the wall between the current cell and the chosen cell
     * Make teh chosen cell as visited and push it to the stack
    
#### GeneratorEnum

An enumeration of all the different generators for specifying which
generator to receive from the factory.

Values: ``` DFS```, ```KRUSTAL```, ```PRIM```, ```ALDOUS```, ```RECDIV```

These represent Depth-First-Search, Kruskal's Algorithm, Prim's Algorithm,
Aldous-Broder Process, and Recursive Division.

#### GeneratorFactory

A factory that creates a generator based on the passed ```GeneratorEnum```

#### KruskalGenerator

Takes a maze with all the walls present and removes the
walls following the rules of Kruskal's algorithm

1) Create a list of all walls, and create a set for each cell, each
containing just that one cell.
2) For each wall, in some random order:
   * If the cells divided by this wall belong to distinct sets:
     * Remove the current wall.
     * Join the sets of teh formerly divided cells.
    
#### MazeGenerator

A generator that takes a maze object and modifies it to be a proper
maze. These generators take different initial states the function
properly. The only function to implement is ```generate()```. This
also will return a list of animations that show all the relevant 
actions done by the generator.

#### PrimGenerator

Takes a maze with all the walls present and removes the
walls following the rules of Prim's algorithm

1) Start with a grid full of walls
2) Pick a tile, mark is as part of the maze, and add its walls to a wall list
3) While there are still walls in the list...
   * Pick a random wall from the list
   * If the wall separates two separated cells
     * Remove the wall, and make the unvisited cell as visited
     * Remove the wall from the list
    
---

### Maze

This package holds all the structure and elements of a maze. Unlike the 
```GraphicMaze```, these elements contain logic for removing and adding edges,
and has indexing values to set the state of cells.

#### Maze

This object holds all the relevant values and structure of the maze. Stores the 
maze as a 2-dimensional, list of lists of ```MazeTile```. Allows for a few
important actions: taking all the cells and making a 1D list of them 
(```collapseMaze```), and receiving a list of all the edges in the maze
(```getEdges```). There are also methods to change individual tiles, to remove
edges, and more.

We follow what we are calling the 'Cell-Centered' ideology. This is where the maze
is a collection of cells that each have walls instead of the maze simply being the 
list of all walls. This adds some complexity in removing walls since every wall
is duplicated from every cell keeping a copy of its neighbouring walls. However, 
this seemed to be far less complex than keeping a single collection of all the
walls, which we call the 'Wall-Centered' ideology. 

#### MazeDirection

The enumeration of the four directions traversable in the maze.

Values: ```NORTH```, ```EAST```, ```SOUTH```, ```WEST```

#### MazeEdge

This is a temporary data structure that has no copy as a member field in any 
class. It is only used to get all the edges in a maze and traverse over those 
edges. Edges have the row and column of the tile either above or to the left 
of them, along with a boolean that dictates if the wall is to the south or
to the east of this specified tile. 

#### MazeTile

A data structure for each tile in the maze. As stated above, this tile contains
information about all of its neighboring edges. These are stored as booleans for
each of the neighbouring edges. Also contains methods to get the neighbouring 
edges and cells to make maze traversal easier. Guards against removing walls on
the edge of the maze.

---

### Solvers

_Implemented Solvers: A*, Random Mouse, Pledge, Wall Following_

These solvers take a generated mazes with a set start and end tile and
find the solution. This solution is not saved, but is represented on
the ```GraphicMaze``` after the animations are all executed. All
path solutions are labelled in pink.

#### AStarSolver

This solver uses the A* solving algorithm. This is described in the
link below. In our specific implementation, we use the Manhattan
distance squared between a cell and the end as our h function.

Reference: https://en.wikipedia.org/wiki/A*_search_algorithm

#### MazeSolver

This is the general interface for a solver. Has one method to be 
implemented: ```solve()```. This takes a ```Maze``` and returns a
list of animations that show the relevant actions the solver did.

#### MouseSolver

Solves the maze using a random mouse moving around the maze,
allowing for backtracking. This algorithm is very inefficient.
This randomly chooses a neighbour cell and travels to it until
the end it reached.

#### MouseTread

A class that has the threads that are used to solve the maze with.
Spawns new threads at every junction. Keeps a record of the 
traversals similarly to A*.

#### MouseThreadController

Controls the mouse-threads. Acts as an interface between the
mouse-threads and the animation system.

#### PledgeSolver

Preferential direction is _always_ north

This solving algorithm acts almost identically to the wall-following
solver. The only difference is that, when the solver has rotated
the same amount left as right, the algorithm prefers going forward 
over following a specified wall.

#### SolverEnum

An enumeration of all the possible solvers. Useful in passing
values to the ```SolverFactory``` to generate a specific solver.
Also allows for an enum to be returned based on a passed string.

#### SolverFactory

A factory that returns a solver (or null) based on a passed
```SolverEnum```

#### WallSolver

Solves the maze by following a specific wall (left or right) of
the maze. 

#### WallThread

A thread that is used within the wall-following multi-threaded
solution. Allows for the thread to be started at different tiles,
(specifically the start and end tile of the maze). Solves the maze
exactly like standard wall following but stops once it meets
the path made by the other thread

#### WallThreadSolver

Solves the maze using the wall-following, multi-threaded
technique. Spawns two WallThreads at opposite ends of the maze
and controls the overall process of them running and gathering t
the animations.

---

### Important Notes

#### How we split up work

We decided it would be a good idea to start by doing some work individually
with a few methods that we decided would be useful. After this, we started
doing pair-programming for the solvers and generators.

#### .jar file

The jar file takes on argument: the filename with the specification for the
parameters (as per the project spec). This is assumed to be in the same
directory as the .jar file.

#### The Start and End Tile

In our maze, there is no way to break a wall on the edge of the maze. However,
it is equivalent to state that there are two tiles within the board on the edges
labeled as the start and end tile. The start tile is labeled __black__ 
and the end tile is labeled __red__.

#### Efficiency

* When using Kruskal's algorithm for larger mazes, (width of more than around
50 tiles on a side) it will take a number
of seconds to generate the maze since we did not try to make that method 
as efficient as possible.
  
#### MouseSolver Speed

The MouseSolver has such sporadic movements that solving can take
a very long time for larger board. Do not expect the solver to find
solutions to larger boards in any reasonable amount of time.

#### ND Mazes

This is an n-dimensional version of the maze solving algorithms for this project.
They are non-comprehensive but include a good number of them and also another one,
```primmod```, which is Prim's algorithm but modified to be stylistically 
different. The file input should be as follows

dimension  
screenHeight  
cellWidth  
generator  
solver  

The below is an example

3  
800  
60  
primmod  
astar  

The dimension of the maze can be 2-6.  
For dim 2 and screenWidth 800, the suggested minimum for the cellWidth is 4  
For dim 3 and screenWidth 800, the suggested minimum for the cellWidth is 20  
For dim 4 and screenWidth 800, the suggested minimum for the cellWidth is 42  
For dim 5 and screenWidth 800, the suggested minimum for the cellWidth is 60  
For dim 6 and screenWidth 800, the suggested minimum for the cellWidth is 80  
Anything smaller will be very difficult and very slow to generate/solve.

#### ND Mazes Controls

_Left-click_ - Toggles if the walls are visible or not  
_Right-click_ - Toggles if the outside walls are visible or not
_Scroll_ - Zooms in and out

---

### Known Bugs List


___

### To-Do List

1) Make Kruskal's algorithm not have the double for loop (store more info in the index)
2) Making the display look better