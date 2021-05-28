import 'package:flutter/material.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/providers/utils_provider.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/widgets/navbar/nav_item.dart';
import 'package:provider/provider.dart';

class NavBar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer3<UtilsProvider, UserProvider, ItemProvider>(
      builder: (context, utilProvider, userProvider, itemProvider, child) => Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            buildNavItem(utilProvider, userProvider, itemProvider, "Create New Job", ROUTE_CREATE_JOB),
            buildNavItem(utilProvider, userProvider, itemProvider, "All Jobs", ROUTE_ALL_JOBS),
            buildNavItem(utilProvider, userProvider, itemProvider, "New Jobs", ROUTE_NEW_JOBS),
            buildNavItem(utilProvider, userProvider, itemProvider, "Ongoing jobs", ROUTE_ONGOING_JOBS),
            buildNavItem(utilProvider, userProvider, itemProvider, "Completed Jobs", ROUTE_COMPLETED_JOBS),
            buildNavItem(utilProvider, userProvider, itemProvider, "Parts List", ROUTE_PARTS),
            buildNavItem(utilProvider, userProvider, itemProvider, "Users", ROUTE_USERS),
          ],
        ),
      ),
    );
  }

  Expanded buildNavItem(
      UtilsProvider utilProvider, UserProvider userProvider, ItemProvider itemProvider, String text, String route) {
    void pullData(UserProvider userPovider, ItemProvider itemProvider) {
      print("\n\n\n\nPulling data");
      itemProvider.pullData(userProvider).then((v) => {userProvider.pullData()});
    }

    return Expanded(
      flex: 1,
      child: Container(
        width: 150,
        child: NavItem(
          text: text,
          route: route,
          selected: utilProvider.selectedRoute == route,
          disabled: route != ROUTE_USERS ? userProvider.loggedInUser == null : false,
          onSelect: (route) {
            pullData(userProvider, itemProvider);
            utilProvider.selectRouteAndNotify(route);
          },
        ),
      ),
    );
  }
}
