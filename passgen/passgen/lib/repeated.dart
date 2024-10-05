import 'dart:math';

// Repeat the input string to the desired length
// by randomly selecting characters from the input
// targetLen should always be more than the input.length
String repeteadPermutation(String input, int targetLen) {

  String result = '';
  Random random = Random();

  for (int i = 0; i < targetLen; i++) {
    int randomIndex = random.nextInt(input.length);
    result += input[randomIndex];
  }

  return result;
}