import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/Models/operation.dart';

import '../../MainProvider.dart';

class OperationController extends StatefulWidget {
  createState() => UserState();
}

class UserState extends State<OperationController> {
  String _selectedUser = "";
  String _selectedItem = "";
  String _operationType = "";
  String _currentKey = "";
  String _currentVal = "";
  HashMap _attrs = new HashMap();

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
    _selectedUser =
        _selectedUser == "" ? mainModel.users[0].email : _selectedUser;
    _selectedItem =
        _selectedItem == "" ? mainModel.items[0].name : _selectedItem;
    int indexUser =
        mainModel.users.indexWhere((element) => element.email == _selectedUser);
    int indexItem =
        mainModel.items.indexWhere((element) => element.name == _selectedItem);
    if (indexItem == -1 || indexUser == -1) return;

    Operation operation = new Operation();
    operation.item = mainModel.items[indexItem];
    operation.user = mainModel.users[indexUser];
    operation.type = _operationType;
    operation.attributes = _attrs;
    mainModel.addOperation(operation);
  }

  DropdownButton<String> getDropDownUsers(MainModel mainModel) {
    if (mainModel.users.length == 0)
      return DropdownButton<String>(
        items: [],
        disabledHint: Text("No users found"),
      );

    return DropdownButton<String>(
        onChanged: _selectUser,
        value: _selectedUser == "" ? mainModel.users[0].email : _selectedUser,
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
        value: _selectedItem == "" ? mainModel.items[0].name : _selectedItem,
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
                child: getDropDownItems(mainModel),
              ),
            ],
          ),
          Row(
            //Type input
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                margin: EdgeInsets.all(8.0),
                child: TextField(
                  decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "Type",
                      contentPadding: EdgeInsets.all(4.0)),
                  onChanged: (type) => {
                    setState(() {
                      _operationType = type;
                    })
                  },
                ),
                width: 100,
              ),
            ],
          ),
          Text("Attributes:"),
          Row(
            // Operation Attributes
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                margin: EdgeInsets.all(8.0),
                child: TextField(
                  decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "key",
                      contentPadding: EdgeInsets.all(4.0)),
                  onChanged: (key) => {
                    setState(() {
                      _currentKey = key;
                    })
                  },
                ),
                width: 50,
              ),
              Container(
                margin: EdgeInsets.all(8.0),
                child: TextField(
                  decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "value",
                      contentPadding: EdgeInsets.all(4.0)),
                  onChanged: (val) => {
                    setState(() {
                      _currentVal = val;
                    })
                  },
                ),
                width: 100,
              ),
              ElevatedButton(
                  onPressed: () => {
                        setState(() {
                          _attrs[_currentKey] = _currentVal;
                          _attrs = HashMap.from(_attrs);
                        })
                      },
                  child: Text("+"))
            ],
          ),
          ..._attrs.keys.map((key) => Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(key + ": " + _attrs[key]),
                  MaterialButton(
                    onPressed: () => {
                      setState(() {
                        _attrs.remove(key);
                      })
                    },
                    child: Text("-"),
                    color: Colors.red,
                    textColor: Colors.white,
                    minWidth: 10,
                  )
                ],
              )),
          Row(
            // Add/Remove operation
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
