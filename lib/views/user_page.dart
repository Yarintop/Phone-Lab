import 'package:flutter/material.dart';
import 'package:myapp/widgets/user_form/user_display.dart';
import 'package:myapp/widgets/user_form/user_form.dart';

class UsersPage extends StatefulWidget {
  @override
  _UsersPageState createState() => _UsersPageState();
}

class _UsersPageState extends State<UsersPage> {
  bool addUserMode = false;
  String btnText = "Create New User";

  void toggleMode() {
    setState(() {
      if (addUserMode)
        btnText = "Create New User";
      else
        btnText = "Show All Users";
      addUserMode = !addUserMode;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        SizedBox(height: 32),
        Text("User Control Area"),
        SizedBox(height: 32),
        ElevatedButton(onPressed: toggleMode, child: Text(btnText)),
        SizedBox(height: 16),
        addUserMode ? UserForm() : UserDisplay(),
      ],
    );
  }
}
