import 'package:flutter/material.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/provider/user_provider.dart';
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
      DataCell(Text(user.avatar)),
    ]);
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(
      builder: (context, provider, child) => DataTable(
        columns: createTableHeaders(),
        rows: createUserRows(provider.users),
      ),
    );
  }
}
