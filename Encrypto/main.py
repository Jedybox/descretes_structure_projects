import tkinter as tk
from tkinter import messagebox
from tkinter import PhotoImage
from circular import generate_circular_permutation, decrypt, encrypt
from utils import save_key, get_saved_key

button_style = {
    "bg": "#4CAF50",  # Background color
    "fg": "white",    # Foreground (text) color
    "font": ("Helvetica", 10, "bold"),  # Font style
    "bd": 3,          # Border width
    "relief": tk.RAISED  # Button relief style
}

def show_encrypt_page():
    root.withdraw()  # Hide the main window
    encrypt_window = tk.Toplevel(root)
    encrypt_window.geometry("300x320")
    encrypt_window.title("Encrypt Page")
    encrypt_window.resizable(False, False)  # Disable window resizing
    encrypt_window.protocol("WM_DELETE_WINDOW", lambda: close_page(encrypt_window))

    tk.Label(encrypt_window, text="Enter text to encrypt:").pack(pady=2)
    text_entry = tk.Entry(encrypt_window, width=40)
    text_entry.pack(pady=5)

    tk.Label(encrypt_window, text="Generated key:").pack(pady=2)
    key_entry = tk.Entry(encrypt_window, width=40)
    key_entry.pack(pady=5)
    
    tk.Label(encrypt_window, text="Encrypted text").pack(pady=2)
    encryted_text = tk.Entry(encrypt_window, width=40)
    encryted_text.pack(pady=5)

    def encrypt_and_display() -> None:
        if not text_entry.get():
            messagebox.showerror("Error", "Please enter text to encrypt")
            return
        
        text = text_entry.get()
        key = generate_circular_permutation()
        newtext = encrypt(text, key)
        
        encryted_text.delete(0, tk.END)
        encryted_text.insert(0, newtext)
        
        key_entry.delete(0, tk.END)
        key_entry.insert(0, key)
    
    def copy_encrypted_text():
        encrypted_text = encryted_text.get()
        if not encrypted_text:
            messagebox.showerror("Error", "No text to copy")
            return
        root.clipboard_clear()
        root.clipboard_append(encrypted_text)
        messagebox.showinfo("Copied", "Encrypted text copied to clipboard")    

    button_frame = tk.Frame(encrypt_window)
    button_frame.pack(pady=10)

    tk.Button(button_frame, text="Encrypt Text", command= encrypt_and_display, **button_style).pack(side=tk.LEFT, padx=5)
    tk.Button(button_frame, text="Save Key", command= lambda: save_key(key_entry.get()), **button_style).pack(side=tk.LEFT, padx=5)
    tk.Button(button_frame, text="Copy Encrypted", command=copy_encrypted_text, **button_style).pack(side=tk.LEFT, padx=5)

def show_decrypt_page():
    root.withdraw()  # Hide the main window
    decrypt_window = tk.Toplevel(root)
    decrypt_window.geometry("300x300")
    decrypt_window.title("Decrypt Page")
    decrypt_window.resizable(False, False)  # Disable window resizing
    decrypt_window.protocol("WM_DELETE_WINDOW", lambda: close_page(decrypt_window))
    
    tk.Label(decrypt_window, text="Enter text to decrypt:").pack(pady=2)
    text_entry = tk.Entry(decrypt_window, width=40)
    text_entry.pack(pady=5)
    
    tk.Label(decrypt_window, text="Decrpted text:").pack(pady=2)
    decrypted_text = tk.Entry(decrypt_window, width=40)
    decrypted_text.pack(pady=5)
    
    tk.Label(decrypt_window, text="Select key:").pack(pady=2)
    key_var = tk.StringVar(decrypt_window)
    key_options = [key for key in get_saved_key()]  # Replace with actual keys
    key_menu = tk.OptionMenu(decrypt_window, key_var, *key_options)
    key_menu.config(width=30)  # Adjust the width as needed
    key_menu.pack(pady=5)

    def decrypt_and_display() -> None:
        if not text_entry.get():
            messagebox.showerror("Error", "Please enter text to decrypt")
            return
        if not key_var.get():
            messagebox.showerror("Error", "Please select a key")
            return
        
        text = text_entry.get()
        key = key_var.get()
        newtext = decrypt(text, key)
        
        decrypted_text.delete(0, tk.END)
        decrypted_text.insert(0, newtext)

    tk.Button(decrypt_window, text="Decrypt Text", command=decrypt_and_display, **button_style).pack(pady=10)
    
def close_page(window):
    root.deiconify() 
    window.destroy()

def exit_app():
    root.destroy()

root = tk.Tk()
root.geometry("300x300")
root.title("Main Menu")
root.resizable(False, False)  # Disable window resizing

# Load the image
logo = PhotoImage(file="assets/cryptographyLogo.png").subsample(3, 3)  # Adjust subsample to resize image

# Create a label to display the image
logo_label = tk.Label(root, image=logo)
logo_label.pack(pady=10)

button_frame = tk.Frame(root)
button_frame.pack(pady=10)

encrypt_button = tk.Button(button_frame, text="Encrypt", command=show_encrypt_page, **button_style)
encrypt_button.pack(side=tk.LEFT, padx=5)

decrypt_button = tk.Button(button_frame, text="Decrypt", command=show_decrypt_page, **button_style)
decrypt_button.pack(side=tk.LEFT, padx=5)

exit_button = tk.Button(button_frame, text="Exit", command=exit_app, **button_style)
exit_button.pack(side=tk.LEFT, padx=5)

root.mainloop()