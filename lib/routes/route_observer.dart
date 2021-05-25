import 'package:flutter/cupertino.dart';
import 'package:myapp/provider/utils_provider.dart';

//TODO : Delete? should be used to detected route changes, but any update occurs in the middle of the build proccess.
class RepairRouteObserver extends RouteObserver {
  final UtilsProvider provider;

  RepairRouteObserver({this.provider});

  void didPush(Route route, Route prev) {
    this.provider.selectRoute(route.settings.name);
  }

  void didPop(Route route, Route prev) {}
}
