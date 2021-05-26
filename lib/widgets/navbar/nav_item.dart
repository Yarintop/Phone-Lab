import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:myapp/routes/routes.dart';

class NavItem extends StatelessWidget {
  final String text;
  final double padding;
  final bool selected;
  final Function onSelect;

  final String route;

  NavItem({
    @required this.text,
    @required this.route,
    this.padding = 50.0,
    this.selected,
    this.onSelect,
  });

  @override
  Widget build(BuildContext context) {
    return MouseRegion(
      cursor: SystemMouseCursors.click,
      child: InkWell(
        onTap: () {
          // navKey.currentState.popAndPushNamed(route);
          navKey.currentState.pushReplacementNamed(route);
          // navKey.currentState.pushNamed(route);
          onSelect(this.route);
        },
        child: Container(
          decoration: BoxDecoration(
              border: Border(
                  left: BorderSide(
            color: selected ? Colors.red : Colors.white,
            width: selected ? 10 : 0,
          ))),
          child: Center(
            child: Text(
              this.text,
              style: TextStyle(color: this.selected ? Colors.red : Colors.black),
            ),
          ),
        ),
      ),
    );
  }
}
