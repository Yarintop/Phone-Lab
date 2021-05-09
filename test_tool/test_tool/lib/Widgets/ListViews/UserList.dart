import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/Models/user.dart';

import '../../MainProvider.dart';

class UsersView extends StatelessWidget {
  String getUserDetail(User user) {
    String role = user.role;
    String email = user.email;
    // String avatar = user.avatar;
    String name = user.name;
    return name + "|" + email + "(" + role + ")";
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MainModel>(
      builder: (context, mainModel, child) {
        return Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text("User List:"),
            ...mainModel.users.map((user) => Text(getUserDetail(user))),
          ],
        );
      },
    );
  }
}
