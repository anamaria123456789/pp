import re

def read_file(file_path):
    with open(file_path, 'r') as file:
        return file.read()

def remove_punctuation(text):
    return re.sub(r'[^\w\s]','',text)

def remove_extra_spaces(text):
    return ' '.join(text.split())

def to_lowercase(text):
    return text.lower()

def filter_words_by_length(text, length):
    words = text.split()
    return ' '.join([word for word in words if len(word) == length])

def main():
    file_path = '/home/student/pp/lab1/file'
    content = read_file(file_path)
    content = remove_punctuation(file_path)
    content = remove_extra_spaces(file_path)
    content = to_lowercase(content)
    filtered_content = filter_words_by_length(content, 5)

    print(filtered_content)

if __name__ == "__main__":
    main()