import csv
import sys

# Read a specific range of rows from the dataset
def read_rows(filename, start_row, end_row):
    rows = []
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        for i, row in enumerate(reader):
            if start_row - 1 <= i <= end_row - 1:
                rows.append((int(row[0]), row[1]))
    return rows

# Recursive quick sort with step logging
def quick_sort_with_steps(arr, low, high, steps):
    if low < high:
        pi = partition(arr, low, high, steps)
        steps.append(f"pi={pi} " + str(format_data(arr)))
        quick_sort_with_steps(arr, low, pi - 1, steps)
        quick_sort_with_steps(arr, pi + 1, high, steps)

# Partition function (last element as pivot)
def partition(arr, low, high, steps):
    pivot = arr[high][0]
    i = low - 1
    for j in range(low, high):
        if arr[j][0] < pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

# Format array for logging
def format_data(arr):
    return ["{}/{}".format(x[0], x[1]) for x in arr]

def main():
    if len(sys.argv) != 4:
        print("Usage: python quick_sort_step.py <csv_file> <start_row> <end_row>")
        return

    filename = sys.argv[1]
    start_row = int(sys.argv[2])
    end_row = int(sys.argv[3])

    data = read_rows(filename, start_row, end_row)

    steps = [str(format_data(data))]  # initial array
    quick_sort_with_steps(data, 0, len(data) - 1, steps)

    output_file = f"quick_sort_step_{start_row}_{end_row}.txt"
    with open(output_file, 'w') as out:
        for line in steps:
            out.write(line + '\n')

    print(f"Steps saved to {output_file}")

if __name__ == "__main__":
    main()
