# Encrypto
This project's main feature is in encrypting a text by generating a circular permutation of the alphabet in lowercases as a key and using ceasar ciper as a method for encryption

This project is made by using:

![Python](https://img.shields.io/badge/python-3670A0?style=flat&logo=python&logoColor=ffdd54)
 ![Tkinter](https://img.shields.io/badge/Tkinter-%2300BFFF.svg?style=flat&logo=Python&logoColor=white)

## Circular Permutation

A circular permutation is the number of arrangements of items in a circle when the order of items matters. Often, rotations are not considered to be different outcomes. 

## Features

This program consists three pages, Home/Main menu, Enrcrypt page, and Decrypt page

### Code for generating a permutation
~~~python
def generate_circular_permutation() -> str:
    alphabet = list(string.ascii_lowercase)
    for i in range(len(alphabet) - 1, 0, -1):
        j = random.randint(0, i)
        alphabet[i], alphabet[j] = alphabet[j], alphabet[i]
    return ''.join(alphabet)
~~~

### What it does
This function shuffles the alphabet randomly using a version of the Fisher-Yates algorithm, returning a new permutation of the letters each time it's called.

---

### Code for comparing 2 keys (circular permutations)
~~~python
def is_same_permutation(str1, str2) -> bool:
    return str2 in (str1 + str1 + str1)
~~~

### What it does
This function compares 2 permutation if its same circular wise by combining the 3 str1 to it's self by 3 times which then check if the str2 is in the arrangement of the 3 combined str1. If it is there we return True otherwise False

---

### Code for encrypting
~~~ python
def encrypt(text, key) -> str:
    alphabet = string.ascii_lowercase
    encrypted_text = ""
    shifter = 5
    for char in text:
        index = alphabet.find(char) if char.isalpha() else alphabet.find(char.lower())
        shifted_index = (index + shifter) % len(key)
        encrypted_text += key[shifted_index]
        
    return encrypted_text
~~~

### What it does
The encrypt function shifts each letter in the text by 5 positions within the alphabet and then substitutes it with a corresponding letter from the key. It uses the lowercase alphabet to find the letter's index, applies the shift, and wraps around using the length of the key. The resulting encrypted text is built by replacing each character with the letter at the shifted index in the key. However, it does not handle uppercase or non-alphabetic characters properly and the key length may cause issues with the alphabet's 26 letters.

---

### Code for decrypting
~~~python
def decrypt(text, key) -> str:
    alphabet = string.ascii_lowercase
    decrypted_text = ""
    shifter = 5 % len(key)
    for char in text:
        index = key.find(char) if char.isalpha() else key.find(char.lower())
        shifted_index = (index - shifter) % len(key)
        decrypted_text += alphabet[shifted_index]
        
    return decrypted_text
~~~
### What it does
The decrypt function reverses the encryption by shifting each letter in the text back by 5 positions using the key. It finds the index of each character in the key, subtracts the shift value, and then maps the result back to the corresponding letter in the lowercase alphabet. The decrypted text is built by appending these mapped letters. Similar to the encryption function, it doesn't handle uppercase or non-alphabetic characters well, and key length mismatches may cause issues.

---

### Code for saving a key
~~~python
def save_key(key:str) -> None:
    if not key:
        messagebox.showerror("Error", "No key to save")
        return
    
    saved_keys = []
    try:
        with open("key.csv", "r") as file:
            values = file.read().splitlines()
            for value in values:
                if value == "key":
                    continue
                saved_keys.append(value)
    except FileNotFoundError:
        saved_keys = []
    
    for saved_key in saved_keys:
        if is_same_permutation(key, saved_key):
            messagebox.showerror("Error", "Key already exists")
            return
    
    with open("key.csv", "a", newline='') as file:
        writer = csv.writer(file)
        writer.writerow([key])        
    
    messagebox.showinfo("Success", "Key saved successfully")
~~~

### What it does
The save_key function saves a unique key to a CSV file, ensuring no duplicate or permuted versions of the key already exist. It first checks if the key is empty, displaying an error if so. Then, it tries to read existing keys from key.csv, handling the case where the file does not exist by initializing an empty list. It checks if the new key is a permutation of any saved keys using is_same_permutation, and if a match is found, it shows an error. If the key is unique, it appends it to the CSV file and displays a success message.

---

### Code for getting saved keys
~~~python
def get_saved_key() -> list:
    saved_keys = []
    try:
        with open("key.csv", "r") as file:
            keys = file.read().splitlines()
            saved_keys = [key for key in keys]
            return saved_keys
    except FileNotFoundError:
        return []
~~~

### What it does
The get_saved_key function retrieves and returns a list of keys stored in a CSV file called key.csv. It attempts to open the file and read its contents line by line, storing each line as a key in the saved_keys list. If the file is found, it returns the list of saved keys; if the file does not exist, it catches the FileNotFoundError and returns an empty list instead.

---