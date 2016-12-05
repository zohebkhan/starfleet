Starfleet Mine Clearing Exercise Evaluator

----------------------------------------------------------------------------------------------------------------------------------------

Steps to build :

1) Clone the repository in your local and import as a Java project on Eclipse or similar IDE

2) Export as a Runnable JAR and name JAR as starfleet.jar

3) Add files "field" and "script" (or copy from resources folder in repository) in the location of the JAR

4) Open Command Prompt in the location where JAR is created

5) Run command: java -jar starfleet.jar field script

----------------------------------------------------------------------------------------------------------------------------------------

Assumptions:

1) Program expects the file names in order as mentioned in step 5 of Build instructions

2) The Problem Statement PDF has south east printed without comma - has been assumed to be a typing mistake and taken as 2 separate movements - south, east

----------------------------------------------------------------------------------------------------------------------------------------

Overview :

The program has following components: 

A datastructure that maintains the status of mines left in the cuboid, 

Methods to determine dimensions of cuboid specifically the height which plays a crucial role to drive the scoring, 

Method to regularly update the distance between mines and vessel when it drops at constant speed of 1km/s, 

Different methods to move the vessel 

Different methods to fire the volleys in different patterns based on input in the script file

Scoring based on following criterias: 

 a) Fail if a mine is missed
  
 b) Fail if the vessel runs out of steps when there are mines still remaining
  
 c) Pass with 1 point if all mines are cleared but steps are remaining
  
 d) Pass with n points if all mines are cleared (n depends on shots fired and moves used)
  
