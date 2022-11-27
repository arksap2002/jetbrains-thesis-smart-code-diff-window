# Statistics pop-up window

## Description
The new function "Statistics" is located in the toolbar. It shows some statistics in a pop-up window. It includes the number of methods in the open file and the number of initializations of variables in the method pointed to by the cursor.
## Solution
To implement the plugin, I used technologies developed by JetBrains.\
To implement the Java code parser, I used the JavaParser framework, with which I had huge experience in my school project.
The number of methods I searched using the command `compilationUnit.findAll(MethodDeclaration.class).size()` - counting the number of declaration methods in the program.\
For searching the number of initialization variables, it was necessary firstly find the current method using the lines numbers of all methods and the cursor, and then using the command, similarly with the first part of this task, I searched `VariableDeclarator.class` inside this method.
## Demo video
Demo video is in the `video.mp4` file.