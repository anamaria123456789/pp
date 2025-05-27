#lab12tema1

from functools import reduce

# Lista inițială
data = [1, 21, 75, 39, 7, 2, 35, 3, 31, 7, 8]

# 1. Eliminare numere < 5
filtered = list(filter(lambda x: x >= 5, data))

# 2. Grupare în perechi consecutive
pairs = list(zip(filtered[::2], filtered[1::2]))

# 3. Înmulțirea fiecărei perechi
products = list(map(lambda p: p[0] * p[1], pairs))

# 4. Suma tuturor produselor
total = reduce(lambda acc, x: acc + x, products)

# Afișare intermediară
print("Filtrare (<5 eliminate):", filtered)
print("Perechi:", pairs)
print("Produse:", products)
print("Suma finală:", total)
print("\n\n\n")

#lab12tema2


def caesar_cipher(word, offset):
    result = ""
    for char in word:
        if char.isalpha():
            base = ord('A') if char.isupper() else ord('a')
            result += chr((ord(char) - base + offset) % 26 + base)
        else:
            result += char
    return result

def process_file(file_path, offset):
    with open(file_path, "r", encoding="utf-8") as f:
        text = f.read()

    words = text.split()

    encrypted_words = [
        caesar_cipher(word, offset) if 4 <= len(word) <= 7 else word
        for word in words
    ]

    result = " ".join(encrypted_words)
    return result

# Exemplu de folosire
file_path = "sql.txt"
offset = 3
result = process_file(file_path, offset)
print("Text criptat:\n", result)
