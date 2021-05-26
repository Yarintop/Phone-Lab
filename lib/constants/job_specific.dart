const NOT_ASSIGNED = "Not Assigned";

enum Progress { IN_PROGRESS, COMPLETED, FAILED_TO_FIX, WAITING_FOR_PICKUP, UNDEFINED }

extension ProgressExtension on Progress {
  String get value {
    switch (this) {
      case Progress.COMPLETED:
        return "Completed";
      case Progress.IN_PROGRESS:
        return "In Progress";
      case Progress.FAILED_TO_FIX:
        return "Failed To Fix";
      case Progress.WAITING_FOR_PICKUP:
        return "Waiting For Pickup";
      case Progress.UNDEFINED:
        return "Undefined";
      default:
        return "";
    }
  }
}
