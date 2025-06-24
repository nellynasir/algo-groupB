import sys
import csv

class DataEntry:
    """Inner class to represent data entries"""
    
    def __init__(self, integer, string):
        self.integer = integer
        self.string = string

class MergeSortStep:
    def __init__(self):
        self.steps = []
    
    def main(self):
        if len(sys.argv) != 4:
            print("Usage: python merge_sort_step.py <csv_file> <start_row> <end_row>")
            print("Example: python merge_sort_step.py data_sample_1000.csv 1 7")
            print(f"Arguments provided: {len(sys.argv) - 1}")
            for i in range(1, len(sys.argv)):
                print(f"Arg {i-1}: {sys.argv[i]}")
            return
        
        csv_file = sys.argv[1]
        start_row = int(sys.argv[2])
        end_row = int(sys.argv[3])
        
        try:
            # Read data from CSV file
            data = self.read_csv(csv_file, start_row, end_row)
            
            # Perform merge sort with step tracking
            self.merge_sort(data, 0, len(data) - 1)
            
            # Write steps to output file
            output_file = f"py_merge_sort_step_{start_row}_{end_row}.txt"
            self.write_steps_to_file(output_file)
            
            print(f"Merge sort steps written to: {output_file}")
            
        except IOError as e:
            print(f"Error reading file: {e}", file=sys.stderr)
        except Exception as e:
            print(f"Error: {e}", file=sys.stderr)
    
    def read_csv(self, filename, start_row, end_row):
        """Read CSV file and extract data from specified rows"""
        data = []
        
        try:
            with open(filename, 'r', newline='', encoding='utf-8') as file:
                reader = csv.reader(file)
                current_row = 1
                
                for line in reader:
                    if current_row > end_row:
                        break
                    
                    if current_row >= start_row:
                        if len(line) >= 2:
                            try:
                                integer = int(line[0].strip())
                                string = line[1].strip()
                                data.append(DataEntry(integer, string))
                            except ValueError:
                                # Skip lines that can't be converted to integer
                                pass
                    
                    current_row += 1
        
        except IOError as e:
            raise IOError(f"Error reading file: {e}")
        
        # Add initial state to steps
        self.steps.append(self.array_to_string(data))
        
        return data
    
    def merge_sort(self, arr, left, right):
        """Perform merge sort with step tracking"""
        if left < right:
            mid = left + (right - left) // 2
            
            self.merge_sort(arr, left, mid)
            self.merge_sort(arr, mid + 1, right)
            self.merge(arr, left, mid, right)
    
    def merge(self, arr, left, mid, right):
        """Merge two sorted subarrays and track the step"""
        # Create temporary arrays for left and right subarrays
        left_arr = []
        right_arr = []
        
        # Copy data to temporary arrays
        for i in range(left, mid + 1):
            left_arr.append(arr[i])
        
        for j in range(mid + 1, right + 1):
            right_arr.append(arr[j])
        
        # Merge the temporary arrays back into arr[left..right]
        i = j = 0
        k = left
        
        while i < len(left_arr) and j < len(right_arr):
            if left_arr[i].integer <= right_arr[j].integer:
                arr[k] = left_arr[i]
                i += 1
            else:
                arr[k] = right_arr[j]
                j += 1
            k += 1
        
        # Copy remaining elements of left_arr, if any
        while i < len(left_arr):
            arr[k] = left_arr[i]
            i += 1
            k += 1
        
        # Copy remaining elements of right_arr, if any
        while j < len(right_arr):
            arr[k] = right_arr[j]
            j += 1
            k += 1
        
        # Add current state to steps after merging
        self.steps.append(self.array_to_string(arr))
    
    def array_to_string(self, arr):
        """Convert array to string representation"""
        result = "["
        for i in range(len(arr)):
            result += f"{arr[i].integer}/{arr[i].string}"
            if i < len(arr) - 1:
                result += ", "
        result += "]"
        return result
    
    def write_steps_to_file(self, filename):
        """Write all steps to output file"""
        try:
            with open(filename, 'w', encoding='utf-8') as writer:
                for step in self.steps:
                    writer.write(step + '\n')
        except IOError as e:
            raise IOError(f"Error writing file: {e}")

def main():
    """Main function to run the merge sort step tracker"""
    merge_sort_step = MergeSortStep()
    merge_sort_step.main()

if __name__ == "__main__":
    main()