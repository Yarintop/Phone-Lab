import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:myapp/models/user.dart';

class UserProvider extends ChangeNotifier {
  bool addingUser; // Flag to handle async adding user
  User _loggedInUser; // Loggedin user, if not logged in, it would be null

  User get loggedInUser => _loggedInUser;

  //TEMP
  final List<User> users = [
    User.fromParams("2021b.noam.levi1", "Player@Player.com", "thePlayer", "Some Site", "Player"),
    User.fromParams("2021b.noam.levi1", "Manager@Manager.com", "theManager", "Some Site", "Manager"),
    User.fromParams("2021b.noam.levi1", "Admin@Admin.com", "theAdmin", "Some Site", "Admin"),
  ];
  //

  Future<bool> addUser(User user) async {
    //TODO - make an API call
    this.users.add(user);

    // TODO - add logic to validate the info
    notifyListeners();
    return true;
  }

  Future<void> loadAllUsers() async {
    // TODO - implement API call
    //wait
    notifyListeners();
  }

  Future<bool> login({User user, String email}) async {
    User test;
    if (user != null)
      test = users.firstWhere((u) => user == u);
    else if (email != null) test = findUserByEmail(email);

    // TODO - implement API call
    if ((user != null && user != test) || (user == null && test.email != email)) return false;
    _loggedInUser = test;
    notifyListeners();
    return true;
  }

  User findUserByEmail(String email) {
    return users.firstWhere((user) => user.email == email);
  }

  /// Checks if there is a logged in user, if so, returns [askedPage] otherwise, returns [defaultPage]
  Widget getPageBasedOnLogin(Widget askedPage, Widget defaultPage) {
    if (_loggedInUser == null) return defaultPage;
    return askedPage;
  }
}
