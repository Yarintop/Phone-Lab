import 'package:flutter/material.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/constants/project_specific.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:myapp/widgets/form_widgets/input_field.dart';
import 'package:myapp/widgets/popups/snackbar/snack_confim.dart';
import 'package:myapp/widgets/popups/snackbar/snack_error.dart';
import 'package:provider/provider.dart';

class JobCreationForm extends StatefulWidget {
  @override
  _JobCreationFormState createState() => _JobCreationFormState();
}

class _JobCreationFormState extends State<JobCreationForm> {
  Map<String, TextEditingController> controllers = {
    "customer": TextEditingController(),
    "phone": TextEditingController(),
    "model": TextEditingController(),
    "description": TextEditingController(),
  };
  User _selectedUser;

  void addJob(ItemProvider provider, User user) {
    if (_selectedUser == null) return;
    Job job = Job();
    job.status = Progress.IN_PROGRESS;
    job.customer = controllers["customer"].text;
    job.phoneNumber = controllers["phone"].text;
    job.phoneModel = controllers["model"].text;
    job.jobDescription = controllers["description"].text;
    job.assignedTechnician = _selectedUser.email; // could be changed
    job.active = true;
    job.createdTimestamp = DateTime.now();
    job.space = SPACE;
    job.type = REPAIR_JOB_TYPE;
    job.name = "RepairJob";

    job.createdBy = user;

    job.lat = 1;
    job.lng = 1;
    provider.addJob(job, user).then((success) {
      controllers.forEach((key, controller) => {controller.clear()});
      showConfirmationSnack(context, "Job added successfully!");
    }).onError((error, stackTrace) {
      showErrorSnack(context, error);
    });
  }

  void _selectUser(String email, UserProvider userProvider) {
    setState(() {
      _selectedUser = userProvider.findUserByEmail(email);
    });
  }

  String getDefaultValue(UserProvider provider) {
    String defaultValue = provider.users.length == 0 ? "" : provider.users[0].email;
    if (defaultValue != "") _selectedUser = provider.findUserByEmail(defaultValue);
    return provider.users.length == 0 ? "" : provider.users[0].email;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer2<UserProvider, ItemProvider>(
        builder: (context, userProvider, itemProvider, child) => Container(
              padding: EdgeInsets.all(16.0),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(5.0),
                border: Border.all(color: Colors.black, width: 1),
                color: Colors.blueGrey[50],
              ),
              child: SizedBox(
                width: 550,
                child: Column(
                  children: [
                    InputField(hint: "Customer", controller: controllers["customer"]),
                    InputField(hint: "Phone Number", controller: controllers["phone"]),
                    InputField(hint: "Phone Model", controller: controllers["model"]),
                    InputField(hint: "Description", controller: controllers["description"]),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text("Technician:"),
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: SizedBox(
                            width: 350,
                            child: CustomDropdownButton(
                              defaultValue: getDefaultValue(userProvider),
                              values: userProvider.users.map<String>((User u) => u.email).toList(),
                              onChange: (val) => _selectUser(val, userProvider),
                            ),
                          ),
                        ),
                      ],
                    ),
                    ElevatedButton(
                      onPressed: () => addJob(itemProvider, userProvider.loggedInUser),
                      child: Text("Create Job"),
                    )
                  ],
                ),
              ),
            ));
  }
}
