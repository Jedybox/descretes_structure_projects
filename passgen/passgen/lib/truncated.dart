import 'dart:math';

// Truncate the input string to the desired length
// by randomly selecting characters from the input
// targetLen should always be less than the input.length
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