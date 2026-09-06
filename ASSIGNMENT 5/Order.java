public class Order implements Comparable<Order> {

    // INSTANCE FIELDS
    private String orderID;
    private String customerID;
    private String customerName;
    private String contactNumber;
    private String deliveryAddress;
    private String foodItem;
    private int quantity;
    private double pricePerItem;
    private String orderType;

    // CONSTRUCTORS
    public Order() {
        this.orderID = "";
        this.customerID = "";
        this.customerName = "";
        this.contactNumber = "";
        this.deliveryAddress = "";
        this.foodItem = "";
        this.quantity = 0;
        this.pricePerItem = 0.0;
        this.orderType = "Normal";
    }

    public Order(String orderID, String customerID, String customerName, String contactNumber,
            String deliveryAddress, String foodItem, int quantity, double pricePerItem, String orderType) {
        setOrderID(orderID);
        setCustomerID(customerID);
        setCustomerName(customerName);
        setContactNumber(contactNumber);
        setDeliveryAddress(deliveryAddress);
        setFoodItem(foodItem);
        setQuantity(quantity);
        setPricePerItem(pricePerItem);
        setOrderType(orderType);
    }

    // Constructor for ID searching/comparisons
    public Order(String orderID) {
        this.orderID = orderID;
    }

    // TO-STRING
    public String toString() {
        String myString = String.format(
                "Order ID: %s | Customer: %s (ID: %s) | Contact: %s | Address: %s | Item: %s | Quantity: %d | Total Cost: R%.2f | Type: %s",
                this.orderID, this.customerName, this.customerID, this.contactNumber, this.deliveryAddress,
                this.foodItem, this.quantity, calculateTotalCost(), this.orderType);

        return myString;
    }

    // ACCESSOR METHODS
    public String getOrderID() {
        return this.orderID;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public String getFoodItem() {
        return this.foodItem;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPricePerItem() {
        return this.pricePerItem;
    }

    public String getOrderType() {
        return this.orderType;
    }

    // MUTATOR METHODS
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    // AUXILLARY METHODS
    public double calculateTotalCost() {
        return this.quantity * this.pricePerItem;
    }

    @Override
    public int compareTo(Order other) {
        return this.orderID.compareToIgnoreCase(other.getOrderID());
    }
}