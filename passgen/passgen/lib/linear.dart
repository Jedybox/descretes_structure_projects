import 'dart:math';

// Linear permutation of the input string
// by randomly shuffling the characters
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