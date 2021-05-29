import 'package:flutter/material.dart';
import 'package:myapp/constants/project_specific.dart';
import 'package:myapp/constants/user_specific.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:myapp/widgets/form_widgets/input_field.dart';
import 'package:myapp/widgets/popups/snackbar/snack_confim.dart';
import 'package:myapp/widgets/popups/snackbar/snack_error.dart';
import 'package:provider/provider.dart';

class UserForm extends StatefulWidget {
  @override
  _UserFormState createState() => _UserFormState();
}

class _UserFormState extends State<UserForm> {
  String _selectedRole = Role.PLAYER.value;

  TextEditingController usernameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController avatarController = TextEditingController();

  void addUser(UserProvider provider) {
    // This function would try to add a user using the provider which will invoke an async api call.
    User user = User.fromParams(
      SPACE,
      emailController.text,
      usernameController.text,
      avatarController.text,
      _selectedRole,
    );

    provider.addUser(user).then((success) {
      usernameController.clear();
      emailController.clear();
      avatarController.clear();
      showConfirmationSnack(context, "User - ${user.email} added successfully!");
    }).onError((error, stackTrace) {
      showErrorSnack(context, error);
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
                Text(
                  "Title",
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),
                ),
                InputField(hint: "Username", controller: usernameController),
                InputField(hint: "Email", controller: emailController),
                InputField(hint: "Avatar", controller: avatarController),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Container(
                    child: CustomDropdownButton(
                      hint: "Player's Role",
                      defaultValue: Role.PLAYER.value,
                      values: Role.values.map((Role r) => r.value).toList(),
                      onChange: (v) => _selectedRole = v,
                    ),
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    addUser(provider);
                  },
                  child: Text("Create"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
