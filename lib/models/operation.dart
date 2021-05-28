import 'dart:collection';
import 'dart:convert';

import 'item.dart';
import 'user.dart';

class Operation {
  String _id;
  String _type;
  Item _item;
  DateTime _createdTimestamp;
  User _invokedBy;
  HashMap _operationAttributes;

  String get id => this._id;

  set id(String value) => this._id = value;
  set type(value) => this._type = value;
  set item(value) => this._item = value;
  set createdTimestamp(value) => this._createdTimestamp = value;
  set invokedBy(value) => this._invokedBy = value;
  set operationAttributes(value) => this._operationAttributes = value;

  get type => this._type;
  get item => this._item;
  get createdTimestamp => this._createdTimestamp;
  get invokedBy => this._invokedBy;
  get operationAttributes => this._operationAttributes;

  String convertToJson() {
    return jsonEncode(
      {
        "type": this._type,
        "item": {
          "itemId": {
            "space": this._item.space,
            "id": this._item.id,
          }
        },
        "createdTimestamp": _createdTimestamp.toIso8601String(),
        "invokedBy": {
          "userId": {
            "space": this._invokedBy.space,
            "email": this._invokedBy.email,
          }
        },
        "operationAttributes": _operationAttributes.isEmpty ? {} : jsonEncode(this._operationAttributes),
      },
    );
  }
}
