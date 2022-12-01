// Reference: AlarmSystem
// Git: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// Author: Paul Carter
// Contribution: Updated documentation to 2022W1 CPSC 210 CheckStyle

package exceptions;

// Represents an exception that occurs when printing a log
public class LogException extends Exception {
    public LogException() {
        super("Error printing log");
    }

    public LogException(String msg) {
        super(msg);
    }
}
