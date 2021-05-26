import 'package:flutter/material.dart';
import 'package:myapp/provider/user_provider.dart';
import 'package:myapp/widgets/user-form/user_table.dart';

class UserDisplay extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          child: Text("Users List:"),
        ),
        UsersTable()
      ],
    );
  }
}
