import 'dart:collection';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:myapp/constants/operation_specific.dart';
import 'package:myapp/constants/project_specific.dart';
import 'package:myapp/models/item.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/models/operation.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/models/user.dart';
import 'package:http/http.dart' as http;

class OperationProvider extends ChangeNotifier {
  // final baseUrl = "http://${env["HOST"]}:${env["PORT"]}/$BASE_API";
  final baseUrl = "$BASE_API";
  double _totalCost;

  double get totalCost => this._totalCost;

  set totalCost(double value) => this._totalCost = value;

  Operation createOperation(String type, User user, Item item) {
    Operation operationToInvoke = Operation();
    operationToInvoke.type = type;
    operationToInvoke.invokedBy = user;
    operationToInvoke.item = item;
    operationToInvoke.operationAttributes = HashMap();
    operationToInvoke.createdTimestamp = DateTime.now();

    return operationToInvoke;
  }

  Future<int> getPartCount(Part part, User user) async {
    String url = "$baseUrl/$OPERATION_API";
    Operation operation = createOperation(GET_PART_COUNT, user, part);
    final res = await http.post(
      Uri.parse(url),
      body: operation.convertToJson(),
      headers: {"Content-Type": "application/json"},
    );
    if (res.statusCode != 200) return Future.error(jsonDecode(res.body)["message"]);
    int partCount = int.parse(res.body);
    notifyListeners();
    return partCount;
  }

  Future<void> pullJobPrice(Job job, User user) async {
    String url = "$baseUrl/$OPERATION_API";
    Operation operation = createOperation(GET_PARTS_PRICE, user, job);
    final res = await http.post(
      Uri.parse(url),
      body: operation.convertToJson(),
      headers: {"Content-Type": "application/json"},
    );
    if (res.statusCode != 200)
      return Future.error(jsonDecode(res.body)["message"]);
    else
      totalCost = jsonDecode(res.body)["totalPrice"];
    notifyListeners();
  }
}
