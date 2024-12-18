import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditLog {
    private List<String> logs = new ArrayList<>();

    public void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        logs.add(timestamp + ": " + message);
        System.out.println(timestamp + ": " + message);
    }

    public void showLogs() {
        System.out.println("\n=== Audit Logs ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}
