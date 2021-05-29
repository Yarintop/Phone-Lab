import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:myapp/routes/routes.dart';

class NavItem extends StatelessWidget {
  final String text;
  final double padding;
  final bool selected;
  final bool disabled;
  final Function onSelect;

  final String route;

  NavItem({
    @required this.text,
    @required this.route,
    this.disabled,
    this.padding = 50.0,
    this.selected,
    this.onSelect,
  });

  Color getColor() {
    if (disabled) return Colors.grey;
    if (selected) return Colors.red;

    return Colors.black;
  }

  @override
  Widget build(BuildContext context) {
    return MouseRegion(
      cursor: SystemMouseCursors.click,
      child: InkWell(
        onTap: () {
          // If button is disabled, onTap will do nothing.
          if (disabled) return;

          navKey.currentState.pushReplacementNamed(route);
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
              style: TextStyle(color: getColor()),
            ),
          ),
        ),
      ),
    );
  }
}
