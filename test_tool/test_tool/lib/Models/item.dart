import 'dart:collection';

import "./user.dart";

class Item {
  String _id = "";
  String get id => _id;
  set id(String id) => _id = id;

  User _user = new User();
  String _type = "";
  String _name = "";
  bool _active = true;
  DateTime? _createdTimestamp;
  double? _lat;
  double? _lng;
  HashMap? _attributes;

  User get user => _user;
  String get type => _type;
  String get name => _name;
  bool get active => _active;
  DateTime? get createdTimestamp => _createdTimestamp;
  double? get lat => _lat;
  double? get lng => _lng;
  HashMap? get attributes => _attributes;

  set user(User user) => _user = user;
  set type(String type) => _type = type;
  set name(String name) => _name = name;
  set active(bool active) => _active = active;
  set createdTimestamp(DateTime? createdTimestamp) =>
      _createdTimestamp = createdTimestamp;
  set lat(double? lat) => _lat = lat;
  set lng(double? lng) => _lng = lng;
  set attributes(HashMap? attributes) => _attributes = attributes;
}
