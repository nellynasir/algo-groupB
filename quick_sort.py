# quick_sort.py
import csv
import sys
import time

def load_dataset(filename):
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        return [(int(row[0]), row[1]) for row in reader]

# Quick Sort (last element as pivot)
def quick_sort(arr):
    def _quick_sort(items, low, high):
        if low < high:
            pivot_index = partition(items, low, high)
            _quick_sort(items, low, pivot_index - 1)
            _quick_sort(items, pivot_index + 1, high)

    def partition(items, low, high):
        pivot = items[high][0]  # Pivot = last element's integer
        i = low - 1
        for j in range(low, high):
            if items[j][0] <= pivot:
                i += 1
                items[i], items[j] = items[j], items[i]
        items[i + 1], items[high] = items[high], items[i + 1]
        return i + 1

    _quick_sort(arr, 0, len(arr) - 1)

def save_sorted_dataset(data, output_file):
    with open(output_file, 'w', newline='') as file:
        writer = csv.writer(file)
        for row in data:
            writer.writerow(row)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python quick_sort.py <dataset.csv>")
        sys.exit(1)

    input_file = sys.argv[1]
    data = load_dataset(input_file)

    start_time = time.perf_counter()
    quick_sort(data)
    end_time = time.perf_counter()

    n = len(data)
    output_file = f"quick_sort_{n}.csv"
    save_sorted_dataset(data, output_file)

    print(f"Sorting completed in {(end_time - start_time):.4f} seconds")
    print(f"Sorted output saved to {output_file}")
