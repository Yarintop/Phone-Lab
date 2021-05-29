import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/Models/user.dart';

import '../../MainProvider.dart';

class UserController extends StatefulWidget {
  createState() => UserState();
}

class UserState extends State<UserController> {
  String _username = "";
  String _email = "";
  String _selectedRole = "Player";
  String _avatar = "";

  void _addUser(MainModel mainModel) {
    User user = new User();
    user.name = _username;
    user.email = _email;
    user.role = _selectedRole;
    user.avatar = _avatar;
    mainModel.addUser(user);
  }

  void _selectRole(String? role) {
    setState(() {
      if (role == null)
        _selectedRole = "Player";
      else
        _selectedRole = role;
    });
  }

  DropdownButton getDropDownButton() {
    return DropdownButton<String>(
        value: _selectedRole,
        onChanged: _selectRole,

        // focusNode: _secondNode,
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
                  onChanged: (username) => {_username = username},
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
              onChanged: (email) => {_email = email},
            ),
            width: 200,
          ),
          Container(
            margin: EdgeInsets.all(8.0),
            child: TextField(
              decoration: InputDecoration(
                  fillColor: Colors.white,
                  filled: true,
                  hintText: "Avatar",
                  contentPadding: EdgeInsets.all(4.0)),
              onChanged: (txt) => {_avatar = txt},
              onEditingComplete: () => _addUser(mainModel),
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
