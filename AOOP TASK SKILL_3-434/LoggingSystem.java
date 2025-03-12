import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Enum for Severity Levels
enum LogLevel {
    INFO, DEBUG, ERROR
}

// Log Message Class
class LogMessage {
    private LogLevel level;
    private String message;

    public LogMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}

// Strategy: Log Formatting Interface
interface LogFormatter {
    String format(LogMessage message);
}

// Concrete Strategy: Simple Formatter
class SimpleFormatter implements LogFormatter {
    @Override
    public String format(LogMessage message) {
        return "[" + message.getLevel() + "] " + message.getMessage();
    }
}

// Concrete Strategy: Detailed Formatter
class DetailedFormatter implements LogFormatter {
    @Override
    public String format(LogMessage message) {
        return java.time.LocalDateTime.now() + " [" + message.getLevel() + "] " + message.getMessage();
    }
}

// Observer: Log Subscriber Interface
interface LogSubscriber {
    void update(String formattedMessage);
}

// Concrete Observer: Console Logger
class ConsoleLogger implements LogSubscriber {
    @Override
    public void update(String formattedMessage) {
        System.out.println("Console: " + formattedMessage);
    }
}

// Concrete Observer: File Logger (simulated)
class FileLogger implements LogSubscriber {
    @Override
    public void update(String formattedMessage) {
        System.out.println("File: " + formattedMessage + " (written to log.txt)");
        // In a real app, this would append to a file
    }
}

// Chain of Responsibility: Abstract Log Handler
abstract class LogHandler {
    protected LogHandler nextHandler;
    protected List<LogSubscriber> subscribers = new ArrayList<>();
    protected LogFormatter formatter;

    public LogHandler(LogFormatter formatter) {
        this.formatter = formatter;
    }

    public void setNext(LogHandler next) {
        this.nextHandler = next;
    }

    public void addSubscriber(LogSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void handle(LogMessage message) {
        if (canHandle(message.getLevel())) {
            String formattedMessage = formatter.format(message);
            notifySubscribers(formattedMessage);
        }
        if (nextHandler != null) {
            nextHandler.handle(message); // Pass to next handler
        }
    }

    protected abstract boolean canHandle(LogLevel level);

    private void notifySubscribers(String formattedMessage) {
        for (LogSubscriber subscriber : subscribers) {
            subscriber.update(formattedMessage);
        }
    }
}

// Concrete Handler: Info Handler
class InfoHandler extends LogHandler {
    public InfoHandler(LogFormatter formatter) {
        super(formatter);
    }

    @Override
    protected boolean canHandle(LogLevel level) {
        return level == LogLevel.INFO;
    }
}

// Concrete Handler: Debug Handler
class DebugHandler extends LogHandler {
    public DebugHandler(LogFormatter formatter) {
        super(formatter);
    }

    @Override
    protected boolean canHandle(LogLevel level) {
        return level == LogLevel.DEBUG;
    }
}

// Concrete Handler: Error Handler
class ErrorHandler extends LogHandler {
    public ErrorHandler(LogFormatter formatter) {
        super(formatter);
    }

    @Override
    protected boolean canHandle(LogLevel level) {
        return level == LogLevel.ERROR;
    }
}

// Logging System Facade (optional, for simplicity)
class Logger {
    private LogHandler chain;

    public Logger(LogFormatter formatter) {
        // Set up the chain
        LogHandler infoHandler = new InfoHandler(formatter);
        LogHandler debugHandler = new DebugHandler(formatter);
        LogHandler errorHandler = new ErrorHandler(formatter);

        infoHandler.setNext(debugHandler);
        debugHandler.setNext(errorHandler);
        this.chain = infoHandler;

        // Add subscribers
        infoHandler.addSubscriber(new ConsoleLogger());
        debugHandler.addSubscriber(new ConsoleLogger());
        errorHandler.addSubscriber(new ConsoleLogger());
        errorHandler.addSubscriber(new FileLogger()); // Errors also go to file
    }

    public void log(LogLevel level, String message) {
        chain.handle(new LogMessage(level, message));
    }
}

// Main Class with User Input
public class LoggingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose log formatter (Simple, Detailed): ");
        String formatterType = scanner.nextLine();
        LogFormatter formatter = formatterType.equalsIgnoreCase("Detailed") 
            ? new DetailedFormatter() 
            : new SimpleFormatter();

        Logger logger = new Logger(formatter);

        while (true) {
            System.out.println("Enter log level (INFO, DEBUG, ERROR) or 'exit' to quit: ");
            String levelInput = scanner.nextLine();

            if (levelInput.equalsIgnoreCase("exit")) {
                break;
            }

            LogLevel level;
            try {
                level = LogLevel.valueOf(levelInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid log level. Try again.");
                continue;
            }

            System.out.println("Enter log message: ");
            String message = scanner.nextLine();

            logger.log(level, message);
        }

        scanner.close();
    }
}