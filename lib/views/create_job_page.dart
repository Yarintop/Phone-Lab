import 'package:flutter/material.dart';
import 'package:myapp/widgets/job_from/job_form.dart';

class CreateJobPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          SizedBox(height: 32.0),
          Text(
            "Creating New Job",
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
          ),
          SizedBox(height: 48.0),
          JobCreationForm(),
        ],
      ),
    );
  }
}
