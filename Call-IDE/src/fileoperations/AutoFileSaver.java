package fileoperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.text.JTextComponent;

/**
 * A class to save text files periodically.
 * @author Emin Bahadir Tuluce
 * @version 1.0
 */
public class AutoFileSaver implements ActionListener {
    
    private JTextComponent contentSource;
    private AutosaveHandler outputTarget;
    private FileSaver fileSaver;
    private Timer timer;
    
    /**
     * Creates a AutoFileSaver with the given source period, then starts the timer.
     * @param fileSaver the FileSaver object to use in saving
     * @param contentSource the source component to take content periodically
     * @param period the saving periods in miliseconds
     */
    public AutoFileSaver( FileSaver fileSaver, JTextComponent contentSource,
            AutosaveHandler outputTarget, int period) {
        this.fileSaver = fileSaver;
        this.contentSource = contentSource;
        this.outputTarget = outputTarget;
        timer = new Timer( period, this);
        start();
    }
    
    /** Stops the autosaving. */
    public void stop() {
        timer.stop();
    }
    
    /** Starts the autosaving. */
    public void start() {
        timer.start();
    }
    
    public void setFileSaver( FileSaver fileSaver) {
        this.fileSaver = fileSaver;
    }
    
    public void setContentSource( JTextComponent contentSource) {
        this.contentSource = contentSource;
    }
    
    public void setPeriod( int period) {
        timer.setDelay( period);
        timer.restart();
    }
    
    public void actionPerformed( ActionEvent e) {
        try {
            String content = contentSource.getText();
            fileSaver.save( content);
            outputTarget.report( fileSaver.getFile(), content);
        } catch ( Exception exception) {
            exception.printStackTrace();
        }
    }
    
}
