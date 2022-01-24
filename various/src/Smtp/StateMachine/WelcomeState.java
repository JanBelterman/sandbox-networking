package Smtp.StateMachine;

import Smtp.SmtpContext;

public class WelcomeState implements SmtpStateInf {
    // Simple mail transfer protocol context
    SmtpContext context;
    // Constuctor
    public WelcomeState(SmtpContext smtpContext) {
        // Set context
        context=smtpContext;
        // Send data to context (send date method)
        context.SendData("220 smtp.example.com Welcome at this amazing smtp server!");
    }

    @Override
    // Handle method
    public void Handle(String data) {
        // If data starts with "Helo "
        if(data.toUpperCase().startsWith("HELO ")) {
            // Set context host to data(from index 5
            context.SetHost(data.substring(5));
            // Send data to context
            context.SendData("250 Hello " + context.GetHost() + ", I am glad to meet you");
            // Set next state for context
            context.SetNewState(new WaitForMailFromState(context));
            return;
        }
        // If date start with "QUIT"
        if(data.toUpperCase().startsWith("QUIT")) {
            // Send "221 Bye to sockets output via context helper method"
            context.SendData("221 Bye");
            // Call the disconnect helper method from context
            context.DisconnectSocket();
            return;
        }
        // Otherwise send an error message to context
        context.SendData("503 Error...");
    }
}
