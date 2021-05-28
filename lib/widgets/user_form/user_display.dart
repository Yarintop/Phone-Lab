import 'package:flutter/material.dart';
import 'package:myapp/widgets/user_form/user_table.dart';

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
