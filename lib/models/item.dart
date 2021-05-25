// ignore: unnecessary_getters_setters
import 'dart:collection';

import 'User.dart';

class Item {
  String _id;
  String type;
  String name;
  bool active;
  DateTime createdTimestamp;
  User createdBy;
  double lat;
  double lng;
  HashMap itemAttributes;
  List<Item> children;
  List<Item> parents;

  get id => this._id;
  get getType => this.type;
  get getName => this.name;
  get getActive => this.active;
  get getCreatedTimestamp => this.createdTimestamp;
  get getCreatedBy => this.createdBy;
  get getLat => this.lat;
  get getLng => this.lng;
  get getItemAttributes => this.itemAttributes;
  get getChildren => this.children;
  get getParents => this.parents;

  set id(value) => this._id = value;
  set setParents(parents) => this.parents = parents;
  set setChildren(children) => this.children = children;
  set setItemAttributes(itemAttributes) => this.itemAttributes = itemAttributes;
  set setLng(lng) => this.lng = lng;
  set setType(type) => this.type = type;
  set setLat(lat) => this.lat = lat;
  set setCreatedBy(createdBy) => this.createdBy = createdBy;
  set setCreatedTimestamp(createdTimestamp) => this.createdTimestamp = createdTimestamp;
  set setName(name) => this.name = name;
  set setActive(active) => this.active = active;
}
