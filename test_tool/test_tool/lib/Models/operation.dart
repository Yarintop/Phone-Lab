import 'dart:collection';

import "./item.dart";
import './user.dart';

class Operation {
  String _id = "";
  String get id => _id;
  set id(String id) => _id = id;

  User _user = new User();
  Item _item = new Item();
  DateTime? _createdTimestamp;
  String _type = "";
  HashMap? _attributes;

  User get user => _user;
  Item get item => _item;
  DateTime? get createdTimestamp => _createdTimestamp;
  String get type => _type;
  HashMap? get attributes => _attributes;

  set user(User user) => _user = user;
  set item(Item item) => _item = item;
  set createdTimestamp(DateTime? createdTimestamp) =>
      _createdTimestamp = createdTimestamp;
  set type(String type) => _type = type;
  set attributes(HashMap? attributes) => _attributes = attributes;
}
