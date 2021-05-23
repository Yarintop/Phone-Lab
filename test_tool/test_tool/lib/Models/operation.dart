import 'dart:collection';

import "./item.dart";
import './user.dart';

class Operation {
  String _id = "";
  String _space = "2021b.noam.levi1"; //TODO: Implement more dynamic approch
  User _user = new User();
  Item _item = new Item();
  DateTime? _createdTimestamp;
  String _type = "";
  HashMap? _attributes;

  String get id => _id;
  String get space => _space;
  User get user => _user;
  Item get item => _item;
  DateTime? get createdTimestamp => _createdTimestamp;
  String get type => _type;
  HashMap? get attributes => _attributes;

  set id(String id) => _id = id;
  set space(String space) => _space = space;
  set user(User user) => _user = user;
  set item(Item item) => _item = item;
  set createdTimestamp(DateTime? createdTimestamp) =>
      _createdTimestamp = createdTimestamp;
  set type(String type) => _type = type;
  set attributes(HashMap? attributes) => _attributes = attributes;

  bool operator ==(Object obj) => obj is Operation && obj.id == _id;

  Operation(); // Default constructor

  Operation.fromParams(this._id, this._space, this._user, this._item,
      this._createdTimestamp, this._type, this._attributes);

  factory Operation.fromJson(Map<String, dynamic> json) {
    return new Operation.fromParams(
        json["operationId"]["id"],
        json["operationId"]["space"],
        User.fromJson(json["invokedBy"]),
        Item.fromJson(json["item"]),
        DateTime.parse(json["createdTimestamp"]),
        json["type"],
        HashMap.from(json["operationAttributes"]));
  }
}
