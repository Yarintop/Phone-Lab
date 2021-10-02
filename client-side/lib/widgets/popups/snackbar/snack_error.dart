import 'package:flutter/material.dart';

SnackBar errorSnack(String error) => SnackBar(
      backgroundColor: Colors.red,
      content: Text(
        error,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
    );

void showErrorSnack(BuildContext context, String error) {
  ScaffoldMessenger.of(context).showSnackBar(errorSnack(error));
}
