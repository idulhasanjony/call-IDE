package fileoperations;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A class to save text files.
 * @author Emin Bahadir Tuluce
 * @version 1.0
 */
public class FileSaver {
    
    /** The file to save the contents */
    private File file;
    
    /**
     * Creates an instance of FileSaver with the specified file.
     * @param file the file to save the contents
     */
    public FileSaver( File file) {
        this.file = file;
    }
    
    /** @param file the file to save the contents */
    public void setPath( File file) {
        this.file = file;
    }
    
    /**
     * Saves the contents to the specified file.
     * @param content the contents to save
     * @throws IOException if the writing is failed
     */
    public void save( String content) throws IOException {
        saveAs(content, file);
    }
    
    /**
     * Saves the contents to the given file.
     * @param content the contents to save
     * @param diffFile a different file to save the contents
     * @throws IOException if the writing is failed
     */
    public void saveAs( String content, File diffFile) throws IOException {
        PrintWriter output = new PrintWriter( diffFile);
        output.print( content);
        output.close();
    }
    
    public File getFile() {
        return file;
    }
    
    public String getContent() throws FileNotFoundException
    {
        Scanner scan = new Scanner(  file );
        String content = "";
        while (scan.hasNext())
        {
            content.concat( scan.nextLine() + "\n");
        }
        return content;        
    }
    
}
