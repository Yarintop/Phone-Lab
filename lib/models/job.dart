import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/part.dart';

import 'Item.dart';

class Job extends Item {
  bool _dirty = false;
  String _draftFixDescription;
  String _draftTechnician;
  Progress _draftStatus;

  Job() : super();
  Job.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes)
      : super.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes) {
    this._draftStatus = attributes["status"];
    this._draftFixDescription = attributes["fixDescription"];
    this._draftTechnician = attributes["assignedTechnician"];
  }

  void addPart(Part part) {
    //TODO - the class that calls this function, should make an API call to bind also
    this.children.add(part);
  }

  // GETTERS
  bool get dirty => this._dirty;
  get draftTechnician => this._draftTechnician;
  Progress get draftStatus => this._draftStatus;
  get draftFixDescription => this._draftFixDescription;

  get customer => this.itemAttributes["customer"];
  get phoneNumber => this.itemAttributes["phoneNumber"];
  get phoneModel => this.itemAttributes["phoneModel"];
  get jobDescription => this.itemAttributes["description"];
  get fixDescription => this.itemAttributes["fixDescription"];
  Progress get status => this.itemAttributes["status"];
  get assignedTechnician => this.itemAttributes["assignedTechnician"];
  get partsUsed => this.children;

  // SETTERS
  set dirty(bool value) => this._dirty = value;
  set draftFixDescription(value) => {this._draftFixDescription = value, this.dirty = true};
  set draftTechnician(value) => {this._draftTechnician = value, this.dirty = true};
  set draftStatus(Progress value) => {this._draftStatus = value, this.dirty = true};

  set fixDescription(value) => {this.itemAttributes["fixDescription"] = value, this.draftFixDescription = value};
  set assignedTechnician(value) => {this.itemAttributes["assignedTechnician"] = value, this.draftTechnician = value};
  set status(Progress value) => {this.itemAttributes["status"] = value, this.draftStatus = value};

  set customer(value) => this.itemAttributes["customer"] = value;
  set phoneNumber(value) => this.itemAttributes["phoneNumber"] = value;
  set phoneModel(value) => this.itemAttributes["phoneModel"] = value;
  set jobDescription(value) => this.itemAttributes["description"] = value;
  set partsUsed(value) => this.children = value;
}
