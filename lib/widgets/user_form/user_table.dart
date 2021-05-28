import 'package:flutter/material.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:provider/provider.dart';

class UsersTable extends StatelessWidget {
  List<DataColumn> createTableHeaders() {
    return [
      DataColumn(label: Text("Username")),
      DataColumn(label: Text("Email")),
      DataColumn(label: Text("Role")),
      DataColumn(label: Text("Avatar")),
    ];
  }

  List<DataRow> createUserRows(List<User> users) {
    return users.map<DataRow>(mapUserToRow).toList();
  }

  DataRow mapUserToRow(User user) {
    return DataRow(cells: [
      DataCell(Text(user.username)),
      DataCell(Text(user.email)),
      DataCell(Text(user.role)),
      //TODO - show icon instead
      DataCell(Text(user.avatar)),
    ]);
  }

  Widget getDisplayWidget(UserProvider provider) {
    if (provider.loggedInUser != null) {
      return DataTable(columns: createTableHeaders(), rows: createUserRows(provider.users));
    }

    return Text(
      "Please login to view all users",
      style: TextStyle(fontWeight: FontWeight.bold, color: Colors.red, fontSize: 18),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(builder: (context, provider, child) => getDisplayWidget(provider));
  }
}
