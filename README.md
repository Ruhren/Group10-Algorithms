# Group10-Algorithms

**Roles:** 
- Developer: Kiara 
- Analyzer: Ruha
- Documenter: Milena
- Communicator: Liza

**Overview:**

This project implements and evaluates a hybrid sorting algorithm based 
on a research paper. The algorithm combines QuickSort partitioning with 
Counting Sort on smaller subarrays to improve performance under certain 
conditions.

**Structure:**

- Experiment.java          : Runs all experiments
- CountingSort.java        : Classic Counting Sort
- QuickSort.java           : Classic QuickSort
- QuickInsertionSort.java  : QuickSort + InsertionSort
- QuickCountingSort.java   : Proposed algorithm
- SortFunctions.java       : Helper methods (partition, min/max, array generation)
- README.md                : Project documentation

**How to run and produce results:**

1. Open the terminal in the src folder of this project , then compile the project by running the following :
   -javac *.java
   
2. Run experiments by running the following command:
   -java Experiment

