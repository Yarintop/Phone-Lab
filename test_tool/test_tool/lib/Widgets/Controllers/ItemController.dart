import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../MainProvider.dart';

class ItemController extends StatefulWidget {
  createState() => UserState();
}

class UserState extends State<ItemController> {
  String _selectedUser = "";
  String _itemName = "";
  String _itemType = "";
  bool _active = true;
  double? _lat;
  double? _lng;
  HashMap? _attrs;

  void _selectUser(String? email) {
    setState(() {
      if (email == null)
        _selectedUser = "";
      else
        _selectedUser = email;
    });
  }

  void _addItem(MainModel mainModel) {
    //TODO: Add item
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
              Container(
                margin: EdgeInsets.all(8.0),
                child: TextField(
                  decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "Name",
                      contentPadding: EdgeInsets.all(4.0)),
                  onChanged: (name) => {
                    setState(() {
                      _itemName = name;
                    })
                  },
                ),
                width: 200,
              ),
            ],
          ),
          Row(
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
                      _itemType = type;
                    })
                  },
                ),
                width: 100,
              ),
              Text("Active"),
              Checkbox(
                  value: _active,
                  onChanged: (active) => setState(() {
                        if (active == null)
                          _active = false;
                        else
                          _active = active;
                      })),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                margin: EdgeInsets.all(8.0),
                child: TextField(
                  decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "Latitude",
                      contentPadding: EdgeInsets.all(4.0)),
                  onChanged: (lat) => {
                    setState(() {
                      _lat = double.tryParse(lat);
                    })
                  },
                ),
                width: 100,
              ),
              Container(
                margin: EdgeInsets.all(8.0),
                child: TextField(
                  decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "Longitude",
                      contentPadding: EdgeInsets.all(4.0)),
                  onChanged: (lng) => {
                    setState(() {
                      _lat = double.tryParse(lng);
                    })
                  },
                ),
                width: 100,
              ),
            ],
          ),
          //TODO: Add attribute table/list
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              Container(
                padding: EdgeInsets.all(4.0),
                child: ElevatedButton(
                    onPressed: () => _addItem(mainModel), child: Text("Add")),
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
