import 'package:flutter/material.dart';
import 'package:myapp/widgets/popups/part_popup/part_creation_form.dart';
import 'package:myapp/widgets/tables/parts_table.dart';

class PartsPage extends StatelessWidget {
  void openPartCreationForm(BuildContext context) {
    showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text("Job Details"),
        content: PartCreationForm(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          SizedBox(height: 48),
          Text(
            "Parts Page",
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
          ),
          SizedBox(height: 24),
          ElevatedButton(onPressed: () => openPartCreationForm(context), child: Text("Add Part")),
          SizedBox(height: 32),
          PartsTable(),
        ],
      ),
    );
  }
}
