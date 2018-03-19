/*
@author - Yosef Spektor

the class contains constants and methods which are used by multiple classes.
 */
import javax.swing.*;
import java.awt.*;

public class Utils {
    //messages and warnings strings
    public final static String FILE_READ_ERROR_STR = "Error reading file.\n\nPlease make sure the file exists " +
            "and/or is not corrupted.\n\nThe program will now exit.";
    public final static String INTERNAL_APP_CRASH_ERROR_STR = "Fatal error.\n\nThe program will now exit.";
    public final static String NO_ANSWER_BUTTON_CLICKED_WARNING = "You didn't select any answer.\n" +
            "Would you like to go back and select one?";
    public final static String NOT_ALL_QUESTIONS_SEEN_WARNING = "There're still more questions to be answered\n" +
            "Are you sure that you want to finish the quiz?";
    public final static String EMPTY_FILE_ERROR = "Quiz file is empty. Exiting the app";
    public final static String ERROR_TITLE_STR = "QuizApp: Error";
    public final static String WARNING_TITLE_STR = "QuizApp: Warning";
    public final static String APP_NAME = "Quiz App";

    public final static int BAD_EXIT = 1; //means that a fatal error occurred in the app
    public final static int ZERO_QUESTIONS = 0; //used in Quiz to initialize question counters

    //global quiz constants
    public final static int LINES_PER_QUESTION = 5;
    public final static int ANSWERS_PER_QUESTION = 4;
    public final static int FRAME_WIDTH = 870;
    public final static int START_FRAME_HEIGHT = 270;
    public final static int CHECK_ANSWERS_FRAME_HEIGHT = 500;
    public final static Font HEADER_FONT = new Font("Arial", Font.ITALIC, 22);
    public final static Font BUTTON_FONT = new Font("Arial", Font.BOLD, 15);
    public final static Font TEXT_FONT = new Font("Arial", Font.PLAIN, 16);

    //when we want to get quantity of items from index of an item in an array we need to add 1
    public final static int ARRAY_INDEX_OFFSET = 1;

    //used in several class to describe columns/row subscript
    public final static int FIRST_COL = 0;
    public final static int FIRST_ROW = 0;
    public final static int SECOND_COL = 1;

    //strings that signify whether the answer is in/correct
    public final static String CORRECT_ANSWER_STRING = "y";
    public final static String INCORRECT_ANSWER_STRING = "n";

    //we'll store each of the four answers in String[ROWS][COLUMNS] object,
    //where String[Utils.FIRST_ROW][Utils.FIRST_COL] contains the answer string
    //while [Utils.FIRST_ROW][Utils.SECOND_COL] contains the string which says whether the text is correct
    //or not. COLUMNS is also used for CheckAnswersPanel.fillAnswersFeedbackPanel().
    public final static int COLUMNS = 2;
    public final static int ROWS = 1;


    //wrapper method
    public static void showMessageDialog(JFrame f, String message, String title) {
        String[] options = {"OK"};
        JOptionPane.showOptionDialog(f, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
    }

    //wrapper method
    public static int showConfirmDialog(JFrame f, String message, String title) {
        return JOptionPane.showConfirmDialog(f, message, title, JOptionPane.YES_NO_OPTION);
    }
}
