inputFile = taxi-data-sorted-small.csv 
outputFolder = output

runType = e

run: 
	mvn clean package  
	rm -rf output
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar $(runType) $(inputFile) $(outputFolder) 

git_add: 
	mvn clean
	rm -rf output
	git add . 
	git status

clean: 
	mvn clean
	rm -r output