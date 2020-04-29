# BSPQ20-E1
This is an application that helps people find events that match their interests and take place near their area. Not only can users browse over their recommended events, but also they can mark them as interesting, so that event organizers can find out how appealing their proposals are before they take place. Besides, organizers will be able to post updates on their events so that people interested in them can be up to date.

## Getting Started
### Prerequisites
* Java
* MongoDB server
* Maven

### Building and running the project
These are the steps that need to be followed in order to succesfully build and run the project on Windows:
#### 1. Set up the MongoDB server
1. Open a new CMD window and start the MongoDB server with the *mongod* command
```
mongod
```
2. Create an empty database called **bsqp20e1** (you can do it directly from the command line or you can use a visual MongoDB client such as Robo 3T or Compass)

##### 1.1. Create the schema
1.  Open a new CMD window, go to the directory where the *pom.xml* file is inside the project and run the following command:
```
mvn datanucleus:schema-create
```

Note: the rest of the commands on this guide should be executed from the current directory (the one where the *pom.xml* file is placed)

#### 2. Compile the project
1. Open a new CMD window and run the following command:
```
mvn clean compile
```

#### 3. Start up the server
1. Run the following command (you can use the CMD window open in the previous step if you want to):
```
mvn jetty:run
```

#### 4.  Run the client app
1. Open a new CMD window and run the following command:
```
mvn exec:java -Pclient
```
## Execute the tests
These are the commands that need to be introduced in order to run the project tests on Windows:
###  Tests without performance:
1. Open a new CMD window and run the following command:
```
mvn clean test
```

###  Tests with performance (takes longer time):
1. Open a new CMD window and run the following command:
```
mvn clean test –DargLine=”-Dcontiperf.active=false”
```

###  Run the client
1. Open a new CMD window and run the following command:
```
mvn [clean compile] exec:java -Pclient
```

### Run the server
1. Open a new CMD window and run the following command:
```
mvn [clean compile] jetty:run
```

## Authors

* **Mikel Moreno** - [miikel23](https://github.com/miikel23)
* **Vasileios Matthaios** - [BillMat](https://github.com/BillMat)
* **Erik Sáenz de Ugarte** - [ErikSdU](https://github.com/ErikSdU)
* **Iván García** - [ivan-garcia1996](https://github.com/ivan-garcia1996)
* **Pablo Villacorta** - [pablo-villacorta](https://github.com/pablo-villacorta)

