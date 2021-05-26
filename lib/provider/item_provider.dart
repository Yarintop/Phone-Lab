import 'package:flutter/cupertino.dart';
import 'package:myapp/models/Item.dart';
import 'package:myapp/models/User.dart';
import 'package:myapp/models/job.dart';

class ItemProvider extends ChangeNotifier {
  //TEMP
  final List<Job> jobs = [
    Job.fromParams(
      "temp",
      "2021b.noam.levi1",
      "Phone",
      "SN-950F",
      true,
      DateTime.now(),
      User(),
      1,
      1,
      {
        "customer": "John",
        "phoneModel": "SN-950F",
        "phoneNumber": "054-1234567",
        "description": "Sample Phone",
        "status": "In Progress",
        "assignedTechnician": "Jimmy",
      },
    )
  ];
  //
  final List<Item> items = [];

  //TODO - add methods to get items or specific type items
}
