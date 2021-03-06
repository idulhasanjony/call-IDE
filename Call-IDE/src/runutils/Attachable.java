package runutils;

/**
 * An interface to make connection between ConsoleCore
 * and its parent frame for dispatching functionality.
 * @author Emin Bahadir Tuluce
 * @version 1.0
 */
public interface Attachable {
    
    /** Attaches the console to the frame. */
    void attachConsole();
    
}
