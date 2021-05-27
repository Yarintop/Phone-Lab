import 'package:flutter/material.dart';
import 'package:myapp/widgets/tables/parts_table.dart';


class PartsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: 
        Column(
          children: [Text("Parts Page"),
          PartsTable(),],
        ),
    );
  }
}
