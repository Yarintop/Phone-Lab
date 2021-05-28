import 'package:flutter/material.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:myapp/widgets/popups/job_popup/part_list.dart';
import 'package:provider/provider.dart';

class JobPartsInfo extends StatefulWidget {
  final Job job;
  final Function onChange;
  final Function onBind;
  final double totalCost;

  JobPartsInfo({@required this.job, this.onChange, this.onBind, this.totalCost = 0});

  @override
  _JobPartsInfoState createState() => _JobPartsInfoState();
}

String getDefaultValue(ItemProvider provider, Job job) {
  if (provider.parts.length == 0) return "";
  Part partToAdd = job.draftNewPart;
  return partToAdd == null ? "" : "${partToAdd.name}:${partToAdd.price}";
}

List<String> getReleventParts(ItemProvider itemProvider, Job job) {
  List<Part> activePart = itemProvider.parts.where((Part p) => p.active).toList();
  return activePart.map((p) => "${p.name}:${p.price}").toList();
}

class _JobPartsInfoState extends State<JobPartsInfo> {
  @override
  Widget build(BuildContext context) {
    return Consumer2<UserProvider, ItemProvider>(
      builder: (context, userProvider, itemProvider, child) => Column(
        children: [
          Text("Used Replacment Parts:"),
          SizedBox(height: 16),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SizedBox(
                width: 250,
                child: CustomDropdownButton(
                  hint: "Part to add",
                  defaultValue: getDefaultValue(itemProvider, widget.job),
                  onChange: widget.onChange,
                  values: getReleventParts(itemProvider, widget.job),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: ElevatedButton(
                  onPressed: widget.onBind,
                  child: Text("Bind Part"),
                ),
              )
            ],
          ),
          SizedBox(height: 16),
          JobPartList(job: widget.job),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [Text("Total Price: "), Text(widget.totalCost.toString())],
          )
        ],
      ),
    );
  }
}
