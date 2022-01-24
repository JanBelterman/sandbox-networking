package Smtp;


import Smtp.StateMachine.SmtpStateInf;
import Smtp.StateMachine.WelcomeState;

import java.net.*;
import java.io.*;
import java.util.*;

public class SmtpContext extends Thread {
    //statemachine attributen
    // Current state machine
    private SmtpStateInf statemachine;
    // Queued state machine
    private SmtpStateInf setNewState;

    //socket attributen
    // Socket
    private Socket socket = null;
    // Socket output
    private PrintWriter out;

    //mail attributen
    // Hostname
    private String hostname;
    // Mail from:
    private String mailFrom;
    // Recipients
    private List<String> rcptTo = new ArrayList<String>();
    // The body
    private StringBuilder body = new StringBuilder();

    public SmtpContext(Socket socket) {
        // Send "SmtpContextThread to super class?
        super("SmtpContextThread");
        // Take the socket and store it
        this.socket = socket;
        try {
            // Set a printWriter to the output stream
            out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Set first stateMachine to welcome state
        statemachine = new WelcomeState(this);
    }

    /*
        STATE MANIPULATION
        ==================
     */
    public void SetNewState(SmtpStateInf state){
        // Set queue state to given state
        setNewState = state;
    }

    private void applyNewState(){
        // Check if queue state != null
        if(setNewState!=null)
        {
            // Change current state to queue state
            statemachine=setNewState;
            // Set queue state to null
            setNewState=null;
        }
    }

    /*
        THREADED INPUT PROCESSING
        =========================
     */
    public void run() {
        // Get sockets input stream
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            // Define input line
            String inputLine;
            // While (define input line = sockets input stream.readLine) != null)
            while ((inputLine = in.readLine()) != null) {
                // Delegate handling of the sockets input to the state machine
                statemachine.Handle(inputLine);
                // Print to terminal "C: + the sockets input
                System.out.println("C: " + inputLine);
                // If sockets input.upperCase() equals "QUIT" than stop the loop
                if (inputLine.toUpperCase().equals("QUIT"))
                    break;
                applyNewState(); // Apply queued state and set it as the current state
            }
        }
        // Catch and throw error
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        SOCKETSTREAM MANIPULATION
        =========================
     */
    // Send data to socket
    public void SendData(String data){
        // Send the date to the sockets output stream
        out.println(data);
        // Print S: + the data
        System.out.println("S: " + data);
    }

    // Disconnect from socket
    public void DisconnectSocket() {
        // Try to close the socket
        try {
            socket.close();
        }
        // Catch and throw error
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
        GETTERS EN SETTERS
        ==================
     */
    // Set hostname
    public void SetHost(String hostname) {
        this.hostname = hostname;
    }
    // Get hostname
    public String GetHost() {
        return hostname;
    }
    // Set mail from:
    public void SetMailFrom(String mailFrom) {
        this.mailFrom=mailFrom;
    }
    // Get mail from:
    public String GetMailFrom() {
        return mailFrom;
    }
    // Set recipient (ontvanger)
    public void AddRecipient(String recipient){
        rcptTo.add(recipient);
    }
    // Add text to body
    public void AddTextToBody(String text) {
        body.append(text);
    }

}
