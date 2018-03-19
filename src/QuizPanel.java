/*
@author - Yosef Spektor

the class sets the look of the quiz app, it serves questions one by one (the user sees only one question
at a time). each question has multiple choice letters and answers below.
At the bottom there're two buttons: nextButton and finishedButton. When the user clicks the nextButton
we check if the user selected some answer and if not we issue a warning to the user and give another
chance to select an answer if the user wants. Otherwise an incorrect answer will be recorded.
If the user click finishedButton before all the questions were served we also issue a warning and
the user can return to the remaining questions and finish them if the user wants. After finishedButton is
clicked and the user wants to finish the test CheckAnswersPanel is shown.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuizPanel extends JPanel  implements ActionListener {
    //------CONSTANTS--------
    //letters for multiple choice
    private final static String[] ANSWER_LETTERS = {"A", "B", "C", "D"};

    //this is needed for answerLabels init
    private final static String[] BLANK_LABELS = {"", "", "", ""};

    //padding constants
    private final static int BORDER_ZERO_PADDING = 0;
    private final static int BORDER_PADDING = 20;

    //constants for multipleChoicePanel and answerLabelsPanel panels
    private final static int ANSWER_ROWS = 4;
    private final static int ANSWER_COLS = 0;

    //means that something went wrong in answerButtonHandler()
    private final static int NOT_ANSWER_BUTTON = -1;

    //buttons names
    private final static String NEXT_BUTTON_TEXT = "Next Question";
    private final static String FINISHED_BUTTON_TEXT = "Finished";

    //is used to determine if we reach the end in two more questions which means we need to hide nextButton
    private final static int IN_TWO_QUESTIONS = 2;
    private final static int ANSWERS_PER_QUESTION = 4;

    //------INSTANCE FIELDS--------
    private JLabel question; //displays the question
    private JLabel[] answerLabels; //displays the answers
    private JLabel counterLabel; //counts the how many questions have been answered so far
    private JButton[] answerButtons; //multiple choice answer buttons
    private JButton nextButton; //we proceed to the next question
    private JButton finishedQuizButton; //button to finish the quiz
    private Quiz quiz; //Quiz object that contains the questions and answers
    private JPanel answersButtonsPanel; //holds the answer buttons


    //contructor
    public QuizPanel(Quiz quiz) {
        int i;

        /*
        we'll need the panels for our gui. the main panels are south- and northPanel, which consist of
        other panels
         */
        JPanel northPanel = new JPanel();
        JPanel questionPanel = new JPanel();
        JPanel multipleChoiceAndAnswersPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel nextButtonPanel = new JPanel();
        JPanel multipleChoicePanel = new JPanel();
        JPanel answerLabelsPanel = new JPanel();
        JLabel[] multipleChoiceLetters = new JLabel[ANSWER_LETTERS.length];

        //initializing fieldsA
        this.quiz = quiz;
        this.answersButtonsPanel = new JPanel();
        this.question = new JLabel();
        this.question.setFont(Utils.HEADER_FONT);
        this.counterLabel = new JLabel();
        this.nextButton = new JButton(NEXT_BUTTON_TEXT);
        this.nextButton.setFont(Utils.BUTTON_FONT);
        this.answerButtons = new JButton[ANSWERS_PER_QUESTION];
        this.answerLabels = new JLabel[ANSWERS_PER_QUESTION];
        this.finishedQuizButton = new JButton(FINISHED_BUTTON_TEXT);
        this.finishedQuizButton.setFont(Utils.BUTTON_FONT);

        this.addButtonsToPanel(ANSWER_LETTERS); //adds buttons to answersButtonsPanel

        //LAYOUT stage
        multipleChoicePanel.setLayout(new GridLayout(ANSWER_ROWS, ANSWER_COLS));
        multipleChoicePanel.setBorder(new EmptyBorder(BORDER_ZERO_PADDING,BORDER_ZERO_PADDING,
                BORDER_ZERO_PADDING,BORDER_PADDING));
        this.addLabelsToPanel(multipleChoicePanel, multipleChoiceLetters, ANSWER_LETTERS);
        answerLabelsPanel.setLayout(new GridLayout(ANSWER_ROWS, ANSWER_COLS));
        //we use BLANK_LABELS to add this.answerLabels because otherwise we'd have
        //to create another function (which wouldn't need dummy BLANK_LABELS)
        this.addLabelsToPanel(answerLabelsPanel, this.answerLabels, BLANK_LABELS);
        multipleChoiceAndAnswersPanel.add(multipleChoicePanel);
        multipleChoiceAndAnswersPanel.add(answerLabelsPanel);
        multipleChoiceAndAnswersPanel.setBorder(new EmptyBorder(BORDER_PADDING,BORDER_ZERO_PADDING,
                BORDER_PADDING,BORDER_ZERO_PADDING));
        questionPanel.add(this.question);
        nextButtonPanel.add(this.nextButton);
        nextButtonPanel.add(this.finishedQuizButton);
        this.counterLabel.setBorder(new EmptyBorder(BORDER_ZERO_PADDING,BORDER_PADDING,
                BORDER_ZERO_PADDING,BORDER_ZERO_PADDING));
        this.counterLabel.setFont(Utils.BUTTON_FONT);
        nextButtonPanel.add(this.counterLabel);
        northPanel.setLayout(new BorderLayout());
        northPanel.add(questionPanel, BorderLayout.NORTH);
        northPanel.add(multipleChoiceAndAnswersPanel, BorderLayout.CENTER);
        southPanel.setLayout(new BorderLayout());
        southPanel.add(answersButtonsPanel, BorderLayout.NORTH);
        southPanel.add(nextButtonPanel, BorderLayout.SOUTH);
        this.setLayout(new BorderLayout());
        this.add(northPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        //--- LISTENERS ---
        //because we implemented ActionListener we just need to add this.
        this.nextButton.addActionListener(this);
        this.finishedQuizButton.addActionListener(this);
        for(i = 0; i < this.answerButtons.length; i++) {
            this.answerButtons[i].addActionListener(this);
        }

    }

    /*
    helper method to initialize buttons array instead of having the loop in the constructor
     */
    private void addButtonsToPanel(String[] cNames) {
        int i;
        for(i = 0; i < this.answerButtons.length; i++) {
            this.answerButtons[i] = new JButton(cNames[i]);
            this.answerButtons[i].setFont(Utils.BUTTON_FONT);
            this.answersButtonsPanel.add(this.answerButtons[i]);
        }
    }

    /*
    helper method to initialize labels array instead of having the loop in the constructor
     */
    private void addLabelsToPanel(JPanel p, JLabel[] c, String[] cNames) {
        int i;
        for(i = 0; i < c.length; i++) {
            c[i] = new JLabel(cNames[i]);
            c[i].setFont(Utils.TEXT_FONT);
            p.add(c[i]);
        }
    }

    //set current answers to the current question
    //our String[][] contains answers in the first column, in the second column it contains
    //the info whether the answer is correct or not, we don't need that info now
    private void setAnswerLabels(ArrayList<String[][]> a) {
        int i;
        String s;
        for(i = 0; i < ANSWERS_PER_QUESTION; i++) {
            s = a.get(i)[Utils.FIRST_ROW][Utils.FIRST_COL];
            this.answerLabels[i].setText(s);
        }
    }

    /*
    the method gets the first question from the quiz at the startup of the app,
    because the display of first question is not triggered by any event.
     */
    public void getFirstQuestion() {
        this.question.setText(this.quiz.getCurrentQuestion().getQuestionText());
        this.setAnswerLabels(this.quiz.getCurrentQuestion().getAnswers());
        this.updateCounterLabel();
    }

    /*
    the method returns JOptionPane.NO_OPTION in general. if there's a case that the user didn't
    select any answer, then issue a warning confirm dialog which asks the user if they want to try again.
    if the user wants to try again we return JOptionPane.YES_OPTION. the return value will
    only be used.

    The method handles all actions that need to occur if the user clicks nextButton.
     */
    private int nextButtonHandler() {
        Question q = this.quiz.getCurrentQuestion();

        if(q.getUserAnswer() == null) {
            //this means the user didn't click on any answerButton
            //we'll issue a warning in this case
            int userResponse;
            userResponse = Utils.showConfirmDialog(QuizApp.frame, Utils.NO_ANSWER_BUTTON_CLICKED_WARNING,
                    Utils.WARNING_TITLE_STR);
            if(userResponse == JOptionPane.YES_OPTION) {
                return JOptionPane.YES_OPTION;
            }
        }
        //q will not be null because the last question is handled by finishedButton
        if(q.getAnswerStatus() == Question.AnswerStatus.CORRECT) {
            this.quiz.incrementCorrectlyAnsweredCounter();
        }

        //next question will be the last, therefore this.nextButton will no longer be needed
        //because there will be no next question
        if(this.quiz.getCurrentQuestionId() + IN_TWO_QUESTIONS == this.quiz.getQuestionsQty()) {
            this.nextButton.setVisible(false);
        }

        q = this.quiz.getNextQuestion();
        if(q != null) { //if the next question is null then we arrived to the end of the exam
            this.question.setText(q.getQuestionText());
            this.setAnswerLabels(q.getAnswers());
            this.quiz.incrementCurrentQuestionId();
            this.updateCounterLabel();
        }
        return JOptionPane.NO_OPTION;
    }

    /*
    we show the progress to the user at each question.
     */
    private void updateCounterLabel() {
        this.counterLabel.setText(String.format("Question %d out of %d",
                this.quiz.getCurrentQuestionId() + Utils.ARRAY_INDEX_OFFSET,
                this.quiz.getQuestionsQty()));
    }

    /*
    the method handles action that need to occur when finishedButton is clicked.
     */
    private void finishedButtonHandler() {
        /*
            we arrived to the last question. there's a chance that the user will not select any answer and
            click finishedButton. but earlier we always asked the user in such case if they want to
            go back and select some answer. unfortunately the nextButton took care of this but in the last
            question nextButton disappears. so we'll still use nextButtonHandler() if this is the last
            question.
             */
        if(this.quiz.getCurrentlyAnsweredQuestionsQty() == this.quiz.getQuestionsQty()) {
            //if the currentQuestion is null this means that the user chose not to answer
            //the last question in the dialog message from nextButtonHandler(), therefore we need
            //to show the answers
            if(this.nextButtonHandler() == JOptionPane.NO_OPTION) {
                //the user selected JOptionPane.NO_OPTION which means they want to finish the quiz anyway
                QuizApp.frame.remove(this);
                QuizApp.showCheckAnswersPanel(QuizApp.frame, this.quiz);
            }
        } else {
                /*
            if the user clicked on finishedButton but there may still be questions that were not even seen
            then we issue a warning
             */
            if(this.quiz.getCurrentlyAnsweredQuestionsQty() < this.quiz.getQuestionsQty()) {
                int userResponse = Utils.showConfirmDialog(QuizApp.frame, Utils.NOT_ALL_QUESTIONS_SEEN_WARNING,
                        Utils.WARNING_TITLE_STR);
                if(userResponse == JOptionPane.NO_OPTION) {
                    //the user changed their mind and still wants to try solve the remaining
                    //questions
                    return;
                }
                //it can be that the user pressed an answerButton but didn't click nextQuestion and
                //immediately clicked finishButton. in this case we need to record a potentially
                //correct answer
                if(this.quiz.getCurrentQuestion().getAnswerStatus() == Question.AnswerStatus.CORRECT) {
                    this.quiz.incrementCorrectlyAnsweredCounter();
                }
            }
            /*
            at last we change the current panel and switch to the CheckAnswersPanel layout.
            for this we first remove the current ("this") panel.
             */
            QuizApp.frame.remove(this);
            QuizApp.showCheckAnswersPanel(QuizApp.frame, this.quiz);
        }
    }

    /*
    determines the position of the clicked button in answerButtons array.
    this is needed for methods that check whether the answer is correct
     */
    private int getAnswerButtonId(JButton b) {
        int i;
        for(i = 0; i < this.answerButtons.length; i++) {
            if(b == this.answerButtons[i]) {
                return i;
            }
        }
        return NOT_ANSWER_BUTTON;
    }

    /*
    the method handles answerButton related events. Because we have 4 multiple choice buttons and we don't
    want to have a switch statement which would determine which button was clicked we'll cast the object
    returned by getSource() from actionPerformed() and look it up in the answerButtons array.
     */
    private void answerButtonHandler(Object button) {
        int letterId;
        Question q;
        JButton b = (JButton) button;

        //getAnswerButtonId() will return the position of the button in the answerButtons array
        letterId = getAnswerButtonId(b);

        //this situation should not happen in theory because there's a limited set of buttons
        //and we should've been able to identify the correct button.
        //in the theoretically impossible scenario that the button was not found
        //we'll notify the user about the error and quit the program
        if (letterId == NOT_ANSWER_BUTTON) {
            System.err.println("getAnswerButtonId(): answerButtonId invalid");
            Utils.showMessageDialog(QuizApp.frame, Utils.INTERNAL_APP_CRASH_ERROR_STR, Utils.ERROR_TITLE_STR);
            System.exit(Utils.BAD_EXIT);
        }
        q = this.quiz.getCurrentQuestion();

        //we'll set which answerButton was selected in Question object
        q.setUserAnswer(ANSWER_LETTERS[letterId]);

        //will set Question status field as AnswerStatus.CORRECT if the answer is correct
        q.isCorrectAnswer(letterId);
    }

    /*
    the method determines which button was clicked and calls the appropriate handler.
    there're only 6 buttons in total so first we check for nextButton and finishedButton.
    the remaining buttons can only be answerButtons
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.nextButton) {
            this.nextButtonHandler();
        } else if(e.getSource() == this.finishedQuizButton) {
            this.finishedButtonHandler();
        }else {
            this.answerButtonHandler(e.getSource());
        }
    }
}

