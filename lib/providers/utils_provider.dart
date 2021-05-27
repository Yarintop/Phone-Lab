import 'package:flutter/cupertino.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/routes/routes.dart';

class UtilsProvider extends ChangeNotifier {
  String selectedRoute = ROUTE_LOGIN;

  void selectRouteAndNotify(String route) {
    this.selectedRoute = route;
    notifyListeners();
  }

  void selectRoute(String route) {
    this.selectedRoute = route;
  }
}
