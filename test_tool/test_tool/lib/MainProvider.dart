import 'dart:convert';

import 'package:flutter/cupertino.dart';
import "./Models/user.dart";
import "./Models/operation.dart";
import "./Models/item.dart";
import 'package:http/http.dart' as http;

class MainModel extends ChangeNotifier {
  final BASE_URL = "http://localhost:8010/twins";
  final USER_API = "users";
  final ITEM_API = "items";
  final OPERATION_API = "operations";
  final ADMIN_API = "admin";

  final List<User> _users = [];
  final List<Item> _items = [];
  final List<Operation> _operations = [];

  List<User> get users => _users;
  List<Item> get items => _items;
  List<Operation> get operations => _operations;

  MainModel() {
    //TODO: Add logic so we could get all users without hard-coding an admin user
    initAll("2021b.noam.levi1", "admin@admin.com", "manager@manager.com");
  }

  void addItem(Item item) async {
    final res = await http.post(
      //TODO: get the logged in user instead
      Uri.parse("$BASE_URL/$ITEM_API/${item.user.space}/${item.user.email}"),
      body: jsonEncode({
        "type": item.type,
        "name": item.name,
        "active": item.active,
        "createdTimestamp": item.createdTimestamp,
        "createdBy": {
          "userId": {"space": item.user.space, "email": item.user.email}
        },
        "itemAttributes": item.attributes,
        "location": {
          "lat": item.lat,
          "lng": item.lng,
        }
      }),
      headers: {
        "Content-Type": "application/json",
      },
    );
    if (res.statusCode == 200) {
      item.id = jsonDecode(res.body)["itemId"]["id"];
      item.space = jsonDecode(res.body)["itemId"]["space"];
      _items.add(item);
    } else
      print("ERROR: ${res.body}"); // TODO: Add message error
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

  void addOperation(Operation operation) async {
    final res = await http.post(
      Uri.parse("$BASE_URL/$OPERATION_API"),
      body: jsonEncode({
        "type": operation.type,
        "item": {
          "itemId": {"space": operation.item.space, "id": operation.item.id}
        },
        "createdTimestamp": operation.createdTimestamp,
        "invokedBy": {
          "userId": {
            "space": operation.user.space,
            "email": operation.user.email
          }
        },
        "operationAttributes": operation.attributes,
      }),
      headers: {
        "Content-Type": "application/json",
      },
    );
    if (res.statusCode == 200)
      _operations.add(operation);
    else
      print("ERROR: ${res.body}"); // TODO: Add message error
    notifyListeners();
  }

  Future<void> addUser(User user) async {
    if (_users.contains(user)) return;
    final res = await http.post(
      Uri.parse("$BASE_URL/$USER_API"),
      body: jsonEncode({
        "email": user.email,
        "role": user.role,
        "username": user.name,
        "avatar": user.avatar
      }),
      headers: {
        "Content-Type": "application/json",
      },
    );
    if (res.statusCode == 200)
      _users.add(user);
    else
      print("ERROR: ${res.body}"); // TODO: Add message error
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

  User _convertMapToUser(Map raw) {
    User user = new User();
    user.avatar = raw["avatar"];
    user.email = raw["email"];
    user.role = raw["role"];
    user.name = raw["username"];
    return user;
  }

  Future<bool> findUser(String space, String email) async {
    final res =
        await http.get(Uri.parse("$BASE_URL/$USER_API/login/$space/$email"));
    return res.statusCode == 200;
  }

  void initAll(space, adminEmail, managerEmail) async {
    await getAllUsers(space, adminEmail);
    await getAllItems(space, managerEmail);
    await getAllOperations(space, adminEmail);
  }

  Future<void> getAllItems(String managerSpace, String managerEmail) async {
    print("ITEMS");
    bool userExists = await findUser(managerSpace, managerEmail);
    if (!userExists)
      await addUser(
          new User.fromParams("temp", managerEmail, "MANAGER", "avatar"));

    final res = await http
        .get(Uri.parse("$BASE_URL/$ITEM_API/$managerSpace/$managerEmail"));
    if (res.statusCode == 200) {
      Iterable tmp = jsonDecode(res.body);
      List<Item> items =
          tmp.map<Item>((j) => Item.fromJson(inflateJson(j))).toList();
      _items.addAll(items);
      notifyListeners();
    }
  }

  Future<void> getAllUsers(String adminSpace, String adminEmail) async {
    print("USERS");
    bool userExists = await findUser(adminSpace, adminEmail);
    if (!userExists)
      await addUser(new User.fromParams("temp", adminEmail, "ADMIN", "avatar"));

    final res = await http.get(
        Uri.parse("$BASE_URL/$ADMIN_API/$USER_API/$adminSpace/$adminEmail"));
    if (res.statusCode == 200) {
      Iterable tmp = jsonDecode(res.body);

      List<User> users = tmp.map<User>((j) => User.fromJson(j)).toList();
      _users.addAll(users);
      notifyListeners();
    }
  }

  Future<void> getAllOperations(String adminSpace, String adminEmail) async {
    print("OPERATIONS");
    bool userExists = await findUser(
        adminSpace, adminEmail); // Validate that the admin exists in the system

    if (!userExists)
      await addUser(new User.fromParams("temp", adminEmail, "admin", "avatar"));

    final res = await http.get(Uri.parse(
        "$BASE_URL/$ADMIN_API/$OPERATION_API/$adminSpace/$adminEmail"));
    if (res.statusCode == 200) {
      Iterable tmp = jsonDecode(res.body);
      List<Operation> operations = tmp
          .map<Operation>((j) => Operation.fromJson(inflateJson(j)))
          .toList();
      _operations.addAll(operations); // Update the list view of operations
      notifyListeners(); // Notify widgets to read their state variables
    }
  }

  Map<String, dynamic> inflateJson(Map<String, dynamic> json) {
    if (json.containsKey("createdBy") || json.containsKey("invokedBy")) {
      String key = json.containsKey("createdBy") ? "createdBy" : "invokedBy";
      var user = json[key];
      User savedUser = _users.firstWhere((u) {
        return u.email == user["userId"]["email"] &&
            u.space == user["userId"]["space"];
      }, orElse: () => User());
      if (savedUser.email == "")
        print("SIZE: ${_users.length} FUCK");
      else {
        user["role"] = savedUser.role;
        user["username"] = savedUser.name;
        user["avatar"] = savedUser.avatar;
      }
      json[key] = user;
      //USER STUFF
    }

    if (json.containsKey("item")) {
      var item = json["item"];
      Item savedItem = _items.firstWhere(
          (i) =>
              i.id == item["itemId"]["id"] &&
              i.space == item["itemId"]["space"],
          orElse: () => Item());
      if (savedItem.id == "")
        print("SIZE: ${_items.length} FUCK");
      else {
        item["type"] = savedItem.type;
        item["name"] = savedItem.name;
        item["active"] = savedItem.active;
        item["createdTimestamp"] = savedItem.createdTimestamp.toString();
        item["createdBy"] = jsonDecode(
            jsonEncode(savedItem.user)); //Black magic to make things work
        item["location"] = {"lat": savedItem.lat, "lng": savedItem.lng};
        item["itemAttributes"] = savedItem.attributes;
      }
      json["item"] = item;
      //ITEM STUFF"
    }

    return json;
  }
}
