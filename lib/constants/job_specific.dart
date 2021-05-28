import 'package:json_annotation/json_annotation.dart';

const NOT_ASSIGNED = "Not Assigned";
const REPAIR_JOB_TYPE = "Job";

const CHANGES_DETECTED_STRING = "Unsaved changes detected, please save";

enum Progress {
  @JsonValue("In Progress")
  IN_PROGRESS,
  @JsonValue("Completed")
  COMPLETED,
  @JsonValue("Failed to fix")
  FAILED_TO_FIX,
  @JsonValue("Waiting For Pickup")
  WAITING_FOR_PICKUP,
  @JsonValue("Undefined")
  UNDEFINED
}

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
