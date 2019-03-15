/**
 * @version 0.2 of Manager class
 * Has method to verify a volunteer object
 * Needs:
 *  method to add storage locations (depends on food database)
 *  method to remove volunteers (depends on volunteer database)
 *
 */

package edu.vcu.cmsc355.starter;
import java.io.File;

public class Manager extends User {

    public Manager(){ // default constructor
        super();
    }
    //parameterized constructor:
    public Manager(String aPassword, String aUserName, String aFirstName, String aLastName, int aDob,
                     String anEmail, File aPicture){

        super(aPassword, aUserName, aFirstName, aLastName,aDob,anEmail, aPicture);

    }
    // method to verify volenteer objects
    public void verifyVolunteer(Volunteer aVol){
        aVol.verify();
    }
}
