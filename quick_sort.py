import csv
import sys
import time

# Function to read the entire dataset from the CSV file
# Each row is stored as a tuple: (integer, string)
def load_dataset(filename):
    data = []
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            data.append((int(row[0]), row[1]))  # Convert first column to int
    return data

# Recursive Quick Sort using last element as pivot
def quick_sort(arr, low, high):
    if low < high:
        pi = partition(arr, low, high)     # Partition the array
        quick_sort(arr, low, pi - 1)       # Sort left half
        quick_sort(arr, pi + 1, high)      # Sort right half

# Partition function for Quick Sort
def partition(arr, low, high):
    pivot = arr[high][0]  # Use integer as pivot
    i = low - 1
    for j in range(low, high):
        if arr[j][0] < pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]  # Swap smaller element to the left
    arr[i + 1], arr[high] = arr[high], arr[i + 1]  # Place pivot in correct position
    return i + 1

# Write sorted data to output file
def save_sorted_csv(data, filename):
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for row in data:
            writer.writerow(row)

def main():
    if len(sys.argv) != 2:
        print("Usage: python quick_sort.py <dataset_filename.csv>")
        return

    input_file = sys.argv[1]

    # Load dataset into memory
    data = load_dataset(input_file)

    # Start timing (in nanoseconds, converted to seconds)
    start_time = time.perf_counter_ns()
    quick_sort(data, 0, len(data) - 1)  # Sort the dataset
    end_time = time.perf_counter_ns()

    # Save sorted output to new CSV file
    output_file = f"quick_sort_{len(data)}.csv"
    save_sorted_csv(data, output_file)

    # Calculate time taken in seconds
    duration_seconds = (end_time - start_time) / 1_000_000_000
    print(f"Sorted {len(data)} rows in {duration_seconds:.4f} seconds")

# Entry point
if __name__ == "__main__":
    main()


