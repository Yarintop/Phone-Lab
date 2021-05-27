import 'package:flutter/material.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/providers/utils_provider.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/widgets/navbar/nav_item.dart';
import 'package:provider/provider.dart';

class NavBar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer2<UtilsProvider, UserProvider>(
      builder: (context, utilProvider, userProvider, child) => Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            buildNavItem(utilProvider, userProvider, "Create New Job", ROUTE_CREATE_JOB),
            buildNavItem(utilProvider, userProvider, "All Jobs", ROUTE_ALL_JOBS),
            buildNavItem(utilProvider, userProvider, "New Jobs", ROUTE_NEW_JOBS),
            buildNavItem(utilProvider, userProvider, "Ongoing jobs", ROUTE_ONGOING_JOBS),
            buildNavItem(utilProvider, userProvider, "Completed Jobs", ROUTE_COMPLETED_JOBS),
            buildNavItem(utilProvider, userProvider, "Parts List", ROUTE_PARTS),
            buildNavItem(utilProvider, userProvider, "Users", ROUTE_USERS),
          ],
        ),
      ),
    );
  }

  Expanded buildNavItem(UtilsProvider utilProvider, UserProvider userProvider, String text, String route) {
    return Expanded(
      flex: 1,
      child: Container(
        width: 150,
        child: NavItem(
          text: text,
          route: route,
          selected: utilProvider.selectedRoute == route,
          disabled: route != ROUTE_USERS ? userProvider.loggedInUser == null : false,
          onSelect: utilProvider.selectRouteAndNotify,
        ),
      ),
    );
  }
}
