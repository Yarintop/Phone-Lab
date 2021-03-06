import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/Models/item.dart';

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
  HashMap _attrs = new HashMap();
  String _currentKey = "";
  String _currentVal = "";

  void _selectUser(String? email) {
    setState(() {
      if (email == null)
        _selectedUser = "";
      else
        _selectedUser = email;
    });
  }

  void _addItem(MainModel mainModel) {
    Item item = new Item();
    item.name = _itemName;
    item.type = _itemType;
    item.active = _active;
    item.lat = _lat;
    item.lng = _lng;
    item.attributes = _attrs;
    int index =
        mainModel.users.indexWhere((element) => element.email == _selectedUser);
    if (index != -1) {
      item.user = mainModel.users[index];
      mainModel.addItem(item);
    }
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
                      _lng = double.tryParse(lng);
                    })
                  },
                ),
                width: 100,
              ),
            ],
          ),
          Text("Attributes:"),
          Row(
            // Item Attributes
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
                        })
                      },
                  child: Text("+"))
            ],
          ),
          ..._attrs.keys.map((e) => Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(e + ": " + _attrs[e]),
                  MaterialButton(
                    onPressed: () => {
                      setState(() {
                        _attrs.remove(e);
                        _attrs = HashMap.from(_attrs);
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
                    onPressed: mainModel.removeAllItems,
                    child: Text("Remove All")),
              ),
            ],
          ),
        ],
      );
    });
  }
}
