import 'package:dropdown_search/dropdown_search.dart';
import 'package:flutter/material.dart';

// class CustomDropdownButton extends StatelessWidget {

//   @override

// }

class CustomDropdownButton extends StatefulWidget {
  final String defaultValue;
  final Function onChange;
  final String hint;
  final List<String> values;
  final bool showClearButton;
  CustomDropdownButton({
    @required this.defaultValue,
    this.onChange,
    @required this.values,
    this.hint,
    this.showClearButton,
  });

  @override
  _CustomDropdownButtonState createState() => _CustomDropdownButtonState();
}

class _CustomDropdownButtonState extends State<CustomDropdownButton> {
  String _innerValue = "";

  TextEditingController controller = TextEditingController();
  _CustomDropdownButtonState();

  void innerChange(value) {
    // This function will handle the innder value change and will call the callback.
    // That way, the parent wouldn't neeed to keep track of the selected value all the time
    setState(() {
      _innerValue = value;
    });
    if (widget.onChange != null) widget.onChange(value);
  }

  Widget build(BuildContext context) {
    return DropdownSearch<String>(
      selectedItem: (_innerValue == null || _innerValue == "") ? widget.defaultValue : _innerValue,
      onChanged: innerChange,
      searchBoxStyle: TextStyle(backgroundColor: Colors.white),
      dropdownSearchDecoration: InputDecoration(
        contentPadding: EdgeInsets.fromLTRB(12, 12, 0, 0),
        border: OutlineInputBorder(),
        fillColor: Colors.white,
        filled: true,
      ),
      mode: Mode.MENU,
      showSearchBox: true,
      label: widget.hint,
      showClearButton: widget.showClearButton,
      items: widget.values,
      searchBoxController: controller,
    );
  }
}
