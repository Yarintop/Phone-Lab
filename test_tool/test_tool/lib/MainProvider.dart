import 'package:flutter/cupertino.dart';
import "./Models/user.dart";
import "./Models/operation.dart";
import "./Models/item.dart";

class MainModel extends ChangeNotifier {
  final List<User> _users = [];
  final List<Item> _items = [];
  final List<Operation> _operations = [];

  List<User> get users => _users;
  List<Item> get items => _items;
  List<Operation> get operations => _operations;

  void addItem(Item item) {
    _items.add(item);
    notifyListeners();
  }

  void updateItem(Item item) {
    int index = _items.indexWhere((element) => element.id == item.id);
    if (index == -1) {
      print("Item not found");
      return;
    }
    _items.removeAt(index);
    _items.add(item);
    notifyListeners();
  }

  void removeItem(Item item) {
    int index = _items.indexWhere((element) => element.id == item.id);
    if (index == -1) {
      print("Item not found");
      return;
    }
    _items.removeAt(index);
    notifyListeners();
  }

  void addOperation(Operation operation) {
    _operations.add(operation);
    notifyListeners();
  }

  void addUser(User user) {
    _users.add(user);
    notifyListeners();
  }

  void removeAllUsers() {
    _users.clear();
    notifyListeners();
  }

  void removeAllOperations() {
    _operations.clear();
    notifyListeners();
  }

  void removeAllItems() {
    _items.clear();
    notifyListeners();
  }
}
