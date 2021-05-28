import 'package:flutter/material.dart';

SnackBar confirmSnack(String message) => SnackBar(
      backgroundColor: Colors.lightGreen,
      content: Text(
        message,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
    );

void showConfirmationSnack(BuildContext context, String message) {
  ScaffoldMessenger.of(context).showSnackBar(confirmSnack(message));
}
