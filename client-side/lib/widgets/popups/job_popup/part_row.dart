import 'package:flutter/material.dart';
import 'package:myapp/models/part.dart';

class PartRow extends StatelessWidget {
  final Function onClick;
  final Part part;

  PartRow({this.onClick, this.part});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(left: 5, top: 4),
      child: Container(
        child: Row(
          children: [
            Text("Part name: "),
            SizedBox(width: 10),
            Text(part.name, style: TextStyle(fontWeight: FontWeight.bold)),
            SizedBox(width: 20),
            Text(" | Part cost: "),
            SizedBox(width: 10),
            Text(
              part.price.toString(),
              style: TextStyle(
                color: Colors.green,
                fontWeight: FontWeight.bold,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
