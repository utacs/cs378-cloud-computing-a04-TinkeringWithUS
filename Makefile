inputFile = taxi-data-sorted-small.csv 
outputFile = output.txt

runType = e

run: 
	mvn clean package  
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar $(runType) $(inputFile) $(outputFile) 

clean: 
	mvn clean