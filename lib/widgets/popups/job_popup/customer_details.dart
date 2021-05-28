import 'package:flutter/material.dart';
import 'package:myapp/models/job.dart';

class CustomerDetails extends StatelessWidget {
  final Job job;

  CustomerDetails({this.job});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Customer name: "),
            Text(
              job.customer,
              style: TextStyle(fontWeight: FontWeight.bold),
            )
          ],
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Customer Phone Number: "),
            Text(
              job.phoneNumber,
              style: TextStyle(fontWeight: FontWeight.bold),
            )
          ],
        ),
      ],
    );
  }
}
