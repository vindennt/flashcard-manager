// Reference: JsonSerializationDemo
// Git: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Author: Paul Carter
// Contribution: Method templates for all in this class

package persistence;

import org.json.JSONObject;


// Represents the behaviour of a class that can be saved to a json
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
