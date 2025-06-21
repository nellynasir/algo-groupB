# dataset_generator.py
import random
import string
import csv
import sys

def generate_random_string(length=6):
    return ''.join(random.choices(string.ascii_lowercase, k=length))

def generate_dataset(n, output_file):
    data = set()
    while len(data) < n:
        num = random.randint(0, 1_000_000_000)
        data.add(num)

    with open(output_file, 'w', newline='') as f:
        writer = csv.writer(f)
        for num in data:
            writer.writerow([num, generate_random_string()])

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python dataset_generator.py <size>")
    else:
        size = int(sys.argv[1])
        generate_dataset(size, f"dataset_{size}.csv")
