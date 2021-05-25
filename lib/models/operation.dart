import 'dart:collection';

import 'Item.dart';
import 'User.dart';

class Operation {
  String id;
  String type;
  Item item;
  DateTime createdTimestamp;
  User invokedBy;
  HashMap operationAttributes;
}
