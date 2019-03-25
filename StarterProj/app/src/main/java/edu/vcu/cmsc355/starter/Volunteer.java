/**
 * @version 0.2 of Volunteer class
 * Has verification scheme to allow manager to control which accounts are operable
 *
 * @contributors: Cove Soyars,
 */

package edu.vcu.cmsc355.starter;
import java.io.File;

public class Volunteer extends User {

    private boolean verified; // flag to determine if user is verified by manager

    public Volunteer() { // default constructor
        super();
        verified = false;
    }

    // parameterized constructor with image
    public Volunteer(String aPassword, String aUserName, String aFirstName, String aLastName, int aDob,
                     String anEmail, File aPicture){

        super(aPassword, aUserName, aFirstName, aLastName,aDob,anEmail, aPicture);
        verified = false; // volunteers will initially be unverified
    }

    // parameterized constructor without photo parameter (user accepts default photo)
    public Volunteer(String aPassword, String aUserName, String aFirstName, String aLastName, int aDob,
                String anEmail){
        super(aPassword, aUserName, aFirstName, aLastName, aDob, anEmail);
    }

    // will be called by a method in Manager class to register new users
    public void verify(){
        verified = true;
    }
    // will be used to display appropriate hub page and sort into verified/unverified volunteers
    public boolean isVerified(){
        return verified;
    }
}
