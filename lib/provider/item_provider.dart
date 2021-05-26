import 'package:flutter/cupertino.dart';
import 'package:myapp/models/Item.dart';
import 'package:myapp/models/user.dart';
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

  // TODO - maybe change bool to enum of error types
  Future<bool> addJob(Job job) async {
    //TODO - make an API call
    jobs.add(job);
    return true;
  }
}
