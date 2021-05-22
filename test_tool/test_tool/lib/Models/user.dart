class User {
  String _id = "";
  String get id => _id;
  set id(String id) => _id = id;

  String _name = "";
  String _email = "";
  String _space = "2021b.noam.levi1"; //TODO: Implement more dynamic approch
  String _role = "Player";
  String _avatar = "";

  String get role => _role;
  String get email => _email;
  String get space => _space;
  String get name => _name;
  String get avatar => _avatar;

  set role(String role) => _role = role;
  set email(String email) => _email = email;
  set space(String space) => _space = space;
  set name(String name) => _name = name;
  set avatar(String avatar) => _avatar = avatar;

  User();
  User.fromParams(String name, String email, String role, String avatar) {
    this._name = name;
    this._email = email;
    this._role = role;
    this._avatar = avatar;
  }

  factory User.fromJson(Map<String, dynamic> json) {
    return new User.fromParams(json["username"], json["userId"]["email"],
        json["role"], json["avatar"]);
  }

  Map<String, dynamic> toJson() => {
        "userId": {"email": email, "space": space},
        "role": role,
        "avatar": avatar,
        "username": name
      };

  @override
  bool operator ==(Object obj) => obj is User && obj.email == _email;
}
