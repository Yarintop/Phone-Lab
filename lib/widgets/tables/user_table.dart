import 'package:flutter/material.dart';
import 'package:myapp/constants/user_specific.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:provider/provider.dart';

class UsersTable extends StatelessWidget {
  List<DataColumn> createTableHeaders() {
    return [
      DataColumn(
        label: Text(
          "Username",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Email",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Role",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Avatar",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    ];
  }

  List<DataRow> createUserRows(List<User> users) {
    int index = 0;
    return users.map<DataRow>((u) => mapUserToRow(u, index: index++)).toList();
  }

  Color getRoleColor(String roleStr) {
    Role role = Role.values.firstWhere((r) => r.value == roleStr);
    switch (role) {
      case Role.ADMIN:
        return Colors.red;
      case Role.PLAYER:
        return Colors.blue;
      case Role.MANAGER:
        return Colors.green;
      default:
        return Colors.black;
    }
  }

  DataRow mapUserToRow(User user, {int index}) {
    return DataRow(
      color: MaterialStateColor.resolveWith((states) => index % 2 == 0 ? Colors.blueGrey[50] : Colors.blueGrey[100]),
      cells: [
        DataCell(Text(user.username)),
        DataCell(Text(user.email)),
        DataCell(
          Text(
            user.role,
            style: TextStyle(
              color: getRoleColor(user.role),
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        DataCell(Text(user.avatar)),
      ],
    );
  }

  Widget getDisplayWidget(UserProvider provider) {
    if (provider.loggedInUser != null) {
      return DataTable(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(5),
          border: Border.all(
            color: Colors.black,
            width: 1,
          ),
        ),
        headingRowColor: MaterialStateColor.resolveWith((states) => Colors.blueGrey[200]),
        columns: createTableHeaders(),
        rows: createUserRows(provider.users),
      );
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
