import 'dart:collection';

import 'item.dart';
import 'user.dart';

class Operation {
  String id;
  String type;
  Item item;
  DateTime createdTimestamp;
  User invokedBy;
  HashMap operationAttributes;
}
