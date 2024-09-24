inputFile = taxi-data-sorted-small.csv 
outputFolder = output

outputFolders := gpsOutput earningOutput gpsRatioOutput intermediateRatio


runType = r

fetch_data:
	wget https://storage.googleapis.com/cs378/taxi-data-sorted-small.csv

run: 
	mvn clean package  
	rm -rf $(outputFolders)
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar $(runType) $(inputFile) $(outputFolder) 

add: 
	mvn clean
	rm -rf $(outputFolders)
	git add . 
	git status

clean: 
	mvn clean
	rm -rf $(outputFolders)