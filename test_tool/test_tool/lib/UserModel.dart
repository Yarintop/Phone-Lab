import 'package:flutter/cupertino.dart';
import "./Models/user.dart";

class UserModel extends ChangeNotifier {
  final List<User> _users = [];

  List<User> get users => _users;

  void add(User user) {
    _users.add(user);
    notifyListeners();
  }

  void removeAll() {
    _users.clear();
    notifyListeners();
  }
}
