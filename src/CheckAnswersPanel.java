/*
@author - Yosef Spektor

the class creates the panel which displays the results of the quiz in a table where
to the right of each question there's in/correct information. At the bottom the quiz score
is displayed and TRY_AGAIN_BUTTON_STR which if clicked will start the quiz again.
 */

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckAnswersPanel extends JPanel implements ActionListener {
    //----CONSTANTS
    private final static String HEADER = "Below is the list of questions and your answers:";
    private final static String CORRECT_ANSWER_STR = "CORRECT";
    private final static String INCORRECT_ANSWER_STR = "INCORRECT";
    private final static int GRID_BUTTONS_PADDING = 5;
    private final static int ANSWERS_BORDER_VAL = 1;
    private final static String PERCENTAGE_SIGN_STR = "%";
    private final static int ONE_HUNDERED = 100; //used to convert fraction to percentage
    private final static String TRY_AGAIN_BUTTON_STR = "Try again";

    //instance fields
    private Quiz quiz;
    private JLabel scoreLabel;
    private JPanel answersFeedbackPanel; //displays the questions and in/correct feedback next to each question

    //constructor
    public CheckAnswersPanel(Quiz quiz) {
        //intialization stage of fields and local variables
        this.quiz = quiz;
        this.setQuizScore();
        JPanel checkAnswersPanel = new JPanel(); //the main panel which contains other panels

        JButton anotherTryButton = new JButton(TRY_AGAIN_BUTTON_STR);
        anotherTryButton.setFont(Utils.BUTTON_FONT);
        JPanel anotherTryButtonPanel = new JPanel();
        anotherTryButtonPanel.add(scoreLabel);
        anotherTryButtonPanel.add(anotherTryButton);

        JLabel headerLabel = new JLabel(HEADER);
        headerLabel.setFont(Utils.HEADER_FONT);
        JPanel headerPanel = new JPanel();
        headerPanel.add(headerLabel);

        this.fillAnswersFeedbackPanel(); //displays questions and in/correct answer information

        checkAnswersPanel.setLayout(new BorderLayout());
        checkAnswersPanel.add(headerPanel, BorderLayout.NORTH);
        checkAnswersPanel.add(this.answersFeedbackPanel, BorderLayout.CENTER);
        checkAnswersPanel.add(anotherTryButtonPanel, BorderLayout.SOUTH);
        this.add(checkAnswersPanel);

        //there's only one button, we add the listener this way because we implemented ActionListener interface
        anotherTryButton.addActionListener(this);
    }

    /*
    initializes this.answersFeedbackPanel in order to display the questions and information
    whether they were correct or not.
     */
    private void fillAnswersFeedbackPanel() {
        int i;
        Question q;
        JLabel questionLabel, answerLabel;
        this.answersFeedbackPanel = new JPanel();
        this.answersFeedbackPanel.setLayout(new GridLayout(this.quiz.getQuestionsQty(), Utils.COLUMNS,
                GRID_BUTTONS_PADDING, GRID_BUTTONS_PADDING));

        //each answer in the grid will be surrounded by this border
        MatteBorder border = new MatteBorder(ANSWERS_BORDER_VAL, ANSWERS_BORDER_VAL,
                ANSWERS_BORDER_VAL, ANSWERS_BORDER_VAL, Color.BLACK);

        int nextQuestionId; //helper variable which means the next question to be handled in the loop below
        for(i = 0; i < quiz.getQuestionsQty(); i++) {
            //we want to get the last item of the array because the panel adds new items on top
            //this way if we get the items in reverse order they will appear in correct order
            //we need to offset our index by ARRAY_INDEX_OFFSET in order to not get
            //out of bounds exception
            nextQuestionId = this.quiz.getQuestionsQty() - i - Utils.ARRAY_INDEX_OFFSET;

            //we set the visual representation of question and in/correct information
            q = this.quiz.getQuestionById(nextQuestionId);
            questionLabel = new JLabel(q.getQuestionText());
            questionLabel.setBorder(border);
            questionLabel.setFont(Utils.TEXT_FONT);

            //we use GridLayout subscript to enter questions only to the first column
            //(otherwise we'd fill the upper half of the grip with only questions
            //while the lower half would be filled with in/correct labels)
            this.answersFeedbackPanel.add(questionLabel, i, Utils.FIRST_COL);
            if(q.getAnswerStatus() == Question.AnswerStatus.CORRECT) {
                answerLabel = new JLabel(CORRECT_ANSWER_STR);
                answerLabel.setForeground(Color.GREEN.darker());
            } else {
                answerLabel = new JLabel(INCORRECT_ANSWER_STR);
                answerLabel.setForeground(Color.RED);
            }
            answerLabel.setBorder(border);

            //we use GridLayout subscript to enter questions only to the first column
            //(otherwise we'd fill the upper half of the grip with only questions
            //while the lower half would be filled with in/correct labels)
            this.answersFeedbackPanel.add(answerLabel, i, Utils.SECOND_COL);
        }
    }

    //sets quiz score to this.scoreLabel
    private void setQuizScore() {
        String scoreString;
        double score = (double) this.quiz.getCorrectlyAnsweredCounter() / this.quiz.getQuestionsQty();
        score *= ONE_HUNDERED; //this in order to get percentage format
        scoreString = String.format("Your quiz score is %.2f", score) + PERCENTAGE_SIGN_STR;
        this.scoreLabel = new JLabel(scoreString);
    }

    /*
    we have to implement the ActionListener interface. there's only one button
    so we don't need to use getSource() in order to identify which component triggered the event
     */
    public void actionPerformed(ActionEvent e) {
        /*
        if the user triggers anotherTryButton then we want to start the quiz again.
        Because CheckAnswersPanel object uses the same frame as QuizApp.startQuiz()
        we need to first remove the CheckAnswersPanel.

        Also we don't need to reset the Quiz object because QuizApp.startQuiz()
        starts with a new Quiz object. The quiz should not a be huge file which would take considerable
        time to read itself, therefore we can allow ourselves to start each time with a new Quiz object
         */
        QuizApp.frame.remove(this);
        QuizApp.startQuiz(QuizApp.frame);
    }
} //end of CheckAnswersPanel
