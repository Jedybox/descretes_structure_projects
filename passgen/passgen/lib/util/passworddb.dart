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