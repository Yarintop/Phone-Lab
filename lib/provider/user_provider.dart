import 'package:flutter/cupertino.dart';
import 'package:myapp/models/user.dart';

class UserProvider extends ChangeNotifier {
  bool addingUser; // Flag to handle async adding user

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

  User findUserByEmail(String email) {
    return users.firstWhere((user) => user.email == email);
  }
}
