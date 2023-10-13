package finalproject;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class LogBook {
    private static List<LogEntry> logEntries;

    // creating constructor with array so that it can accept any amount of entries
    public LogBook() {
        logEntries = new ArrayList<>();
    }

    // Method to add a new log entry
    public void addLogEntry(LogEntry logEntry) {
        logEntries.add(logEntry);
    }

    // Method to get all log entries
    public List<LogEntry> getAllLogEntries() {
        return logEntries;
    }
    
    // method to get total calories burnt from each of the entries
    public double calculateTotalCaloriesBurnt() {
        double totalCaloriesBurnt = 0.0;
        for (LogEntry entry : logEntries) {
            totalCaloriesBurnt += entry.getCalories();
        }
        return totalCaloriesBurnt;
    }
}