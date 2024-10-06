import random
import string

def generate_circular_permutation() -> str:
    alphabet = list(string.ascii_lowercase)
    for i in range(len(alphabet) - 1, 0, -1):
        j = random.randint(0, i)
        alphabet[i], alphabet[j] = alphabet[j], alphabet[i]
    return ''.join(alphabet)

def is_same_permutation(str1, str2) -> bool:
    return str2 in (str1 + str1 + str1)

def encrypt(text, key) -> str:
    alphabet = string.ascii_lowercase
    encrypted_text = ""
    shifter = 5
    for char in text:
        index = alphabet.find(char) if char.isalpha() else alphabet.find(char.lower())
        shifted_index = (index + shifter) % len(key)
        encrypted_text += key[shifted_index]
        
    return encrypted_text

def decrypt(text, key) -> str:
    alphabet = string.ascii_lowercase
    decrypted_text = ""
    shifter = 5 % len(key)
    for char in text:
        index = key.find(char) if char.isalpha() else key.find(char.lower())
        shifted_index = (index - shifter) % len(key)
        decrypted_text += alphabet[shifted_index]
        
    return decrypted_text