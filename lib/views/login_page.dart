import 'package:flutter/material.dart';
import 'package:myapp/provider/user_provider.dart';
import 'package:provider/provider.dart';

class LoginPage extends StatelessWidget with RouteAware {
  @override
  Widget build(BuildContext context) {
    // return Container(child: Text("Login Page"));
    return Consumer<UserProvider>(
      builder: (context, userProvider, child) => Container(
        child: Text("Login Page"),
      ),
    );
  }

  @override
  void didPush() {}
}
