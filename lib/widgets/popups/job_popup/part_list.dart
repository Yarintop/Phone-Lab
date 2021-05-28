import 'package:flutter/material.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/widgets/popups/job_popup/part_row.dart';

class JobPartList extends StatelessWidget {
  final Function onDelete;
  final Job job;

  JobPartList({this.onDelete, @required this.job});

  List<Widget> getSavedParts(Job job) {
    List<Widget> tmp = job.partsUsed.map<Widget>((Part part) => PartRow(part: part)).toList();
    return tmp;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(color: Colors.white, border: Border.all(width: 1)),
      child: SizedBox(
        width: 350,
        height: 125,
        child: ListView(
          scrollDirection: Axis.vertical,
          children: getSavedParts(job),
        ),
      ),
    );
  }
}
