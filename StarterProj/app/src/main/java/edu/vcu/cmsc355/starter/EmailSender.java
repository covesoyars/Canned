package edu.vcu.cmsc355.starter;
/**
 * Class that sends an automatic email.
 *
 * contributers: Cove Soyars,
 */

import  static java.net.HttpURLConnection.HTTP_OK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailSender{
    private String message;
    private String to;
    private String from;
    private String subject;


    // constructor
    public EmailSender(String aMessage, String aTo, String aFrom, String aSubject){
        setFrom(aFrom);
        setMessage(aMessage);
        setSubject(aSubject);
        setTo(aTo);
    }
    // getters and setters for all variables:
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Sends the email
     */
    public void send(){
        RetrofitClient.getInstance()
                .getApi()
                .sendEmail(from, to, subject, message)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == HTTP_OK) {
                            try {
                                JSONObject obj = new JSONObject(response.body().string());
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }
}