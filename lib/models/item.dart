// ignore: unnecessary_getters_setters
import 'dart:collection';

import 'user.dart';

class Item {
  String _id;
  String _space;
  String _type;
  String _name;
  bool _active;
  DateTime _createdTimestamp;
  User _createdBy;
  double _lat;
  double _lng;
  HashMap _itemAttributes = HashMap();
  List<Item> _children = [];
  List<Item> _parents = [];

  Item();
  Item.fromParams(this._id, this._space, this._type, this._name, this._active, this._createdTimestamp, this._createdBy,
      this._lat, this._lng, this._itemAttributes);

  Item.fromJSON(Map<String, dynamic> json) {
    this.id = json["itemId"]["id"];
    this.space = json["itemId"]["space"];
    this.type = json["type"];
    this.name = json["name"];
    this.active = json["active"];
    this.createdTimestamp = DateTime.parse(json["createdTimestamp"]);
    this.createdBy = User.fromJSON(json["createdBy"]);
    this.lat = json["location"]["lat"];
    this.lng = json["location"]["lng"];
    this.itemAttributes = HashMap.from(json["itemAttributes"]);
  }

  factory Item.createFromJSON(Map<String, dynamic> json) {
    return new Item.fromJSON(json);
  }

  Map<String, dynamic> toJson() {
    print("item - to json");
    Map tmp = {
      "itemId": {"space": space, "id": id},
      "type": type,
      "name": name,
      "active": active.toString(),
      "createdTimestamp": createdTimestamp.toString(),
      "createdBy": {
        "userId": {
          "space": createdBy.space,
          "email": createdBy.email,
        },
      },
      "location": {
        "lat": lat,
        "lng": lng,
      },
      // "itemAttributes": itemAttributes,
    };
    print(tmp.toString());
    return tmp;
  }

  get id => this._id;
  get space => this._space;
  get type => this._type;
  get name => this._name;
  get active => this._active;
  DateTime get createdTimestamp => this._createdTimestamp;
  User get createdBy => this._createdBy;
  get lat => this._lat;
  get lng => this._lng;
  get itemAttributes => this._itemAttributes;
  List<Item> get children => this._children;
  List<Item> get parents => this._parents;

  set id(value) => this._id = value;
  set space(value) => this._space = value;
  set parents(parents) => this._parents = parents;
  set children(children) => this._children = children;
  set itemAttributes(itemAttributes) => this._itemAttributes = itemAttributes;
  set lng(lng) => this._lng = lng;
  set type(type) => this._type = type;
  set lat(lat) => this._lat = lat;
  set createdBy(createdBy) => this._createdBy = createdBy;
  set createdTimestamp(createdTimestamp) => this._createdTimestamp = createdTimestamp;
  set name(name) => this._name = name;
  set active(active) => this._active = active;
}
