import 'package:flutter/material.dart';
import 'package:myapp/models/job.dart';

class TechnicalDetails extends StatefulWidget {
  final Job job;
  final Function onChange;

  TechnicalDetails({this.job, this.onChange});

  @override
  _TechnicalDetailsState createState() => _TechnicalDetailsState();
}

class _TechnicalDetailsState extends State<TechnicalDetails> {
  // Controller to to manage the text field for cursor position read
  TextEditingController controller = TextEditingController();

  TextSelection lastTextPosition;

  String getText() {
    if (widget.job.dirty) return widget.job.draftFixDescription != null ? widget.job.draftFixDescription : "";
    return widget.job.fixDescription != null ? widget.job.fixDescription : "";
  }

  @override
  Widget build(BuildContext context) {
    controller.text = getText();
    // Update the cursor's location in the textfield, so the rerender would not reset the location
    if (lastTextPosition != null) controller.selection = lastTextPosition;
    return Container(
      child: Column(
        children: [
          Row(mainAxisAlignment: MainAxisAlignment.center, children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text("Phone Model: "),
            ),
            Text(widget.job.phoneModel, style: TextStyle(fontWeight: FontWeight.bold))
          ]),
          Row(mainAxisAlignment: MainAxisAlignment.center, children: [
            Text("Job Description: "),
            Text(widget.job.jobDescription, style: TextStyle(fontWeight: FontWeight.bold))
          ]),
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Repair Proccess Description: "),
              // Text(widget.job.fixDescription != null ? widget.job.fixDescription : "")
              TextField(
                controller: controller,
                keyboardType: TextInputType.multiline,
                maxLines: null,
                onChanged: (v) => {widget.onChange(v), lastTextPosition = controller.selection},
              )
            ],
          ),
        ],
      ),
    );
  }
}
