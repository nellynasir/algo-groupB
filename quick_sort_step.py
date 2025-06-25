import csv
import sys

# Function to read a specific range of rows from a CSV dataset
# Returns a list of tuples (integer, string) from start_row to end_row (inclusive)
def read_rows(filename, start_row, end_row):
    rows = []
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        for i, row in enumerate(reader):
            if start_row - 1 <= i <= end_row - 1:
                rows.append((int(row[0]), row[1]))  # Convert first column to int
    return rows

# Recursive Quick Sort that logs sorting steps after each partition
def quick_sort_with_steps(arr, low, high, steps):
    if low < high:
        pi = partition(arr, low, high, steps)  # Get pivot index
        steps.append(f"pi={pi} " + str(format_data(arr)))  # Log current state
        quick_sort_with_steps(arr, low, pi - 1, steps)     # Sort left side
        quick_sort_with_steps(arr, pi + 1, high, steps)    # Sort right side

# Partition function using last element as pivot
# Rearranges elements and returns the pivot index
def partition(arr, low, high, steps):
    pivot = arr[high][0]  # Use the integer as pivot
    i = low - 1
    for j in range(low, high):
        if arr[j][0] < pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]  # Swap smaller element to the left
    arr[i + 1], arr[high] = arr[high], arr[i + 1]  # Place pivot in correct position
    return i + 1

# Format array into readable string format: [int/str, int/str, ...]
def format_data(arr):
    return ["{}/{}".format(x[0], x[1]) for x in arr]

# Main function to handle input, sorting, and output
def main():
    if len(sys.argv) != 4:
        print("Usage: python quick_sort_step.py <csv_file> <start_row> <end_row>")
        return

    # Read input arguments
    filename = sys.argv[1]
    start_row = int(sys.argv[2])
    end_row = int(sys.argv[3])

    # Load dataset from selected row range
    data = read_rows(filename, start_row, end_row)

    # Initialize log with the initial unsorted array
    steps = [str(format_data(data))]

    # Perform quick sort with step tracking
    quick_sort_with_steps(data, 0, len(data) - 1, steps)

    # Output file will contain all steps of the sorting process
    output_file = f"quick_sort_step_{start_row}_{end_row}.txt"
    with open(output_file, 'w') as out:
        for line in steps:
            out.write(line + '\n')

    print(f"Steps saved to {output_file}")

# Run the script only if executed directly (not imported)
if __name__ == "__main__":
    main()
