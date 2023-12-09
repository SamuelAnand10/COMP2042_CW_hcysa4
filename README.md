# COMP2042_CW_hcysa4
# Brick Game Project

## Compilation Instructions

### Prerequisites
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [JavaFX](https://openjfx.io/)

### Compilation Steps
1. Clone the repository: `[git clone https://github.com/SamuelAnand10/brick-game.git](https://github.com/SamuelAnand10/COMP2042_CW_hcysa4.git)`
2. Navigate to the project directory: `cd brick-game`
3. Compile the code: `javac -cp path/to/javafx-sdk-<version>/lib/*:. brickGame/Main.java`
4. Run the application: `java -cp path/to/javafx-sdk-<version>/lib/*:. brickGame.Main`

## Implemented and Working Properly

### Features

1. **Game Mechanics**
   - [ ] your first taken to a start screen to either load or start a new game 
   - [ ] To play the player must first either start a new game or load a presaved game
   - [ ] they then have to control a breaker using the right and left arrow keys
   - [ ] the ball bounces of the walls, the blocks, the breaker, the top and the ground
   - [ ] when the ball hits a block it gets destroyed and u gain a point
   - [ ] if you break a choco block a bonus floats to the bottom, You can collect it by moving the breaker to make contact with the bonus
   - [ ] if the ball hits the ground, you lose a heart
   - [ ] if you hit a heart block, you gain a heart
   - [ ] if u hit a gold block you gain invicibility for a few seconds, therefore you cannot lose a heart
   - [ ] the goal is to destroy all the blocks to level up, till u reach level 18

   

2. **Score System** 
   - [ ] You gain a point for every block you break
   - [ ] you gain 3 points for every choco bonus you retreive
   - [ ] you can collect points till you lose hearts or you reach past level 18

### Additional Information
   - [ ] you can press p to pause the game where u can resume or restart
   - [ ] you can press S to save the game
   - [ ] the background changes after reaching the half way point
   - [ ] game over has a background that appears as well as the oppourtunity to try again
   - [ ] game win has  a background for the champion as well the oppourtunity to restart

## Implemented but Not Working Properly

### Known Issues

1. **Collision Detection**
   - [ ] sometimes the threading of the events means that an event occur but the threads dont catch it, therefore the ball doesnt bounce of or the collision doesnt result in the destruction of blocks
   - [ ] To resolve this I used an interrupt handler as well as a scheduler so that this issue is not promenent and resolves quickly
   - [ ] this was implemented in the GameEngine class

2. **Score**
   - [ ] Score wasn't properly working properly due to some issues with using the animations of +1 etc
   - [ ] therefore I used Java Fx for the animations, this made it much more smoother and buggless
         

### Additional Information
Due to the nature of multithreading using the same data variables, even after using Scheduling etc., some events dont get handled properly.
This is exclusively the collisions of the ball and blocks. Even the heart reduction sometimes too.
## Features Not Implemented

### Unimplemented Features

1. **Feature Name**
   - [ ] Provide a brief explanation of the unimplemented feature.

## New Java Classes

### List of New Classes

1. *****Ball*****
   - Purpose: Handles initializing the ball in the game, with its variables.
   - Location: located seperately as ball.java in the src folder.

2. *****Breaker*****
   - Purpose: Handles initializing the breaker in the game, with its variables.
   - Location: located seperately as breaker.java in the src folder.
     
3. *****GameLoadSaver*****
   - Purpose: Is a data preparer class that pushes data to be saved into LoadSave with the correct fetching of the instances of variables and parameters
   - additionally, it also initializes the value of a loading game into the main program
   - Location: located seperately as GameSaverLoader.java in the src folder.
  
4. *****collisionChecker*****
   - Purpose:
     CollisionChecker is a class that specifically handles the flagging of collisions of the ball and the wall, breaker and blocks. As well as the top and bottom. It is the extract of the physics of the       ball as to reflectively bounce when hitting the wall and blocks, as well as the change of x value rate of change depending on the axis hit on the breaker.
     I've broken up the whole collision code into individual methods in the class and used a main function to run throught them all as apply the modifications to xball and yball.
     This function is used by the the function one of the threads loops.
     I've also implemented setter functions for the xball and yball variables as well as getter functions for the newly modified xball and yball.
   - Location: located seperately as collisionChecker.java in the src folder.

### Additional Information
All the code used was mainly taking the sub task run by main and using it for the individual classes as a means to refactor the code. Setter and getter functions were implemented but other than that only in collisionChecker was an additional a flagging function for a collsion was implemented. All classes also extends Main.java so that variables are updated in the Main.

## Modified Java Classes

### List of Modified Classes

1. **GameEngine**
   - Changes: I adjusted the try and catch function to instead of returning an error to handle the interrupt. Not only that but i additionally added a new method that starts PhysicsUpdate() and OnUpdate() as threads in a scheduler. 
   - Reason: This is because the catch function did not resolve a collision in thread resources, thereby ending the engine and therefore killing the program. Therefore adding a interrupt handler was important to resolve thread collisions and resource handling. Additionally, adding the scheduler was important as resource manangment for both PhysicsUpdate() and OnUpdate() as they use the same resources. Therefore a schdeuler resolves collisions.

2. **Score**
   - Changes: I've changed as animations to Java FX modules. Additionally adding a game over image and button to restart, as well as adding a game win image and button to restart.
   - Reason: This is because the animations were not working properly therefore I switched to using Java Fx modules for the animations. The additions were to make the game more complete.

3. **Main**
   Changes:
   - Added a stackpane instead of having just root as a pane. In which 2 new panes: pausepane and startpane, were added
   -  a pausepane() and a startpane() function for their respective panes to initialize an image and 2 button images. For pausepane() a restart and resume, for startpane() a new game and load game.
   -  used the ball,breaker classes to initialize the objects.
   -  Imported an image for the background of the game. After level 7 it changes
   -  image OnMouseClick() events for the startpane() and Pausepane() button events
   -  used a resumeFlag flag variable fo resuming and pausing, it implented in CollisonChecker() as a conditional statement to keep original xball and yball.
   -  a saveGame() method to utilize  the GameLoaderSaver class
   -  a P event to event handler to pause the game
   -  a moveBreaker() as a method to update breaker position variables
   -  moved the conditional statement for reduciton heart count before updating Platform in onUpdate()
   -  used collisionChecker in onPhysicsUpdate() for setting the ball physics
   
   Reason:
    

### Additional Information

Provide any additional context or details about the modifications.

## Unexpected Problems

### Challenges Faced

1. **Challenge Description**
   - Explain the unexpected challenge encountered.
   - Solution: Describe how you addressed or attempted to resolve the issue.

### Additional Information

Include any other information about unexpected challenges or problems.

## Acknowledgements

Mention any external libraries, resources, or tutorials used during the development.

## License

Specify the license under which your code is distributed (e.g., MIT License).
