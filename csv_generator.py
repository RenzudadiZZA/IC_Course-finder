import re
import csv


def extract_values_module(html_file_path, output_csv_path):
    # Open and read the HTML file
    with open(html_file_path, 'r', encoding='utf-8') as file:
        html_content = file.read()

    # Regular expression to extract all "valuesModule" assignments
    pattern = r'valuesModule\[\d+\]\s*=\s*"(.*?)";'
    matches = re.findall(pattern, html_content, re.DOTALL)

    # Open CSV file for writing
    with open(output_csv_path, 'w', newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(
            ["Title", "Code", "Term", "Description", "Learning Outcomes", "Module Content", "Prerequisites", "Lecturer"])

        # Process each module
        for match in matches:
            # Extract fields with specific patterns
            title = re.search(r"<span id='CourseTitle'>(.*?)<", match)
            title = title.group(1) if title else "N/A"

            code = re.search(r"<span id='Codes'>(.*?)<", match)
            code = code.group(1) if code else "N/A"
            # \/ => ,
            code = re.sub(r'\\/', ',', code)

            term = re.search(r"Term: <\\/span>(.*?)<\\/div>", match)
            term = term.group(1).strip() if term else "N/A"

            description = re.search(r"Brief description: <\\/span>(.*?)<\\/div>", match)
            description = re.sub(r"<.*?>", "", description.group(1)).strip() if description else "N/A"

            learning_outcomes = re.search(r"Learning Outcomes: <\\/span>(.*?)<\\/div>", match)
            learning_outcomes = re.sub(r"<.*?>", "", learning_outcomes.group(1)).strip() if learning_outcomes else "N/A"

            module_content = re.findall(r"Module content(.*?)Module content", match, re.DOTALL)[0]
            module_content = re.sub(r"<.*?>", "", module_content)
            module_content = re.sub(r'\\t|\\r|\\n', '', module_content)
            # remove Close &#128462
            module_content = re.sub(r'\s+', ' ', module_content)
            module_content = re.sub(r'Close &#128462;', '', module_content)

            prerequisites = re.search(r"Prerequisites: <\\/span>(.*?)<\\/div>", match)
            prerequisites = re.sub(r"<.*?>", "", prerequisites.group(1)).strip() if prerequisites else "N/A"
            # example alt="Photo of Martin Holloway">
            lecturers = re.findall(r'Photo of (.*?)\\"', match)
            lecturers = ', '.join(lecturers) if lecturers else "N/A"
            print(lecturers)
            print('---------------------')
            # Write to CSV
            writer.writerow([title, code, term, description, learning_outcomes, module_content, prerequisites, lecturers])

    print(f"Data successfully extracted to {output_csv_path}")


# Run the function
extract_values_module('Module Descriptor 2024-25.html', 'module_descriptors.csv')