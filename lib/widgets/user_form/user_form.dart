import 'package:flutter/material.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:myapp/widgets/form_widgets/input_field.dart';
import 'package:provider/provider.dart';

class UserForm extends StatefulWidget {
  @override
  _UserFormState createState() => _UserFormState();
}

class _UserFormState extends State<UserForm> {
  String _selectedRole = "Player";

  TextEditingController usernameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController avatarController = TextEditingController();

  // TODO - Find a better way to get default values, env file / const file
  final String space = "2021b.noam.levi1";
  final List<String> roles = ["Player", "Manager", "Admin"];
  // TextEditingController universalTextController = TextEditingController();

  void addUser(UserProvider provider) {
    // This function would try to add a user using the provider which will invoke an async api call.
    User user = User.fromParams(
      space,
      emailController.text,
      usernameController.text,
      avatarController.text,
      _selectedRole,
    );

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
                InputField(hint: "Username", controller: usernameController),
                InputField(hint: "Email", controller: emailController),
                InputField(hint: "Avatar", controller: avatarController),
                Container(
                    child: CustomDropdownButton(
                  defaultValue: "Player",
                  values: roles,
                  onChange: (v) => _selectedRole = v,
                )),
                ElevatedButton(onPressed: () => addUser(provider), child: Text("Create")),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
