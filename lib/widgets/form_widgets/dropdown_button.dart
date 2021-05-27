import 'package:flutter/material.dart';

// class CustomDropdownButton extends StatelessWidget {

//   @override

// }

class CustomDropdownButton extends StatefulWidget {
  final String defaultValue;
  final Function onChange;
  final List<String> values;

  CustomDropdownButton({this.defaultValue, this.onChange, this.values});

  @override
  _CustomDropdownButtonState createState() => _CustomDropdownButtonState();
}

class _CustomDropdownButtonState extends State<CustomDropdownButton> {
  String _innerValue;

  _CustomDropdownButtonState();

  void innerChange(value) {
    // This function will handle the innder value change and will call the callback.
    // That way, the parent wouldn't neeed to keep track of the selected value all the time
    setState(() {
      _innerValue = value;
    });
    widget.onChange(value);
  }

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
        value: _innerValue == null ? widget.defaultValue : _innerValue,
        onChanged: innerChange,

        // focusNode: _secondNode,
        items: widget.values
            .map((e) => DropdownMenuItem(
                  child: Text(e),
                  value: e,
                ))
            .toList());
  }
}
