# Group10-Algorithms

**Roles:** 
- Developer: Kiara 
- Analyzer: Ruha
- Documentor: Milena
- Communicator: Liza

**Overview:**

This project implements and evaluates a hybrid sorting algorithm based 
on a research paper. The algorithm combines quicksort partitioning with 
counting sort on smaller subarrays to improve performance under certain 
conditions.

**Structure:**

- Experiment.java              
 -Runs all experiments

- CountingSort.java            
-classical counting sort
- QuickSort.java               
-Standard quicksort
- QuickInsertionSort.java      
-Quicksort + insertion sort
- QuickCountingSort.java       
-Proposed hybrid algorithm
- SortFunctions.java           
-Helper methods (partition, min/max, array generation)
- README.md                    
-Project documentation

**How run and produce results:**

1. Compile the project

   -javac *.java
2. Run experiments

   -java Experiment

