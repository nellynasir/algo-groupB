# Final binary_search.py
import csv
import sys
import time
import random

def load_data(filename):
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        return [(int(row[0]), row[1]) for row in reader]

def binary_search(arr, target):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = (left + right) // 2
        if arr[mid][0] == target:
            return mid
        elif arr[mid][0] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1

def measure_time(arr, target, repeat):
    for _ in range(1000):
        binary_search(arr, arr[0][0])  # warm-up

    start = time.perf_counter()
    for _ in range(repeat):
        binary_search(arr, target)
    end = time.perf_counter()

    return (end - start) * 1000 / repeat  # ms per search

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python binary_search.py <sorted_dataset.csv>")
        sys.exit(1)

    data = load_data(sys.argv[1])
    data.sort(key=lambda x: x[0])
    size = len(data)
    repeat = size * 10

    best = data[size // 2][0]
    avg = data[int(size * 0.37)][0]
    worst = -1

    best_time = sum(measure_time(data, best, repeat) for _ in range(5)) / 5
    avg_time = sum(measure_time(data, avg, repeat) for _ in range(5)) / 5
    worst_time = sum(measure_time(data, worst, repeat) for _ in range(5)) / 5

    with open(f"binary_search_{size}.txt", 'w') as out:
        out.write(f"Best case time   : {best_time:.4f} ms\n")
        out.write(f"Average case time: {avg_time:.4f} ms\n")
        out.write(f"Worst case time  : {worst_time:.4f} ms\n")

    print(f"Done. Output saved to binary_search_{size}.txt")
