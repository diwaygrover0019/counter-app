# counter-app
 Application to search words in a given data and find top occurrence words 

**Steps to run the project:**
1.	Import the project in your local IDE 
2.  Run commands:
a)	mvn clean
b)	mvn compile
3.	Run the project using CounterAppApplication.java (currently supported for JDK 8 or JDK 11)

**Assumptions**
1.  Used Spring Security in-memory authentication to demonstrate authentication.   
2.  The API's will work only with username:**_diway_** and password:**_pass1234_** as hard coded in the app.
3.  The sample paragraph has been kept at path:  src/main/resources/data.txt 
    -   If it's location is changed, then changes need to be done in CounterAppResourceLoader.java file.
4.  As per the Task 2 requirement, path variable top listing can only be 5, 10, 20, 30.

**REST API Endpoints**
1.  POST _/counter-api/search_
    -   Description: Search the entered texts, which will return the counts respectively. If the request body is not passed, API will give 400 (Bad Request) error.
    
2.  GET _/counter-api/top/{value}_
    -   Description: Provide the top texts which has the highest counts. If the path variable is not passed or incorrect value is passed, API will give 400 (Bad Request) error.
    
**Error Handling**
   -   Counter App handles all major Exceptions including Runtime Exception’s. We have a customized ErrorResponse object which we return to the consumers of the APIs.