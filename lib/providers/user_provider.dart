import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:myapp/constants/project_specific.dart';
import 'package:myapp/constants/user_specific.dart';
import 'package:myapp/models/user.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_dotenv/flutter_dotenv.dart';

class UserProvider extends ChangeNotifier {
  final baseUrl = "http://${env["HOST"]}:${env["PORT"]}/$BASE_API";

  bool addingUser; // Flag to handle async adding user
  User _loggedInUser; // Loggedin user, if not logged in, it would be null
  User get loggedInUser => _loggedInUser;

  List<User> _users = [];
  List<User> get users => _users;

  //TEMP
  // final List<User> users = [
  //   User.fromParams("2021b.noam.levi1", "Player@Player.com", "thePlayer", "Some Site", "Player"),
  //   User.fromParams("2021b.noam.levi1", "Manager@Manager.com", "theManager", "Some Site", "Manager"),
  //   User.fromParams("2021b.noam.levi1", "Admin@Admin.com", "theAdmin", "Some Site", "Admin"),
  // ];
  //

  Future<bool> addUser(User user) async {
    if (_users.contains(user)) return false;
    bool resStatus = false; //TODO - convert to error codes
    final res = await http.post(
      Uri.parse("$baseUrl/$USER_API"),
      body: jsonEncode(
        {
          "email": user.email,
          "role": user.role,
          "username": user.username,
          "avatar": user.avatar,
        },
      ),
      headers: {
        "Content-Type": "application/json",
      },
    );
    if (res.statusCode == 200) {
      _users.add(user);
      resStatus = true;
    } else
      notifyListeners();

    // TODO - add logic to validate the info
    notifyListeners();
    return resStatus;
  }

  Future<void> loadAllUsers() async {
    if (loggedInUser == null) return;
    //save current role
    String tempRole;
    if (loggedInUser.role != Role.ADMIN.value) {
      tempRole = loggedInUser.role;
      await updateRole(loggedInUser, Role.ADMIN.value);
    }

    //get all users
    final res = await http.get(Uri.parse("$baseUrl/$ADMIN_API/$USER_API/${loggedInUser.space}/${loggedInUser.email}"));
    if (res.statusCode != 200) {
      return;
    }
    Iterable tmp = jsonDecode(res.body);
    List<User> users = tmp.map<User>((j) => User.fromJSON(j)).toList();
    _users.clear();
    _users.addAll(users);

    // Restore role if it was changed
    if (tempRole != null) {
      await updateRole(loggedInUser, tempRole);
      User tempUser = users.firstWhere((u) => u == loggedInUser);
      if (tempUser != null) tempUser.role = tempRole;
    }
    notifyListeners();
  }

  Future<bool> updateRole(User user, String role) async {
    // But the connection is stable: https://i.imgur.com/GZcvM5N.png
    user.role = role;
    final res = await http.put(Uri.parse("$baseUrl/$USER_API/$SPACE/${user.email}"),
        body: jsonEncode(user), headers: {"Content-Type": "application/json"});
    return res.statusCode == 200;
  }

  Future<bool> login({String email}) async {
    // User test;
    // if (user != null)
    // test = users.firstWhere((u) => user == u);
    // else if (email != null) test = findUserByEmail(email);

    final res = await http.get(
      Uri.parse("$baseUrl/$USER_API/login/$SPACE/$email"),
    );
    if (res.statusCode == 200) {
      _loggedInUser = User.fromJSON(jsonDecode(res.body));
      loadAllUsers();
    } else {
      _loggedInUser = null;
    }
    notifyListeners();
    return res.statusCode == 200;
  }

  User findUserByEmail(String email) {
    return users.firstWhere((user) => user.email == email);
  }

  /// Checks if there is a logged in user, if so, returns [askedPage] otherwise, returns [defaultPage]
  Widget getPageBasedOnLogin(Widget askedPage, Widget defaultPage) {
    if (_loggedInUser == null) return defaultPage;
    return askedPage;
  }

  Future<void> pullData() async {
    await loadAllUsers();
  }
}
