import 'package:flutter/material.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/provider/user_provider.dart';
import 'package:provider/provider.dart';

class UserForm extends StatefulWidget {
  @override
  _UserFormState createState() => _UserFormState();
}

class _UserFormState extends State<UserForm> {
  String _selectedRole = "Player";

  String _currentUsername = "";
  String _currentEmail = "";
  String _currentAvatar = "";

  TextEditingController usernameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController avatarController = TextEditingController();

  final String space = "2021b.noam.levi1";

  // TextEditingController universalTextController = TextEditingController();

  void addUser(UserProvider provider) {
    User user = User.fromParams(space, _currentEmail, _currentUsername, _currentAvatar, _selectedRole);
    provider.addUser(user).then((success) {
      if (success) {
        //TODO - add confirmation alert
        usernameController.clear();
        emailController.clear();
        avatarController.clear();
      } else {
        //TODO - show error alert
      }
    });
  }

  void _selectRole(String role) {
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

  Widget createTextField(String hint, TextEditingController controller, Function callback) {
    return Padding(
      padding: EdgeInsets.all(4.0),
      child: TextField(
        controller: controller,
        decoration: InputDecoration(
          hintText: hint,
          contentPadding: EdgeInsets.all(4.0),
          fillColor: Colors.white,
          filled: true,
        ),
        onChanged: callback,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(
      builder: (context, provider, child) => Padding(
        padding: const EdgeInsets.all(32.0),
        child: Container(
          padding: EdgeInsets.all(32.0),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(5)),
            border: Border.all(color: Colors.black, width: 1),
            color: Colors.blueGrey[50],
          ),
          child: SizedBox(
            width: 250,
            child: Column(
              children: [
                Text("Title"), //TODO make it better later
                createTextField("Username", usernameController, (v) => {_currentUsername = v}),
                createTextField("Email", emailController, (v) => {_currentEmail = v}),
                createTextField("Avatar", avatarController, (v) => {_currentAvatar = v}),
                Container(child: getDropDownButton()),
                ElevatedButton(onPressed: () => addUser(provider), child: Text("Create")),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
