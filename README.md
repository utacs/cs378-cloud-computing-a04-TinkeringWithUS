# Please add your team members' names here. 

## Team members' names 

Student Name: Danny Zheng

Student UT EID: dz5622

Student Name: David Liu

Student UT EID: dcl2257

Student Name: Maxwell Wu

Student UT EID: mmw3567

Student Name: Shehreen Rahman

Student UT EID: sr53259

 ...

##  Course Name: CS378 - Cloud Computing 

##  Unique Number: 51515
    


# Add your Project REPORT HERE 
## ----- Outputs -----

### TASK 1: 
0	227398
1	176057
2	135731
3	105623
4	81840
5	66426
6	120976
7	197013
8	237831
9	248260
10	241814
11	248155
12	263973
13	263456
14	276698
15	275806
16	243410
17	272186
18	331009
19	344850
20	323517
21	319257
22	309174
23	276840

### TASK 2:
00AC8ED3B4327BDD4EBBEBCB2BA10A00	1.0
00DC83118CA675B9A2876C35E3398AF5	1.0
0219EB9A4C74AAA118104359E5A5914C	1.0
FE757A29F1129533CD6D4A0EC6034106	1.0
FF96A951C04FBCEDE5BCB473CF5CBDBF	1.0

### TASK 3:
FD2AE1C5F9F5FBE73A6D6D3D33270571	4094.9998
A7C9E60EEE31E4ADC387392D37CD06B8	1260.0
D8E90D724DBD98495C1F41D125ED029A	630.0
E9DA1D289A7E321CC179C51C0C526A73	231.29999
74071A673307CA7459BCF75FBD024E09	209.99998
95A921A9908727D4DC03B5D25A4B0F62	210.0
96E79218965EB72C92A549DD5A330112	204.0
42AB6BEE456B102C1CF8D9D8E71E845A	191.55
28EAF0C54680C6998F0F2196F2DA2E21	179.99998
FA587EC2731AAB9F2952622E89088D4B	179.99998

## SCREENSHOTS:

# Google Cloud Dataproc Machines
![image](https://github.com/user-attachments/assets/39ee3122-76fc-4727-a43e-11274aaae923)

# YARN History
![image](https://github.com/user-attachments/assets/f70e2a18-d0a5-4d1c-b749-a56ae0c6153c)

# Project Template

# Running on Laptop     ####

Prerequisite:

- Maven 3

- JDK 1.6 or higher

- (If working with eclipse) Eclipse with m2eclipse plugin installed


The java main class is:

edu.cs.utexas.HadoopEx.WordCount 

Input file:  Book-Tiny.txt  

Specify your own Output directory like 

# Running:




## Create a JAR Using Maven 

To compile the project and create a single jar file with all dependencies: 
	
```	mvn clean package ```



## Run your application
Inside your shell with Hadoop

To run top earners per minute, change runType to e, then do 
make run. To run multiple jobs, set runType to a concatenation of 
corresponding letters.

Running as Java Application:

```java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar SOME-Text-Fiel.txt  output``` 

Or has hadoop application

```hadoop jar your-hadoop-application.jar edu.cs.utexas.HadoopEx.WordCount arg0 arg1 ... ```



## Create a single JAR File from eclipse



Create a single gar file with eclipse 

*  File export -> export  -> export as binary ->  "Extract generated libraries into generated JAR"
