import fitz  # PyMuPDF
import re

def extract_phone_numbers(pdf_path):
    phone_numbers = []

    # Open the PDF file
    with fitz.open(pdf_path) as pdf:
        for page in pdf:
            # Extract text from the page
            text = page.get_text()

            # Regex to find phone numbers
            matches = re.findall(r'\+\d{1,3}\s?\d{5}\s?\d{5}', text)
            phone_numbers.extend(matches)

    return phone_numbers

# Path to your PDF file
pdf_path = 'RK1.pdf'  # Replace with your PDF file path
numbers = extract_phone_numbers(pdf_path)

# Print the extracted phone numbers
print(numbers)
