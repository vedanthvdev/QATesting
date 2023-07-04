#Welcome to the APi/Selenium testing framework

#Java Version 17 corretto is the JRE being used. Please set your environment of your ide accordingly

#Install few plugins like lombok, Cucumber for Java, Gherkin in IDE

#Clone the project

#To run the selenium test, Open the terminal or command prompt(Win) and runn the below command to start the node

~ java -jar /path/to/project/QATesting/node/selenium-server-4.10.0.jar standalone --selenium-manager true

#Mac(Intel and M1) and windows compatible(Tested).

#Intellij IDE was used.


To trigger hub and then register a node into that hub do the below

#Terminal:

~ java -jar /Users/chintuvedanth/IdeaProjects/QATesting/node/selenium-server-4.10.0.jar hub

~ java -jar /Users/chintuvedanth/IdeaProjects/QATesting/node/selenium-server-4.10.0.jar node --hub "http://localhost:4444/wd/hub" --selenium-manager true


#Using the standalone takes care of both of these like below

~ java -jar /Users/chintuvedanth/IdeaProjects/QATesting/node/selenium-server-4.10.0.jar standalone --selenium-manager true
