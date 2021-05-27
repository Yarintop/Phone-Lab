import 'package:flutter/material.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:provider/provider.dart';

class UserLogin extends StatefulWidget {
  @override
  _UserLoginState createState() => _UserLoginState();
}

class _UserLoginState extends State<UserLogin> {
  String _userToLogin;

  String getDefaultValue(UserProvider userProvider) {
    String res = userProvider.users.length == 0 ? "" : userProvider.users[0].email;
    _userToLogin = res;
    return res;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(
      builder: (context, provider, _) => Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text("Login as: "),
          CustomDropdownButton(
            defaultValue: getDefaultValue(provider),
            values: provider.users.map<String>((u) => u.email).toList(),
            onChange: (v) => {_userToLogin = v},
          ),
          ElevatedButton(
              onPressed: () {
                provider.login(email: _userToLogin);
              },
              child: Text("Login")),
        ],
      ),
    );
  }
}
