from tkinter import messagebox
from circular import is_same_permutation
import csv

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

def get_saved_key() -> list:
    saved_keys = []
    try:
        with open("key.csv", "r") as file:
            keys = file.read().splitlines()
            saved_keys = [key for key in keys]
            return saved_keys
    except FileNotFoundError:
        return []