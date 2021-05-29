import 'package:flutter/material.dart';
import 'package:myapp/constants/part_specific.dart';
import 'package:myapp/constants/project_specific.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/input_field.dart';
import 'package:myapp/widgets/popups/snackbar/snack_confim.dart';
import 'package:myapp/widgets/popups/snackbar/snack_error.dart';
import 'package:provider/provider.dart';

class PartCreationForm extends StatefulWidget {
  @override
  _PartCreationFormState createState() => _PartCreationFormState();
}

class _PartCreationFormState extends State<PartCreationForm> {
  Map<String, TextEditingController> controllers = {
    "name": TextEditingController(),
    "price": TextEditingController(),
  };

  void addPart(ItemProvider itemProvider, UserProvider userProvider) {
    //BUG - should throw an exception
    Part part = Part();
    User loggedInUser = userProvider.loggedInUser;
    part.name = controllers["name"].text;
    part.price = double.parse(controllers["price"].text);
    part.active = true;
    part.createdTimestamp = DateTime.now();
    part.space = SPACE;
    part.type = PART_TYPE;

    part.createdBy = loggedInUser;

    part.lat = 1;
    part.lng = 1;
    itemProvider.addPart(part, loggedInUser).then((success) {
      showConfirmationSnack(context, "Part ${part.name} was added successfully!");
      controllers.forEach((key, controller) => {controller.clear()});
      itemProvider.loadAllParts(userProvider);
    }).onError((error, stackTrace) {
      showErrorSnack(context, error);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Consumer2<UserProvider, ItemProvider>(
      builder: (context, userProvider, itemProvider, child) => Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Container(
            padding: EdgeInsets.all(16.0),
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(5.0),
              border: Border.all(color: Colors.black, width: 1),
              color: Colors.blueGrey[50],
            ),
            child: SizedBox(
              width: 250,
              child: Column(
                children: [
                  InputField(hint: "Part Name", controller: controllers["name"]),
                  InputField(hint: "Part Price", controller: controllers["price"], numbersOnly: true),
                  ElevatedButton(
                    onPressed: () => addPart(itemProvider, userProvider),
                    child: Text("Add Part"),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
