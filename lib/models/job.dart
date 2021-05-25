import 'Item.dart';

class Job extends Item {
  get customer => this.itemAttributes["customer"];
  get phoneNumber => this.itemAttributes["phoneNumber"];
  get phoneModel => this.itemAttributes["phoneModel"];
  get jobDescription => this.itemAttributes["description"];
  get status => this.itemAttributes["status"];
  get assignedTechnician => this.itemAttributes["assignedTechnician"];

  set customer(value) => this.itemAttributes["customer"] = value;
  set phoneNumber(value) => this.itemAttributes["phoneNumber"] = value;
  set phoneModel(value) => this.itemAttributes["phoneModel"] = value;
  set jobDescription(value) => this.itemAttributes["description"] = value;
  set status(value) => this.itemAttributes["status"] = value;
  set assignedTechnician(value) => this.itemAttributes["assignedTechnician"] = value;
}
