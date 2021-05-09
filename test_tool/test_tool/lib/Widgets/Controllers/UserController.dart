import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/Models/user.dart';

import '../../MainProvider.dart';

class UserController extends StatefulWidget {
  createState() => UserState();
}

class UserState extends State<UserController> {
  String inputText = "";
  String emailText = "";
  String selectedRole = "Player";
  void _updateText(String newText) {
    inputText = newText;
  }

  void _updateEmail(String newText) {
    emailText = newText;
  }

  void _addUser(MainModel mainModel) {
    User user = new User();
    user.name = inputText;
    user.email = emailText;
    user.role = selectedRole;
    mainModel.addUser(user);
  }

  void _selectRole(String? role) {
    setState(() {
      if (role == null)
        selectedRole = "Player";
      else
        selectedRole = role;
    });
  }

  DropdownButton getDropDownButton() {
    return DropdownButton<String>(
        value: selectedRole,
        icon: const Icon(Icons.arrow_downward_rounded),
        onChanged: _selectRole,
        items: ["Player", "Manager", "Admin"]
            .map((e) => DropdownMenuItem(
                  child: Text(e),
                  value: e,
                ))
            .toList());
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MainModel>(builder: (context, mainModel, child) {
      return Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                margin: EdgeInsets.all(8.0),
                child: TextField(
                  decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "Username",
                      contentPadding: EdgeInsets.all(4.0)),
                  onChanged: _updateText,
                ),
                width: 100,
              ),
              Container(
                width: 100,
                child: getDropDownButton(),
              ),
            ],
          ),
          Container(
            margin: EdgeInsets.all(8.0),
            child: TextField(
              decoration: InputDecoration(
                  fillColor: Colors.white,
                  filled: true,
                  hintText: "Email",
                  contentPadding: EdgeInsets.all(4.0)),
              onChanged: _updateEmail,
            ),
            width: 200,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              Container(
                padding: EdgeInsets.all(4.0),
                child: ElevatedButton(
                    onPressed: () => _addUser(mainModel), child: Text("Add")),
              ),
              Container(
                padding: EdgeInsets.all(4.0),
                child: ElevatedButton(
                    style: ElevatedButton.styleFrom(primary: Colors.red),
                    onPressed: mainModel.removeAllUsers,
                    child: Text("Remove All")),
              ),
            ],
          ),
        ],
      );
    });
  }
}
