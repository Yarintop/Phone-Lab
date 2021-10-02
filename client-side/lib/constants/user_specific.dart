enum Role { PLAYER, MANAGER, ADMIN }

extension RoleExtension on Role {
  String get value {
    switch (this) {
      case Role.PLAYER:
        return "PLAYER";
      case Role.MANAGER:
        return "MANAGER";
      case Role.ADMIN:
        return "ADMIN";
      default:
        return "ERROR";
    }
  }
}
