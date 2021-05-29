import 'package:flutter/material.dart';
import 'package:myapp/models/part.dart';

class PartDetail extends StatelessWidget {
  final Part part;
  final int count;
  PartDetail({this.part, this.count});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Container(
          width: 250,
          child: Column(
            children: [
              SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [Text("Part Name: "), Text(part.name, style: TextStyle(fontWeight: FontWeight.bold))],
              ),
              SizedBox(height: 8),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text("Part Price:  "),
                  Text(part.price.toString(), style: TextStyle(fontWeight: FontWeight.bold))
                ],
              ),
              SizedBox(height: 8),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [Text("Available parts:  "), Text(count.toString())],
              ),
              // Technical details
              SizedBox(height: 16),

              // Work progress details

              // * Confirmation / Cancellation area
              // SizedBox(height: 8),
            ],
          ),
        ),
      ],
    );
  }
}
