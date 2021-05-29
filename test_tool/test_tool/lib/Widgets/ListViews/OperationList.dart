import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/Models/operation.dart';

import '../../MainProvider.dart';

class OperationsView extends StatelessWidget {
  String _getOperationDetail(Operation operation) {
    String itemName = operation.item.name;
    String userEmail = operation.user.email;
    String opType = operation.type;
    return userEmail + " | " + itemName + " | " + opType;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MainModel>(
      builder: (context, mainModel, child) {
        return Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text("Operation List:"),
            ...mainModel.operations.map((op) => Text(_getOperationDetail(op))),
          ],
        );
      },
    );
  }
}
