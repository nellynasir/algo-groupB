import sys
import time
import csv

class DataEntry:
    """Class to hold the data from CSV (integer and string)"""
    
    def __init__(self, number, text):
        """Constructor to initialize the data"""
        self.number = number
        self.text = text
    
    def __lt__(self, other):
        """Less than comparison for sorting"""
        return self.number < other.number
    
    def __le__(self, other):
        """Less than or equal comparison for sorting"""
        return self.number <= other.number
    
    def __gt__(self, other):
        """Greater than comparison for sorting"""
        return self.number > other.number
    
    def __ge__(self, other):
        """Greater than or equal comparison for sorting"""
        return self.number >= other.number
    
    def __eq__(self, other):
        """Equal comparison for sorting"""
        return self.number == other.number
    
    def __str__(self):
        """Convert the data to a string format"""
        return f"{self.number},{self.text}"

def merge_sort(data_list, start, end):
    """Method to perform merge sort"""
    if start < end:
        mid = (start + end) // 2
        
        # Recursively sort the left half
        merge_sort(data_list, start, mid)
        
        # Recursively sort the right half
        merge_sort(data_list, mid + 1, end)
        
        # Merge the two sorted halves
        merge(data_list, start, mid, end)

def merge(data_list, start, mid, end):
    """Method to merge the sorted sublists"""
    temp = []
    i, j = start, mid + 1
    
    # Merge the two halves while maintaining the order
    while i <= mid and j <= end:
        if data_list[i] <= data_list[j]:
            temp.append(data_list[i])
            i += 1
        else:
            temp.append(data_list[j])
            j += 1
    
    # Add remaining elements from both halves
    while i <= mid:
        temp.append(data_list[i])
        i += 1
    
    while j <= end:
        temp.append(data_list[j])
        j += 1
    
    # Copy the merged list back into the original list
    for i in range(start, end + 1):
        data_list[i] = temp[i - start]

def read_csv(filename):
    """Method to read data from the CSV file"""
    data = []
    try:
        with open(filename, 'r', newline='', encoding='utf-8') as file:
            reader = csv.reader(file)
            for line in reader:
                if len(line) == 2:
                    try:
                        number = int(line[0].strip())
                        text = line[1].strip()
                        data.append(DataEntry(number, text))
                    except ValueError:
                        # Skip lines that can't be converted to integer
                        continue
    except IOError as e:
        print(f"Error reading the file: {e}", file=sys.stderr)
    
    return data

def write_sorted_data(data, filename):
    """Method to write the sorted data to a file"""
    try:
        with open(filename, 'w', newline='', encoding='utf-8') as file:
            writer = csv.writer(file)
            for entry in data:
                writer.writerow([entry.number, entry.text])
    except IOError as e:
        print(f"Error writing the file: {e}", file=sys.stderr)

def main():
    """Main method"""
    # Check if command line arguments are provided
    if len(sys.argv) != 3:
        print("Usage: python merge_sort.py <input_file> <output_file>")
        print("Example: python merge_sort.py data_sample_1000.csv py_output.csv")
        return
    
    # Get file paths from command line arguments
    input_file = sys.argv[1]   # First argument is input file
    output_file = sys.argv[2]  # Second argument is output file
    
    # Read the dataset from the CSV file
    print(f"Reading data from file: {input_file}")
    data = read_csv(input_file)
    
    if not data:
        print("No data found in the file.")
        return
    
    # Measure sorting time
    start_time = time.time_ns()
    merge_sort(data, 0, len(data) - 1)
    end_time = time.time_ns()
    
    # Calculate the duration in seconds
    duration_in_seconds = (end_time - start_time) / 1_000_000_000.0
    
    # Write sorted data to the output file
    print(f"Writing sorted data to file: {output_file}")
    write_sorted_data(data, output_file)
    
    # Print the time taken for the sorting operation
    print(f"Merge Sort completed in {duration_in_seconds:.4f} seconds.")
    print(f"Sorted data has been saved to: {output_file}")

if __name__ == "__main__":
    main()