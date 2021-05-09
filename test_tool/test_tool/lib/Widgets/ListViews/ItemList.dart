import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/Models/item.dart';

import '../../MainProvider.dart';

class ItemsView extends StatelessWidget {
  String _getItemDetails(Item item) {
    String itemName = item.name;
    String userEmail = item.user.email;
    return userEmail + " | " + itemName;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MainModel>(
      builder: (context, mainModel, child) {
        return Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text("Item List:"),
            ...mainModel.items.map((item) => Text(_getItemDetails(item))),
          ],
        );
      },
    );
  }
}
