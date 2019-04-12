package edu.vcu.cmsc355.starter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;

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
    private int counter;
    private int depletion;


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
        setDepletion(0);
        setCounter(0);


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

    public void setDepletion(int d) {
        depletion = d;
    }

    public int getDepletion() {
        return depletion;
    }

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


    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
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

        if(this.getExprDate().equals("n/a")){
            return false;
        }
        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // months start at 0 lol
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);


        String[] splitByDash = exprDate.split("/");

        int itemMonth = Integer.parseInt(splitByDash[0]);
        int itemDay = Integer.parseInt(splitByDash[1]);
        int itemYear = Integer.parseInt(splitByDash[2]);

        if (nowMonth > itemMonth || nowYear > itemYear) { // if the item's month/year is passed the current month/year
            return true; //expired
        } else if (nowDay -2 >= itemDay && nowMonth == itemMonth) {  // if the item has the same month, but the day is
            // more than 2 days greater,
            return true; // expired
        } else {
            return false;
        }

    }




    }


