/*
@author - Yosef Spektor

the class contains the data about the question, answers, correctly answered questions
 */

import java.util.ArrayList;
import java.util.Collections;

public class Quiz {
    //------CONSTANSTS-------
    private final static int FIRST_LINE = 0; //initially the first line represents the correct answer

    //-------INSTANCE FIELDS-------
    private Question[] questions; //array of questions with answers
    private int correctlyAnsweredCounter;
    private int currentQuestionId; //the index of the currently handled question in this.questions array
    private int questionsNum; //how many questions there are

    //constructor
    public Quiz(String[] lines) {
        this.currentQuestionId = Utils.ZERO_QUESTIONS;
        this.correctlyAnsweredCounter = Utils.ZERO_QUESTIONS;

        //according to our assumptions lines.length is a multiple of Utils.LINES_PER_QUESTION
        //so we should always get an integer (without remainder) after division
        this.questionsNum = lines.length / Utils.LINES_PER_QUESTION;

        //we know how many questions are from the constant Utils.QUESTIONS_NUM
        this.questions = new Question[this.questionsNum];

        //I decided to fill this.questions array with a static method in order to have less
        //code in the constructor
        this.fillQuestions(lines);
    }

    /*
    fills one question with question, answers text. First we fill the String[][] object with one answer
    to the question. Then we fill the ArrayList object with all the four answers. We need to the ArrayList
    object in order to use the built-in Collections.shuffle() method in order to shuffle the answers.

    the method receives lines string array which contains all the lines read from our file by
    QuizReader
     */
    private void fillQuestion(String[] lines, int from, int questionId) {
        int i,
                linesIndex; //helper index to access answer strings

        //we use ArrrayList to use the built-in Collections.shuffle() method in order to shuffle the answers.
        ArrayList<String[][]> answersArray = new ArrayList<String[][]>();

        //we start with linesIndex = from + 1 because the question is at index from and we want to get the
        //answers only
        linesIndex = from + 1;
        for(i = 0; i < Utils.ANSWERS_PER_QUESTION; i++, linesIndex++) {
            String[][] s = new String[Utils.ROWS][Utils.COLUMNS];
            s[Utils.FIRST_ROW][Utils.FIRST_COL] = lines[linesIndex];

            //according to the conventions described in the maman the first line always contains the correct
            //answer
            if(i == FIRST_LINE) {
                s[Utils.FIRST_ROW][Utils.SECOND_COL] = Utils.CORRECT_ANSWER_STRING;
            } else {
                s[Utils.FIRST_ROW][Utils.SECOND_COL] = Utils.INCORRECT_ANSWER_STRING;
            }
            answersArray.add(s);
        }
        Collections.shuffle(answersArray);

        //lines[from] contains the question text.
        this.questions[questionId] = new Question(lines[from], answersArray);
    }

    //we initialize our this.questions array
    private void fillQuestions(String[] lines) {
        int i, from = 0, to;
        for(i = 0; i < this.questionsNum; i++) {
            this.fillQuestion(lines, from, i);

            //we need to update the index for next question which is in Utils.LINES_PER_QUESTION lines
            from += Utils.LINES_PER_QUESTION;
        }
    }

    /*
    return the next question. this.currentQuestionId field will be updated in QuizPanel
     */
    public Question getNextQuestion() {
        int nextQuestion = this.currentQuestionId + Utils.ARRAY_INDEX_OFFSET;
        if(nextQuestion < this.questionsNum) {
            return this.questions[nextQuestion];
        } else {
            return null;
        }
    }

    //updates the quantity of currently correctly answered questions
    public void incrementCorrectlyAnsweredCounter() {
        this.correctlyAnsweredCounter++;
    }

    /*
    this method is called in QuizPanel after all events were handled
     */
    public void incrementCurrentQuestionId() {
        this.currentQuestionId++;
    }

    //get how many questions there're in total
    public int getQuestionsQty() {
        return this.questions.length;
    }

    //get the quantity of currently answered questions
    public int getCurrentlyAnsweredQuestionsQty() {
        return this.currentQuestionId + Utils.ARRAY_INDEX_OFFSET;
    }

    //returns the current question
    public Question getCurrentQuestion() {
        return this.questions[this.currentQuestionId];
    }

    //returns the question by questionId
    public Question getQuestionById(int questionId) {
        return this.questions[questionId];
    }

    //returns the number of correctly answered questions so far
    public int getCorrectlyAnsweredCounter() {
        return this.correctlyAnsweredCounter;
    }

    //returns the id of the current question
    public int getCurrentQuestionId() {
        return this.currentQuestionId;
    }
}
