# passgen

A Flutter project. This Projects uses Linear Permutation, Permutation with Repeating elements, and Permutation with truncated elements  

Obviously made by using:

![Flutter](https://img.shields.io/badge/Flutter-%2302569B.svg?style=flat&logo=Flutter&logoColor=white)
![Dart](https://img.shields.io/badge/dart-%230175C2.svg?style=flat&logo=dart&logoColor=white)

## Linear Permutation

Linear permutation refers to the number of ordered arrangement of objects in a line.

## Permutation with repeating elements

Permutations with repeated elements refer to the arrangements of a set of items where some items may be indistinguishable from each other. This is different from standard permutations, where all items are unique.

## Permutation with truncated elements

Permutations with truncated elements involve selecting and arranging a subset of items from a larger set. This is typically referred to as "permutations of a subset" or "partial permutations."

# Features

This app only consist one page with a floating action button and a dialog pop-up for password generation.

## Major Features

This features relates to the permutation topics

### Code for generating a linear permutation
~~~dart
String linearPermutation(String input) {
  List<String> chars = input.split('');
  Random random = Random();

  for (int i = 0; i < chars.length; i++) {
    int randomIndex = random.nextInt(chars.length);
    String temp = chars[i];
    chars[i] = chars[randomIndex];
    chars[randomIndex] = temp;
  }

  return chars.join('');

}
~~~

### What does it do?
The linearPermutation function in Dart takes an input string, splits it into a list of individual characters, and then shuffles these characters randomly using a loop that swaps each character with another randomly chosen character from the list. It achieves this by generating random indices within the bounds of the character list and performing the swaps. Finally, the shuffled characters are joined back into a single string, which is returned as the output. This function produces a random permutation of the input string each time it is called.

---

### Code for generating permutation with repeated elements
~~~dart
String repeteadPermutation(String input, int targetLen) {

  String result = '';
  Random random = Random();

  for (int i = 0; i < targetLen; i++) {
    int randomIndex = random.nextInt(input.length);
    result += input[randomIndex];
  }

  return result;
}
~~~

### What it does

The repeatedPermutation function in Dart generates a new string of a specified length (targetLen) by randomly selecting characters from the given input string. It starts with an empty result string and uses a loop to iterate targetLen times. In each iteration, it generates a random index within the bounds of the input string and appends the character at that index to the result. This means that characters can be repeated in the output string since each character is chosen independently. Finally, the function returns the constructed result string, which can vary in length and character composition each time it is called.

---

### Code for generating permutation with truncated elements
~~~dart
String truncatedPermutation(String input, int targetLen) {
  String result = '';
  Random random = Random();

  while (result.length < targetLen) {
    int randomIndex = random.nextInt(input.length);

    if (result.contains(input[randomIndex])) {
      continue;
    }

    result += input[randomIndex];
  }

  return result;

}
~~~

### What it does
The truncatedPermutation function in Dart creates a new string of a specified length (targetLen) by randomly selecting unique characters from the input string. It initializes an empty result string and uses a while loop to continue adding characters until the result reaches the desired length. In each iteration, it generates a random index within the bounds of the input string and checks if the character at that index is already present in the result. If the character is a duplicate, it skips to the next iteration using continue. This ensures that all characters in the output string are unique. Once the result reaches the specified length, the function returns the constructed string, which will vary each time it is called, provided that the input string contains enough unique characters.

---

### Code for what to output with conditions of the user
~~~dart
void _generate() {
  int? targetLen = int.tryParse(_passwordLength.text);
  String chars = _withoutSpecialCharacters ? _noSpecialChars : _allChars;
  String password = '';

  if (targetLen == null) {
    _snackBarError('Password length is required');
    return;
  }

  if (targetLen > chars.length) {
    if (_isRepeating) {
      password = repeteadPermutation(chars, targetLen);
    } else {
      _snackBarError('Error: Invalid length for desired conditions');
      return;
    }
  }

  if (targetLen < chars.length) {
    if (_isRepeating) {
      int truncatedLen = (targetLen ~/ 2) + random.nextInt(targetLen -  (targetLen ~/ 2));
      password = repeteadPermutation(truncatedPermutation(chars, truncatedLen), targetLen);
    } else {
      password = truncatedPermutation(chars, targetLen);
    }
  }

  if (targetLen == chars.length) {
    if (_isRepeating) {
      password = repeteadPermutation(chars, targetLen);
    } else {
      password = linearPermutation(chars);
    }
  }

  Passworddb.savePassword(password);
  setState(() {
    _storedPass.add(password);
  });
}
~~~ 

### What it does


The _generate function creates a password based on user-defined criteria, specifically its length and character composition. It first retrieves the desired password length from a text field and selects the character set depending on whether special characters are allowed. The function then checks the specified length against the available characters: if the length exceeds the character set, it generates a password using repeatedPermutation if repetition is allowed; otherwise, it shows an error. If the length is less, it may use truncatedPermutation or repeatedPermutation based on the repetition option. If the specified length matches the character set size, it generates a password using either repeatedPermutation or linearPermutation. Finally, the generated password is saved and added to a list of stored passwords, updating the UI accordingly.

---

## Minor feature

This section of features is not relating to the permutation topics

### Code for saving ata without SQL or Database servers
~~~dart
import 'package:shared_preferences/shared_preferences.dart';

class Passworddb {
  static SharedPreferences? _prefs;

  static Future<void> init() async {
    _prefs = await SharedPreferences.getInstance();
  }

  static Future<void> savePassword(String password) async {
    if (_prefs == null) {
      await init();
    }
    final List<String> passwords = _prefs!.getStringList('passwords') ?? [];
    passwords.add(password);
    await _prefs!.setStringList('passwords', passwords);
  }

  static Future<List<String>> getPasswords() async {
    if (_prefs == null) {
      await init();
    }
    return _prefs!.getStringList('passwords') ?? [];
  }

  static Future<void> deletePassword(String password) async {
    if (_prefs == null) {
      await init();
    }
    final List<String> passwords = _prefs!.getStringList('passwords') ?? [];
    passwords.remove(password);
    await _prefs!.setStringList('passwords', passwords);
  }

}
~~~

### What it does
The Passworddb class serves as a utility for managing password storage using the SharedPreferences package in Dart. It maintains a static instance of SharedPreferences and provides methods for initializing this instance, saving passwords, retrieving them, and deleting specific passwords. The init method asynchronously initializes the SharedPreferences instance, ensuring that it is ready for use. The savePassword method adds a new password to an existing list (or creates a new one if none exist) and updates the stored list, while getPasswords retrieves the current list of stored passwords, returning an empty list if none are found.

The deletePassword method allows for the removal of a specific password from the stored list. Each method first checks if the SharedPreferences instance is initialized, calling init if necessary. This design ensures that the passwords are persistently stored and can be accessed or modified even after the application is closed or restarted, providing a simple interface for password management.