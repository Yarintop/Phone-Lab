import 'package:flutter/material.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:myapp/widgets/form_widgets/input_field.dart';
import 'package:provider/provider.dart';

class UserLogin extends StatefulWidget {
  @override
  _UserLoginState createState() => _UserLoginState();
}

class _UserLoginState extends State<UserLogin> {
  // String _userToLogin;
  // String _email;
  TextEditingController controller = TextEditingController();
  // String getDefaultValue(UserProvider userProvider) {
  //   String res = userProvider.users.length == 0 ? "" : userProvider.users[0].email;
  //   _userToLogin = res;
  //   return res;
  // }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(
      builder: (context, provider, _) => Container(
        width: 350,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Login as: "),
            SizedBox(
              child: InputField(hint: "Email", controller: controller),
              width: 200,
            ),
            ElevatedButton(
                onPressed: () {
                  provider.login(email: controller.text);
                },
                child: Text("Login")),
          ],
        ),
      ),
    );
  }
}
