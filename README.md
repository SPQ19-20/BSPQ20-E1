# BSPQ20-E1
### Project definition
This is an application that helps people find events that match their interests and take place near their area. Not only can users browse over their recommended events, but also they can mark them as interesting, so that event organizers can find out how appealing their proposals are before they take place.

Besides, organizers will be able to post updates on their events so that people interested in them can be up to date. 

### How to
There are several tools that need to be installed in the host so that the project can be built and executed:
* Java
* MongoDB server
* Maven

These are the steps that need to be followed in order to succesfully build and run the project on Windows:
#### 1. Set up the MongoDB server
1. Open a new CMD window and start the MongoDB server with the *mongod* command
2. Create an empty database called **bsqp20e1** (you can do it directly from the command line or you can use a visual MongoDB client such as Robo 3T)

##### Create the schema
1.  Open a new CMD window, go to the directory where the *pom.xml* file is inside the project and run the following command:
	*mvn datanucleus:schema-create*

	Note: the following commands should be executed from this  directory

#### 2. Compile the project
1. Open a new CMD window and run the following command:
	*mvn clean compile*

#### 3. Start up the server
1. Run the following command (you can use the CMD window open in the previous step if you want to):
	*mvn jetty:run*

#### 4.  Run the client app
1. Open a new CMD window and run the following command:
	*mvn exec:java -Pclient*

