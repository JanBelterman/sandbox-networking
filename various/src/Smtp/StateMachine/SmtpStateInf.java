package Smtp.StateMachine;

// An interface that defines the generic methods of a state machine
public interface SmtpStateInf {
    // Handle the date (each implementation differently)
    void Handle(String data);
}
