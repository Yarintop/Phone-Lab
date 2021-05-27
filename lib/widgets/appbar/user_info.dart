import 'package:flutter/material.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:provider/provider.dart';

class UserInfo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(
      builder: (context, provider, _) => Container(
        width: 350,
        child: Center(
          child: Padding(
            padding: EdgeInsets.only(right: 48),
            child: Text("User: " + (provider.loggedInUser == null ? "NOT LOGGED IN" : provider.loggedInUser.email)),
          ),
        ),
      ),
    );
  }
}
