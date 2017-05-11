package helputils;

import java.util.Scanner;

/**
 *
 * @author Emin Bahadir Tuluce
 */
public class ExceptionParser {
    
    public static final String URL_ROOT = "https://docs.oracle.com/javase/8/docs/api/";
    
    private static String getException( String exceptionLine) {
        Scanner parser = new Scanner( exceptionLine);
        for (int i = 0; i < 4; i++) // Skip the first four words.
            parser.next();
        return parser.next();
    }
    
    public static String getExceptionLink( String exceptionLine) {
        String exception = getException( exceptionLine);
        if (exception.endsWith(":"))
            exception = exception.substring(0, exception.length()-1);
        String exceptionPath = exception.replace('.', '/');
        String exceptionLink = URL_ROOT + exceptionPath + ".html";
        return exceptionLink;
    }

}