import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_native_splash/flutter_native_splash.dart';
import 'package:passgen/linear.dart';
import 'package:passgen/repeated.dart';
import 'package:passgen/truncated.dart';

import 'package:passgen/util/passworddb.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
  await Passworddb.init();

  await Future.delayed(const Duration(milliseconds: 500));
  FlutterNativeSplash.remove();

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'PassGen',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.red),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'PassGen'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  Random random = Random();

  final String _allChars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#%^&*()_+';
  final String _noSpecialChars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

  String collection = "";

  bool _withoutSpecialCharacters = false;
  bool _isRepeating = false;
  List<String> _storedPass = [];

  final TextEditingController _passwordLength = TextEditingController();

  @override
  void initState() {
    super.initState();
    _loadPasswords();
  }

  @override
  void dispose() {
    _passwordLength.dispose();
    super.dispose();
  }

  Future<void> _loadPasswords() async {
    List<String> passwords = await Passworddb.getPasswords();
    setState(() {
      _storedPass = passwords;
    });
  }

  void _snackBarError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: Colors.red,
      ),
    );
  }

  void _resetConditions() {
    _withoutSpecialCharacters = false;
    _isRepeating = false;
  }

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: SizedBox(
        height: double.infinity,
        width: double.infinity,
        child: Column(
          children: <Widget>[
            const Text('Your Passwords'),
            Expanded(
              child: ListView.builder(
                itemCount: _storedPass.length,
                itemBuilder: (context, index) {
                  final item = _storedPass[index];
                  return Dismissible(
                    key: Key(item),
                    onDismissed: (direction) {
                      Passworddb.deletePassword(item);
                      setState(() {
                        _storedPass.removeAt(index);
                      });
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Text('Removed: $item'),
                        ),
                      );
                    },
                    background: Container(
                      color: Colors.red,
                    ),
                    child: ListTile(
                      onTap: () {
                        Clipboard.setData(ClipboardData(text: item));
                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(
                            content: Text('Copied: $item'),
                          ),
                        );
                      },
                      title: Text(item),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => {
          showDialog(
            context: context,
            builder: (context) => AlertDialog(
              title: const Text('Generate Password'),
              content: StatefulBuilder(
                builder: (context, setStateDialog) {
                  return SizedBox(
                    height: 175,
                    child: Column(
                      children: <Widget>[
                        TextField(
                          controller: _passwordLength,
                          keyboardType: TextInputType.number,
                          inputFormatters: [
                            FilteringTextInputFormatter.digitsOnly,
                          ],
                          decoration: const InputDecoration(
                            labelText: 'Password length',
                          ),
                        ),
                        Row(
                          children: <Widget>[
                            Switch.adaptive(
                              value: _isRepeating,
                              onChanged: (bool value) {
                                setStateDialog(() {
                                  _isRepeating = value;
                                });
                              },
                            ),
                            const Text('Repeating'),
                          ],
                        ),
                        Row(
                          children: <Widget>[
                            Switch.adaptive(
                              value: _withoutSpecialCharacters,
                              onChanged: (bool value) {
                                setStateDialog(() {
                                  _withoutSpecialCharacters = value;
                                });
                              },
                            ),
                            const Text('Without Special Characters'),
                          ],
                        )
                      ],
                    ),
                  );
                },
              ),
              actions: [
                TextButton(
                  child: const Text('Cancel'),
                  onPressed: () {
                    _resetConditions();
                    Navigator.of(context).pop();
                  },
                ),
                TextButton(
                  child: const Text('Generate'),
                  onPressed: () {
                    _generate();
                    _resetConditions();
                    Navigator.of(context).pop();
                  },
                ),
              ],
            ),
          ),
        },
        tooltip: 'Passgen',
        child: const Icon(
          Icons.bolt,
          color: Colors.black,
        ),
      ),
    );
  }
}