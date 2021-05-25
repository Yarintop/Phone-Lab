import 'package:flutter/material.dart';
import 'package:myapp/widgets/navbar/nav_bar.dart';

class AppView extends StatelessWidget {
  final Widget child;
  final String currentRoute;
  AppView({this.child, this.currentRoute});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Row(
        children: [NavBar(), Expanded(child: child)],
      ),
    );
  }
}
