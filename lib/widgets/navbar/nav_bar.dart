import 'package:flutter/material.dart';
import 'package:myapp/providers/utils_provider.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/widgets/navbar/nav_item.dart';
import 'package:provider/provider.dart';

class NavBar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer<UtilsProvider>(
        builder: (context, utilProvider, child) => Container(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  buildNavItem(utilProvider, "Create New Job", ROUTE_CREATE_JOB),
                  buildNavItem(utilProvider, "All Jobs", ROUTE_ALL_JOBS),
                  buildNavItem(utilProvider, "New Jobs", ROUTE_NEW_JOBS),
                  buildNavItem(utilProvider, "Ongoing jobs", ROUTE_ONGOING_JOBS),
                  buildNavItem(utilProvider, "Completed Jobs", ROUTE_COMPLETED_JOBS),
                  buildNavItem(utilProvider, "Parts List", ROUTE_PARTS),
                  buildNavItem(utilProvider, "Users", ROUTE_USERS),
                ],
              ),
            ));
  }

  Expanded buildNavItem(UtilsProvider utilProvider, String text, String route) {
    return Expanded(
      flex: 1,
      child: Container(
        width: 150,
        child: NavItem(
          text: text,
          route: route,
          selected: utilProvider.selectedRoute == route,
          onSelect: utilProvider.selectRouteAndNotify,
        ),
      ),
    );
  }
}
