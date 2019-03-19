package edu.vcu.cmsc355.starter;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Class to hold donor information (including what they've donated) and send thank you messages
 *
 * Contributors: Justin Nelson, Cove Soyars,
 */
public class Donor {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private ArrayList<FoodItem> donated;
    private String phoneNum;
    private String address;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public String getPhoneNum() {
        return phoneNum;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Donor(String firstName, String lastName, String emailAddress, String aPhoneNum, String anAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.donated = new ArrayList<FoodItem>();
        this.phoneNum = aPhoneNum;
        this.address = anAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ArrayList<FoodItem> getDonated() {
        return donated;
    }

    /**
     * Adds a new donation to the list of what the donor has donated
     * @param newFood food to be added
     */
    public void donate(FoodItem newFood){

        int pos = checkExists(newFood);
        if(pos > -1){ // if the donor has already donated this item,
            // update its quantity
            int currentQuant = donated.get(pos).getQuantity();
            int newQuant = newFood.getQuantity() + currentQuant;

            donated.get(pos).setQuantity(newQuant);  // set new quantity
        }

        else{ // if not in list,
            donated.add(newFood); // add it
        }
    }

    /**
     * checks if a donor has already donated an item
     * @param newFood
     * @return -1 if donor hasn't already donated item, int position in donated if they have
     */
    private int checkExists(FoodItem newFood){
        if(donated.isEmpty()){ // if array list is empty then the new food isn't in there
            return -1;
        }
        for(FoodItem food : donated){
            // if food matches by name and size, then it is the same item
            if(newFood.getName().equals(food.getName()) && newFood.getSize() == food.getSize()){
                return donated.indexOf(food);
            }
        }
        return -1;
    }

    public void sendEmail(){

        Date now = new Date(); // grab current time for email message

        // generate thank you message
        String message = "Donated by: " + firstName + " " + lastName + "\n\n" + "Address: " + address
              +  "\n\n" + "Phone: " + phoneNum + "\n\n" + "Email: " + emailAddress + "\n\n" +
                "Date: " + dateFormat.format(now) + "\n\n" + "Thank you for your donation of food " +
                "for our food bank. Your generosity helps us reduce hunger in our area.\n";

        // create email sender and send
        EmailSender sender = new EmailSender(message, emailAddress,
                "postmaster@automail-canned.com", "Thank You For Your Donation");
        sender.send();
    }
}
