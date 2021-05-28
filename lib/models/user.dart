import 'dart:convert';

class User {
  String _space;
  String _email;
  String _username;
  String _avatar;
  String _role;

  // Constructors
  User();
  User.fromParams(this._space, this._email, this._username, this._avatar, this._role);
  factory User.fromJSON(json) {
    return User.fromParams(
      json["userId"]["space"],
      json["userId"]["email"],
      json["username"],
      json["avatar"],
      json["role"],
    );
  }

  Map<String, dynamic> toJson() => {
        "userId": {"email": email, "space": space},
        "role": role,
        "avatar": avatar,
        "username": username,
      };

  String convertToJson() {
    return jsonEncode({
      "userId": {"email": email, "space": space},
      "role": role,
      "avatar": avatar,
      "username": username,
    });
  }

  @override
  bool operator ==(Object obj) => obj is User && (obj.space == _space && obj.email == _email);

  String get space => _space;
  String get email => _email;
  String get username => _username;
  String get avatar => _avatar;
  String get role => _role;

  set space(String id) => _space = space;
  set email(String email) => _email = email;
  set username(String username) => _username = username;
  set avatar(String avatar) => _avatar = avatar;
  set role(String role) => _role = role;
}
