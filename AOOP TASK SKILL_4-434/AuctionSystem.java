import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Observer: Auction Observer Interface
interface AuctionObserver {
    void update(String message);
}

// Concrete Observer: Bidder
class Bidder implements AuctionObserver {
    private String name;

    public Bidder(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification to " + name + ": " + message);
    }

    public String getName() {
        return name;
    }
}

// Auction Item Class
class AuctionItem {
    private String name;
    private double currentPrice;

    public AuctionItem(String name, double startingPrice) {
        this.name = name;
        this.currentPrice = startingPrice;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double price) {
        this.currentPrice = price;
    }
}

// Template Method: Abstract Auction Class
abstract class Auction {
    protected AuctionItem item;
    protected List<AuctionObserver> observers = new ArrayList<>();
    protected boolean isActive;

    public Auction(AuctionItem item) {
        this.item = item;
        this.isActive = false;
    }

    // Template Method: Defines the auction process
    public final void conductAuction() {
        startAuction();
        acceptBids();
        endAuction();
    }

    protected void startAuction() {
        isActive = true;
        notifyObservers("Auction for " + item.getName() + " has started. Starting price: $" + item.getCurrentPrice());
    }

    protected abstract void acceptBids(); // Subclasses define bidding logic

    protected void endAuction() {
        isActive = false;
        notifyObservers("Auction for " + item.getName() + " has ended. Final price: $" + item.getCurrentPrice());
    }

    public void addObserver(AuctionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AuctionObserver observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (AuctionObserver observer : observers) {
            observer.update(message);
        }
    }

    public boolean isActive() {
        return isActive;
    }
}

// Concrete Template: English Auction (ascending bids)
class EnglishAuction extends Auction {
    private double minIncrement;

    public EnglishAuction(AuctionItem item, double minIncrement) {
        super(item);
        this.minIncrement = minIncrement;
    }

    @Override
    protected void acceptBids() {
        Scanner scanner = new Scanner(System.in);
        while (isActive) {
            System.out.println("Current price for " + item.getName() + ": $" + item.getCurrentPrice());
            System.out.println("Enter bidder name (or 'end' to close auction): ");
            String bidderName = scanner.nextLine();

            if (bidderName.equalsIgnoreCase("end")) {
                isActive = false;
                break;
            }

            System.out.println("Enter bid amount: ");
            double bidAmount;
            try {
                bidAmount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid bid amount. Try again.");
                continue;
            }

            if (bidAmount >= item.getCurrentPrice() + minIncrement) {
                item.setCurrentPrice(bidAmount);
                notifyObservers("New highest bid: $" + bidAmount + " by " + bidderName);
            } else {
                System.out.println("Bid must be at least $" + (item.getCurrentPrice() + minIncrement));
            }
        }
    }
}

// Concrete Template: Dutch Auction (descending price)
class DutchAuction extends Auction {
    private double decrement;
    private double reservePrice;

    public DutchAuction(AuctionItem item, double decrement, double reservePrice) {
        super(item);
        this.decrement = decrement;
        this.reservePrice = reservePrice;
    }

    @Override
    protected void acceptBids() {
        Scanner scanner = new Scanner(System.in);
        while (isActive && item.getCurrentPrice() >= reservePrice) {
            System.out.println("Current price for " + item.getName() + ": $" + item.getCurrentPrice());
            System.out.println("Enter bidder name to accept current price (or 'next' to decrease, 'end' to close): ");
            String bidderName = scanner.nextLine();

            if (bidderName.equalsIgnoreCase("end")) {
                isActive = false;
                break;
            } else if (bidderName.equalsIgnoreCase("next")) {
                item.setCurrentPrice(item.getCurrentPrice() - decrement);
                notifyObservers("Price decreased to $" + item.getCurrentPrice());
            } else {
                notifyObservers(bidderName + " accepted the price of $" + item.getCurrentPrice());
                isActive = false;
                break;
            }
        }
        if (item.getCurrentPrice() < reservePrice) {
            notifyObservers("Price dropped below reserve. Auction ended without a winner.");
        }
    }
}

// Main Class with User Input
public class AuctionSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter item name: ");
        String itemName = scanner.nextLine();

        System.out.println("Enter starting price: ");
        double startingPrice = Double.parseDouble(scanner.nextLine());
        AuctionItem item = new AuctionItem(itemName, startingPrice);

        System.out.println("Choose auction type (English, Dutch): ");
        String auctionType = scanner.nextLine();

        Auction auction;
        if (auctionType.equalsIgnoreCase("English")) {
            System.out.println("Enter minimum bid increment: ");
            double minIncrement = Double.parseDouble(scanner.nextLine());
            auction = new EnglishAuction(item, minIncrement);
        } else if (auctionType.equalsIgnoreCase("Dutch")) {
            System.out.println("Enter price decrement: ");
            double decrement = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter reserve price: ");
            double reservePrice = Double.parseDouble(scanner.nextLine());
            auction = new DutchAuction(item, decrement, reservePrice);
        } else {
            System.out.println("Invalid auction type. Exiting.");
            scanner.close();
            return;
        }

        System.out.println("Enter number of bidders: ");
        int numBidders = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numBidders; i++) {
            System.out.println("Enter bidder " + (i + 1) + " name: ");
            String bidderName = scanner.nextLine();
            auction.addObserver(new Bidder(bidderName));
        }

        auction.conductAuction();

        scanner.close();
    }
}