import 'dart:collection';
import 'dart:convert';

import "./user.dart";

class Item {
  String _id = "";
  String get id => _id;
  set id(String id) => _id = id;

  User _user = new User();
  String _type = "";
  String _name = "";
  String _space = "2021b.noam.levi1"; //TODO: Implement more dynamic approch
  bool _active = true;
  DateTime? _createdTimestamp;
  double? _lat;
  double? _lng;
  HashMap? _attributes;

  User get user => _user;
  String get type => _type;
  String get name => _name;
  String get space => _space;
  bool get active => _active;
  DateTime? get createdTimestamp => _createdTimestamp;
  double? get lat => _lat;
  double? get lng => _lng;
  HashMap? get attributes => _attributes;

  set user(User user) => _user = user;
  set type(String type) => _type = type;
  set name(String name) => _name = name;
  set space(String space) => _space = space;
  set active(bool active) => _active = active;
  set createdTimestamp(DateTime? createdTimestamp) =>
      _createdTimestamp = createdTimestamp;
  set lat(double? lat) => _lat = lat;
  set lng(double? lng) => _lng = lng;
  set attributes(HashMap? attributes) => _attributes = attributes;

  @override
  bool operator ==(Object obj) => obj is Item && obj.name == _name;

  Item(); // Default constructor

  Item.fromParams(this._id, this._type, this._name, this._user, this._active,
      this._createdTimestamp, this._lat, this._lng, this._attributes);

  factory Item.fromJson(Map<String, dynamic> json) {
    return new Item.fromParams(
        json["itemId"]["id"],
        json["type"],
        json["name"],
        User.fromJson(json["createdBy"]),
        json["active"],
        DateTime.parse(json["createdTimestamp"]),
        json["location"]["lat"],
        json["location"]["lng"],
        HashMap.from(json["itemAttributes"]));
  }
}
