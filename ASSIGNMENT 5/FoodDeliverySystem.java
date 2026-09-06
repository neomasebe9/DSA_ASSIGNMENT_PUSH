import java.util.Scanner;

public class FoodDeliverySystem {

    public static Order createOrder(Scanner scanner, MyLinkedList<Order> orders, String orderType) {

        String orderID;
        do {
            System.out.print("Enter Order ID: ");
            orderID = scanner.nextLine();

            if (orderExists(orders, orderID)) {
                System.out.println("Error: Order ID already exists! Enter a unique ID.");
            }
        } while (orderExists(orders, orderID) || orderID.trim().isEmpty());

        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();

        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        System.out.print("Enter Delivery Address: ");
        String deliveryAddress = scanner.nextLine();

        System.out.print("Enter Food Item: ");
        String foodItem = scanner.nextLine();

        int quantity = 0;
        do {
            System.out.print("Enter Quantity (> 0): ");
            quantity = Integer.parseInt(scanner.nextLine());
        } while (quantity <= 0);

        double pricePerItem = 0.0;
        do {
            System.out.print("Enter Price Per Item (> 0): ");
            pricePerItem = Double.parseDouble(scanner.nextLine());
        } while (pricePerItem <= 0);

        Order newOrder = new Order(orderID, customerID, customerName, contactNumber,
                deliveryAddress, foodItem, quantity, pricePerItem, orderType);

        return newOrder;
    }

    public static boolean orderExists(MyLinkedList<Order> orders, String orderID) {
        MyLinkedList<Order> temp = new MyLinkedList<>();
        boolean exists = false;

        while (orders.getFirst() != null) {
            Order curr = orders.removeFirst();
            if (curr.getOrderID().equalsIgnoreCase(orderID)) {
                exists = true;
            }
            temp.append(curr);
        }

        while (temp.getFirst() != null) {
            orders.append(temp.removeFirst());
        }

        return exists;
    }

    public static void placeOrder(Scanner scanner, MyLinkedList<Order> orders) {
        System.out.println("\n--- PLACE NORMAL ORDER ---");
        Order order = createOrder(scanner, orders, "Normal");

        orders.append(order);

        System.out.printf("Total Order Cost: R%.2f\n", order.calculateTotalCost());
        System.out.println("Normal order added successfully to the end of the queue!");
    }

    public static void placeUrgentOrder(Scanner scanner, MyLinkedList<Order> orders) {
        System.out.println("\n--- PLACE URGENT ORDER ---");
        Order order = createOrder(scanner, orders, "Urgent");

        orders.prepend(order);

        System.out.printf("Total Order Cost: R%.2f\n", order.calculateTotalCost());
        System.out.println("Urgent order added successfully with PRIORITY at the front of the queue!");
    }

    public static void processNextOrder(MyLinkedList<Order> orders) {
        System.out.println("\n--- PROCESS NEXT ORDER ---");
        if (orders.getFirst() == null) {
            System.out.println("No orders waiting to be processed.");
            return;
        }

        Order nextOrder = orders.getFirst();
        System.out.println("Processing order: " + nextOrder);
        orders.removeFirst();
        System.out.println("Order processed and removed successfully!");
    }

    public static void cancelOrder(Scanner scanner, MyLinkedList<Order> orders) {
        System.out.println("\n--- CANCEL ORDER ---");
        if (orders.getFirst() == null) {
            System.out.println("No orders available to cancel.");
            return;
        }

        System.out.print("Enter Order ID to cancel: ");
        String orderID = scanner.nextLine();

        MyLinkedList<Order> temp = new MyLinkedList<>();
        Order targetOrder = null;

        while (orders.getFirst() != null) {
            Order curr = orders.removeFirst();
            if (curr.getOrderID().equalsIgnoreCase(orderID) && targetOrder == null) {
                targetOrder = curr;
            }
            temp.append(curr);
        }

        while (temp.getFirst() != null) {
            orders.append(temp.removeFirst());
        }

        if (targetOrder == null) {
            System.out.println("Order with ID " + orderID + " does not exist.");
            return;
        }

        System.out.println("Found Order:\n" + targetOrder);
        System.out.print("Are you sure you want to cancel this order? (Y/N): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            orders.delete(targetOrder);
            System.out.println("Order cancelled successfully!");
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

    public static void searchOrder(Scanner scanner, MyLinkedList<Order> orders) {
        System.out.println("\n--- SEARCH ORDER ---");
        if (orders.getFirst() == null) {
            System.out.println("The order list is empty.");
            return;
        }

        System.out.println("1. Order ID");
        System.out.println("2. Customer ID");
        System.out.println("3. Customer Name");
        System.out.println("4. Contact Number");
        System.out.print("Choose search criteria: ");
        String option = scanner.nextLine();

        System.out.print("Enter search value: ");
        String searchValue = scanner.nextLine();

        MyLinkedList<Order> temp = new MyLinkedList<>();
        boolean found = false;

        while (orders.getFirst() != null) {
            Order curr = orders.removeFirst();
            boolean match = false;

            switch (option) {
                case "1":
                    match = curr.getOrderID().equalsIgnoreCase(searchValue);
                    break;
                case "2":
                    match = curr.getCustomerID().equalsIgnoreCase(searchValue);
                    break;
                case "3":
                    match = curr.getCustomerName().equalsIgnoreCase(searchValue);
                    break;
                case "4":
                    match = curr.getContactNumber().equalsIgnoreCase(searchValue);
                    break;
            }

            if (match) {
                System.out.println("Match Found -> " + curr);
                found = true;
            }

            temp.append(curr);
        }

        while (temp.getFirst() != null) {
            orders.append(temp.removeFirst());
        }

        if (!found) {
            System.out.println("No matching orders found.");
        }
    }

    public static void displayOrders(MyLinkedList<Order> orders) {
        System.out.println("\n--- CURRENT ORDERS ---");
        if (orders.getFirst() == null) {
            System.out.println("No orders to display.");
            return;
        }

        MyLinkedList<Order> temp = new MyLinkedList<>();
        int count = 1;

        while (orders.getFirst() != null) {
            Order curr = orders.removeFirst();
            System.out.printf("%d. [ID: %s] | Customer: %s | Item: %s | Qty: %d | Total: R%.2f | Type: %s\n",
                    count, curr.getOrderID(), curr.getCustomerName(), curr.getFoodItem(), curr.getQuantity(),
                    curr.calculateTotalCost(), curr.getOrderType());
            count++;
            temp.append(curr);
        }

        while (temp.getFirst() != null) {
            orders.append(temp.removeFirst());
        }
    }

    public static void displayFirstAndLastOrders(MyLinkedList<Order> orders) {
        System.out.println("\n--- FIRST AND LAST ORDERS ---");
        Order first = orders.getFirst();
        Order last = orders.getLast();

        if (first == null) {
            System.out.println("The order list is empty.");
        } else {
            System.out.println("FIRST ORDER:\n" + first);
            System.out.println("LAST ORDER:\n" + last);
        }
    }

    public static void clearOrders(Scanner scanner, MyLinkedList<Order> orders) {
        System.out.println("\n--- CLEAR ORDERS ---");
        if (orders.getFirst() == null) {
            System.out.println("The order list is already empty.");
            return;
        }

        System.out.print("Are you sure you want to clear all orders? (Y/N): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            orders.clear();
            System.out.println("All orders cleared successfully!");
        } else {
            System.out.println("Clear operation aborted.");
        }
    }

    public static void main(String[] args) {
        // CONSTANTS & INITIALIZATION
        Scanner scanner = new Scanner(System.in);
        MyLinkedList<Order> ORDERS = new MyLinkedList<>();
        String choice;

        do {
            System.out.println("\n=================================");
            System.out.println("FOOD DELIVERY ORDER MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Place a new order");
            System.out.println("2. Place an urgent order");
            System.out.println("3. Process the next order");
            System.out.println("4. Cancel an order");
            System.out.println("5. Search for an order");
            System.out.println("6. Display all current orders");
            System.out.println("7. Display the first and last orders");
            System.out.println("8. Clear all orders");
            System.out.println("9. Exit");
            System.out.print("Enter choice (1-9): ");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    placeOrder(scanner, ORDERS);
                    break;
                case "2":
                    placeUrgentOrder(scanner, ORDERS);
                    break;
                case "3":
                    processNextOrder(ORDERS);
                    break;
                case "4":
                    cancelOrder(scanner, ORDERS);
                    break;
                case "5":
                    searchOrder(scanner, ORDERS);
                    break;
                case "6":
                    displayOrders(ORDERS);
                    break;
                case "7":
                    displayFirstAndLastOrders(ORDERS);
                    break;
                case "8":
                    clearOrders(scanner, ORDERS);
                    break;
                case "9":
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice! Enter an option between 1 and 9.");
                    break;
            }

        } while (!choice.equals("9"));

        System.out.println("Thanks for using the system!");
    }
} // END CLASS