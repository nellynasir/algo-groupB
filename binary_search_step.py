import csv
import os

# Function to read the dataset from a CSV file
def read_dataset_from_csv(filename):
    dataset = []
    
    # Set the working directory to the directory of the script
    current_directory = os.path.dirname(os.path.abspath(__file__))
    dataset_path = os.path.join(current_directory, filename)
    
    try:
        with open(dataset_path, mode='r') as file:
            reader = csv.reader(file)
            for row in reader:
                value = int(row[0].strip())  # Parse the integer
                key = row[1].strip()  # String is the second column
                dataset.append((value, key))
    except IOError as e:
        print(f"Error reading file {dataset_path}: {e}")
    
    return dataset

# Manually sorts the dataset using selection sort
def selection_sort(dataset):
    n = len(dataset)
    
    # Selection Sort Algorithm
    for i in range(n - 1):
        min_index = i
        for j in range(i + 1, n):
            if dataset[j][0] < dataset[min_index][0]:
                min_index = j
        # Swap if we found a smaller element
        dataset[i], dataset[min_index] = dataset[min_index], dataset[i]

# Binary search function that tracks the search steps and adds the index of each comparison
def binary_search(dataset, target, search_path):
    left = 0
    right = len(dataset) - 1
    
    while left <= right:
        mid = left + (right - left) // 2  # Calculate the middle index
        mid_element = dataset[mid]
        
        # Record the comparison step with row number (index adjusted to 1-based index)
        search_path.append(f"{mid + 1}: {mid_element[0]}/{mid_element[1]}")

        # If the target is found
        if mid_element[0] == target:
            return mid  # Target found at the mid index
        
        # If the target is greater, ignore the left half
        if mid_element[0] < target:
            left = mid + 1
        # If the target is smaller, ignore the right half
        else:
            right = mid - 1
    
    # If the target is not found, return -1
    return -1

def main():
    # Get the target value from user input
    target = int(input("Please enter a target value: "))
    
    # File name of the unsorted dataset
    dataset_file = "dataset_sample_1000.csv"
    
    # Read the dataset from the CSV file
    dataset = read_dataset_from_csv(dataset_file)
    
    # Manually sort the dataset using selection sort
    selection_sort(dataset)
    
    # List to hold the steps of the binary search
    search_path = []
    
    # Perform binary search and track the steps
    result = binary_search(dataset, target, search_path)
    
    # Get the current directory (same directory as the script and dataset)
    current_directory = os.path.dirname(os.path.abspath(__file__))
    
    # Path for the output file in the same directory
    output_file = os.path.join(current_directory, f"binary_search_step_{target}.txt")
    
    # Write the search steps to a text file
    with open(output_file, 'w') as writer:
        for step in search_path:
            writer.write(step + '\n')
        
        # Append the result (either the found target or -1 if not found)
        if result == -1:
            writer.write("-1")
        else:
            writer.write(f"Target {target} found at index {result + 1}")  # Adjust to 1-based index

# Run the main function
if __name__ == "__main__":
    main()
