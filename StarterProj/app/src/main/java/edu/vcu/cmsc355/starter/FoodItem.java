package edu.vcu.cmsc355.starter;

/***
 * Class to hold information about food items
 *
 * May need more parameters....
 *
 * contributors: Cove Soyars, Justin Nelson,
 */
public class FoodItem {

    private String category;
    private String name;
    private double size;
    private String dateRecieved;
    private String exprDate;
    private int quantity;
    private int threshold;


    /**
     * default constructor
     */
    public FoodItem(){
        setCategory("");
        setSize(0.0);
        setDateRecieved("");
        setExprDate("");
        setQuantity(0);
        setThreshold(0);
        setName("");

    }

    /**
     * Parameterized constructor
     * @param category
     * @param name
     * @param size
     * @param dateRecieved
     * @param exprDate
     * @param quantity
     * @param threshold
     */
    public FoodItem(String category, String name, double size, String dateRecieved, String exprDate,
                    int quantity, int threshold) {
        this.category = category;
        this.name = name;
        this.size = size;
        this.dateRecieved = dateRecieved;
        this.exprDate = exprDate;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    /*
     * Getters and setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getDateRecieved() {
        return dateRecieved;
    }

    public void setDateRecieved(String dateRecieved) {
        this.dateRecieved = dateRecieved;
    }

    public String getExprDate() {
        return exprDate;
    }

    public void setExprDate(String exprDate) {
        this.exprDate = exprDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
