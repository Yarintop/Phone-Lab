import 'package:flutter/material.dart';
import 'package:myapp/widgets/nav_bar.dart';

class AppView extends StatelessWidget {
  final Widget child;

  AppView({this.child});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Row(
        children: [NavBar(), Expanded(child: child)],
      ),
    );
  }
}
