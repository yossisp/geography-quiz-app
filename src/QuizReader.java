/*
@author - Yosef Spektor

this is a helper class which only has one method that allows us to read input from a file.
we suppose that the file input is correct and according to the conventions that were set in the maman.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class QuizReader {
    private String filePath; //path to the file to be read

    //each element of the list contains a line from the file
    private ArrayList<String> linesList;

    //constructor
    public QuizReader(String filePath) {
        this.filePath = filePath;
        this.linesList = new ArrayList<>();
    }

    public String[] readFile() {
        String errorMsg;
        String[] result = null;
        String line;

        //we're using try-with-resources so we don't need a finally block to close the file
        try(Scanner input = new Scanner(new File(this.filePath))) {
            int i;

            //as long as there's another line in file we read it and add to our linesList
            for(i = 0; input.hasNextLine(); i++) {
                line = input.nextLine();
                this.linesList.add(line);
            }

            //now we know how many lines are in the file
            result = new String[this.linesList.size()];
            for(i = 0; i < this.linesList.size(); i++) {
                result[i] = this.linesList.get(i);
            }

            //we catch possible exceptions as in the book on page 698
        } catch (IOException e) {
            errorMsg = "Error opening file. Exiting program";
            System.err.println(errorMsg);
            Utils.showMessageDialog(QuizApp.frame, Utils.FILE_READ_ERROR_STR, Utils.ERROR_TITLE_STR);
            System.exit(Utils.BAD_EXIT);
        } catch (NoSuchElementException e) {
            errorMsg = "File improperly formed. Exiting program";
            System.err.println(errorMsg);
            Utils.showMessageDialog(QuizApp.frame, Utils.FILE_READ_ERROR_STR, Utils.ERROR_TITLE_STR);
            System.exit(Utils.BAD_EXIT);
        } catch (IllegalStateException e) {
            errorMsg = "Error reading from file. Exiting program";
            System.err.println(errorMsg);
            Utils.showMessageDialog(QuizApp.frame, Utils.FILE_READ_ERROR_STR, Utils.ERROR_TITLE_STR);
            System.exit(Utils.BAD_EXIT);
        }
        //in the maman forum we're told that we can expect valid input so the string shouldn't be null
        return result;
    }
}
