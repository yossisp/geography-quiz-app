/*
@author - Yosef Spektor

the class contains data per each question.
 */

import java.util.ArrayList;

public class Question {
    //used to signify the status of question
    public enum AnswerStatus { CORRECT, INCORRECT, NO_ANSWER };

    private String question; //the test of the question
    private String userAnswer; //the multiple choice letter that the user selected as answer
    private AnswerStatus status;
    private ArrayList<String[][]> answers; //contains four answers strings

    //constructor
    public Question(String question, ArrayList<String[][]> answers) {
        this.question = question;
        this.status = AnswerStatus.NO_ANSWER;
        this.answers = answers;
    }

    //-------GETTERS and SETTERS
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getUserAnswer() {
        return this.userAnswer;
    }

    public AnswerStatus getAnswerStatus() {
        return this.status;
    }

    public String getQuestionText() {
        return this.question;
    }

    public ArrayList<String[][]> getAnswers() {
        return this.answers;
    }

    /*
    the answerId represents the row position of the user selected answer in our answers ArrayList field.
    we then check if the second column of String[][] element of the ArrayList is a CORRECT_ANSWER_STRING
    or not.
     */
    public boolean isCorrectAnswer(int answerId) {
        int i;
        String s;

        //get the Utils.IN/CORRECT_ANSWER_STRING of the answer
        s = this.answers.get(answerId)[Utils.FIRST_ROW][Utils.SECOND_COL];

        //update this.status accordingly
        if(s.equals(Utils.CORRECT_ANSWER_STRING)) {
            this.status = AnswerStatus.CORRECT;
            return true;
        }
        this.status = AnswerStatus.INCORRECT;
        return false;
    }
}
