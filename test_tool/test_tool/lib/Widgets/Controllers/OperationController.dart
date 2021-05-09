import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../MainProvider.dart';

class OperationController extends StatefulWidget {
  createState() => UserState();
}

class UserState extends State<OperationController> {
  String _selectedUser = "";
  String _selectedItem = "";

  void _selectUser(String? email) {
    setState(() {
      if (email == null)
        _selectedUser = "";
      else
        _selectedUser = email;
    });
  }

  void _selectItem(String? name) {
    setState(() {
      if (name == null)
        _selectedItem = "";
      else
        _selectedItem = name;
    });
  }

  void _addOperation(MainModel mainModel) {
    //TODO: Add operation
  }

  DropdownButton<String> getDropDownUsers(MainModel mainModel) {
    if (mainModel.users.length == 0)
      return DropdownButton<String>(
        items: [],
        disabledHint: Text("No users found"),
      );

    return DropdownButton<String>(
        onChanged: _selectUser,
        value: mainModel.users[0].email,
        items: mainModel.users
            .map((user) => DropdownMenuItem(
                  value: user.email,
                  child: Text(user.email),
                ))
            .toList());
  }

  DropdownButton<String> getDropDownItems(MainModel mainModel) {
    if (mainModel.items.length == 0)
      return DropdownButton<String>(
        items: [],
        disabledHint: Text("No items found"),
      );

    return DropdownButton<String>(
        onChanged: _selectItem,
        value: mainModel.items[0].name,
        items: mainModel.items
            .map((item) => DropdownMenuItem(
                  value: item.name,
                  child: Text(item.name),
                ))
            .toList());
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MainModel>(builder: (context, mainModel, child) {
      return Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("User:"),
              Container(
                margin: EdgeInsets.only(left: 16.0),
                child: getDropDownUsers(mainModel),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Item:"),
              Container(
                margin: EdgeInsets.only(left: 16.0),
                child: getDropDownUsers(mainModel),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              Container(
                padding: EdgeInsets.all(4.0),
                child: ElevatedButton(
                    onPressed: () => _addOperation(mainModel),
                    child: Text("Add")),
              ),
              Container(
                padding: EdgeInsets.all(4.0),
                child: ElevatedButton(
                    style: ElevatedButton.styleFrom(primary: Colors.red),
                    onPressed: mainModel.removeAllOperations,
                    child: Text("Remove All")),
              ),
            ],
          ),
        ],
      );
    });
  }
}
