package edu.vcu.cmsc355.starter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/***
 * Class to hold information about food items
 *
 * May need more parameters....
 *
 * contributors: Cove Soyars, Justin Nelson,
 */
public class FoodItem implements Serializable {

    private String category;
    private String name;
    private String size;
    private String dateRecieved;
    private String exprDate;
    private int quantity;
    private int threshold;
    private String location;


    /**
     * default constructor
     */
    public FoodItem(){
        setCategory("");
        setSize("");
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
     * @param exprDate
     * @param quantity
     * @param threshold
     */
    public FoodItem(String category, String name, String size, String exprDate,
                    int quantity, int threshold) {
        this.category = category;
        this.name = name;
        this.size = size;
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
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public int compareTo(FoodItem two){
        if(this.getCategory().compareTo(two.getCategory()) == 0){
            return(this.getName().compareTo(two.getName()));
        }
        else{
            return this.getCategory().compareTo(two.getCategory());
        }
    }

    // determines if item is expired
    public boolean isExpired(){

        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // months start at 0 lol
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        nowYear = nowYear % 100;

        int itemMonth = Integer.parseInt(exprDate.substring(0,2));
        int itemDay = Integer.parseInt(exprDate.substring(2,4));
        int itemYear = Integer.parseInt(exprDate.substring(4));

        if(nowMonth > itemMonth || nowYear > itemYear){ // if the item's month/year is passed the current month/year
            return true; //expired
        }

        else if(nowDay + 2  <= itemDay && nowMonth  == itemMonth){  // if the item has the same month, but the day is
            // more than 2 days greater,
            return true; // expired
        }

        else{
            return false;
        }

    }

}
