import 'package:flutter/material.dart';
import 'package:myapp/models/part.dart';

class PartRow extends StatelessWidget {
  final Function onClick;
  final Part part;

  PartRow({this.onClick, this.part});

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        children: [
          Text(part.name),
          SizedBox(width: 50),
          Text(part.price),
        ],
      ),
    );
  }
}
