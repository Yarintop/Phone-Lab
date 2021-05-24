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
    return GestureDetector(
      onTap: () {
        navKey.currentState.pushNamed(route);
        onSelect();
        //TODO - Implement this logic outside later. (for hovering and clicking)
      },
      child: Padding(
        //TODO - Add style when hovered or clicked.
        padding: EdgeInsets.symmetric(vertical: padding),
        child: Text(this.text),
      ),
    );
  }
}
