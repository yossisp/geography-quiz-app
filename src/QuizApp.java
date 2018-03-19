/*
@author - Yosef Spektor

this is the main class which initiates the quiz.

Assumptions:
1) we assume the input is valid. we assume the file not to
be empty.
2) each question/answer takes up one line and ends with newline.
3) the first answer is the correct answer.

App flow:
1) the app starts with the first question. the app displays one question at a time.
2) in order to see the next question next button must be pressed.
3) if the user didn't click an answer button (multiple choice letters)
we alert the user by displaying a dialog message: the user can go back or continue (in this case the question
will be considered as answered incorrectly).
4) if there're remaining questions that the user still didn't see but already clicked finished button
we alert the user by displaying dialog message: the user can go back and finish the remaining questions
or continue to answers feedback stage (in this case all the questions that were skipped by the user will be
considered as answered incorrectly).
5) the user can click answer buttons as many times as they want before clicking next button or finished button.
only the last selection will be considered as user final answer.
6) if the user selects an answer and immediately wants to finish the quiz (while unanswered questions
still remain) we'll alert the user as in point 4, but if the user wants to finish we'll record the button that
was pressed as answer.
7) after user presses finished button when all questions finished (or in point 4) we
display a new panel which shows the results of the quiz with in/correct label next to each quesion.
the user is given the score and can take the quiz again or close the app.

Classes overview:
QuizReader - reads the input file and return the contents
Utils - contains global constants and methods
Question - represents a single question (including its answers)
Quiz - represents the quiz, contains all questions in the quiz
QuizPanel - starts the quiz, accepts user answers
CheckAnswersPanel - show the results of the quiz to the user. allow to start the quiz again
 */

import javax.swing.*;

public class QuizApp {
    //path to the text file which contains the quiz
    private final static String FILE_PATH = "../resources/quiz.txt";


    //the frame is global so that it can be used from different classes in order to change panels
    //(quiz panel at first then results panel)
    public static JFrame frame = new JFrame(Utils.APP_NAME);

    public static void main(String[] args) {
        startQuiz(frame);
    }

    /*
    the method start the quiz displaying the first question.
     */
    public static void startQuiz(JFrame frame) {
        QuizReader r = new QuizReader(FILE_PATH);
        String[] text = r.readFile(); //we read the contents of the text file

        Quiz q = new Quiz(text); //we create Quiz based on the contents of the text file

        QuizPanel p = new QuizPanel(q);
        frame.setContentPane(p);

        p.getFirstQuestion(); //this way we start immediately from the first question

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Utils.FRAME_WIDTH, Utils.START_FRAME_HEIGHT);

        /*
        because the panel may be starting after results panel, this means that
        the results panel was first removed, now we're setting new panel and in order for
        the panel to be displayed correctly we need to validate() and repaint() the new panel
         */
        frame.validate();
        frame.repaint(); // prefer to write this always.

        frame.setVisible(true);
    }

    /*
    the method displays the quiz results
     */
    public static void showCheckAnswersPanel(JFrame frame, Quiz quiz) {
        CheckAnswersPanel checkAnswersPanel = new CheckAnswersPanel(quiz);

        //potentially there may be a lot of question so we add a scroll bar just in case
        JScrollPane scrollPane = new JScrollPane(checkAnswersPanel);

        frame.setContentPane(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Utils.FRAME_WIDTH, Utils.START_FRAME_HEIGHT);

        /*
        because the panel will be starting after QuizPanel, this means that
        the QuizPanel was first removed, now we're setting new panel and in order for
        the panel to be displayed correctly we need to validate() and repaint() the new panel
         */
        frame.validate();
        frame.repaint();
        frame.setSize(Utils.FRAME_WIDTH, Utils.CHECK_ANSWERS_FRAME_HEIGHT);
        frame.setVisible(true);
    }
}
