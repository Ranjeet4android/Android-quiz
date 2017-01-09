package quiz.ranjeet.android.androidquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ranjeet on 8/1/17.
 */


public class MultiQuizActivity extends Activity  {
    /** Called when the activity is first created. */

    EditText question = null;
   /* RadioButton answer1 = null;
    RadioButton answer2 = null;
    RadioButton answer3 = null;
    RadioButton answer4 = null;
    RadioGroup answers = null;*/

    CheckBox answer1 = null;
    CheckBox answer2 = null;
    CheckBox answer3 = null;
    CheckBox answer4 = null;
    //RadioGroup answers = null;
    Button finish = null;
    int selectedAnswer = -1;
    int quesIndex = 0;
    int numEvents = 0;
    int selected[] = null;
    int correctAns[] = null;
    boolean review =false;
    Button prev, next = null;

    ArrayList<String> selectedList1 = new ArrayList<String>();
    ArrayList<String> selectedList2 = new ArrayList<String>();
    ArrayList<String> selectedList3 = new ArrayList<String>();
    ArrayList<String> selectedList4 = new ArrayList<String>();
    TextView questionno;
    int score = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiquiz);

        TableLayout quizLayout = (TableLayout) findViewById(R.id.quizLayout);

        questionno    = (TextView) findViewById(R.id.questionno);

        quizLayout.setVisibility(View.INVISIBLE);

        try {
            question = (EditText) findViewById(R.id.question);
            answer1 = (CheckBox) findViewById(R.id.checkBox1);
            answer2 = (CheckBox) findViewById(R.id.checkBox2);
            answer3 = (CheckBox) findViewById(R.id.checkBox3);
            answer4 = (CheckBox) findViewById(R.id.checkBox4);
           /* answer1.setOnCheckedChangeListener(this);
            answer2.setOnCheckedChangeListener(this);
            answer3.setOnCheckedChangeListener(this);
            answer4.setOnCheckedChangeListener(this);*/
            //answers = (RadioGroup) findViewById(R.id.answers);
            Button finish = (Button)findViewById(R.id.finish);
            finish.setOnClickListener(finishListener);

            prev = (Button)findViewById(R.id.Prev);
            prev.setOnClickListener(prevListener);
            next = (Button)findViewById(R.id.Next);
            next.setOnClickListener(nextListener);


            selected = new int[QuizFunActivity.getQuesList().length()];
            Arrays.fill(selected, -1);
            correctAns = new int[QuizFunActivity.getQuesList().length()];
            Arrays.fill(correctAns, -1);

            getselectedlist();
            questionno.setText(1+"/"+QuizFunActivity.getQuesList().length());
            this.showQuestion(0,review);

            quizLayout.setVisibility(View.VISIBLE);



        } catch (Exception e) {
           // Log.e("", e.getMessage().toString(), e.getCause());
        }

    }


    private void showQuestion(int qIndex,boolean review) {
        try {
            JSONObject aQues = QuizFunActivity.getQuesList().getJSONObject(qIndex);
            String quesValue = aQues.getString("Question");
            if (correctAns[qIndex] == -1) {
                String correctAnsStr = aQues.getString("CorrectAnswer");
                correctAns[qIndex] = Integer.parseInt(correctAnsStr);
            }

            question.setText(quesValue.toCharArray(), 0, quesValue.length());
            questionno.setText((qIndex+1)+ "/" + QuizFunActivity.getQuesList().length());
            //answers.check(-1);
            answer1.setTextColor(Color.WHITE);
            answer2.setTextColor(Color.WHITE);
            answer3.setTextColor(Color.WHITE);
            answer4.setTextColor(Color.WHITE);
            JSONArray ansList = aQues.getJSONArray("Answers");
            String aAns = ansList.getJSONObject(0).getString("Answer");
            answer1.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(1).getString("Answer");
            answer2.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(2).getString("Answer");
            answer3.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(3).getString("Answer");
            answer4.setText(aAns.toCharArray(), 0, aAns.length());
            Log.d("selected[qIndex]","selected[qIndex]="+selected[qIndex]);
            if (selected[qIndex] == 0)
                //answer1.setChecked(true);
                //answers.check(R.id.a0);
            if (selected[qIndex] == 1)
                //answer2.setChecked(true);
               // answers.check(R.id.a1);
            if (selected[qIndex] == 2)
               // answer3.setChecked(true);
               // answers.check(R.id.a2);
            if (selected[qIndex] == 3)
                //answer4.setChecked(true);
               // answers.check(R.id.a3);

            setScoreTitle();
            if (quesIndex == (QuizFunActivity.getQuesList().length()-1))
                next.setEnabled(false);

            if (quesIndex == 0)
                prev.setEnabled(false);

            if (quesIndex > 0)
                prev.setEnabled(true);

            if (quesIndex < (QuizFunActivity.getQuesList().length()-1))
                next.setEnabled(true);


            if (review) {
                Log.d("review","selected[qIndex]="+selected[qIndex]+", correctAns[qIndex]= "+correctAns[qIndex]);
                Log.d("review","selectedList1[qIndex]="+selectedList1.get(qIndex)+"selectedList2[qIndex]= "+selectedList2.get(qIndex)
                +"\nselectedList3[qIndex]="+selectedList3.get(qIndex)+"\nselectedList4[qIndex]= "+selectedList4.get(qIndex));

                if (selected[qIndex] != correctAns[qIndex]) {
                    if (selected[qIndex] == 0)
                        answer1.setTextColor(Color.RED);
                    //answer1.setChecked(true);
                    if (selected[qIndex] == 1)
                        answer2.setTextColor(Color.RED);
                    //answer2.setChecked(true);
                    if (selected[qIndex] == 2)
                        answer3.setTextColor(Color.RED);
                   // answer3.setChecked(true);
                    if (selected[qIndex] == 3)
                        answer4.setTextColor(Color.RED);
                   // answer4.setChecked(true);

                }
                setchecked(qIndex);
                if (correctAns[qIndex] == 0)
                    answer1.setTextColor(Color.GREEN);
               // answer1.setChecked(true);
                if (correctAns[qIndex] == 1)
                    answer2.setTextColor(Color.GREEN);
                //answer2.setChecked(true);
                if (correctAns[qIndex] == 2)
                    answer3.setTextColor(Color.GREEN);
               // answer3.setChecked(true);
                if (correctAns[qIndex] == 3)
                    answer4.setTextColor(Color.GREEN);
               // answer4.setChecked(true);


            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage(), e.getCause());
        }
    }


    private View.OnClickListener finishListener = new View.OnClickListener() {
        public void onClick(View v) {
            setAnswer();
            //Calculate Score
            Log.d("oldscore","oldscore="+score);
            for(int i=0; i<correctAns.length; i++){
                if ((correctAns[i] != -1) && (correctAns[i] == selected[i]))
                    score++;
            }
            Log.d("score","score="+score);
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(MultiQuizActivity.this).create();
            alertDialog.setTitle("Score");
            alertDialog.setMessage((score) +" out of " + (QuizFunActivity.getQuesList().length()));

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Retake", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog, int which) {
                    review = false;
                    quesIndex=0;
                    getselectedlist();
                    MultiQuizActivity.this.showQuestion(0, review);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Review", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog, int which) {
                    review = true;
                    quesIndex=0;
                    MultiQuizActivity.this.showQuestion(0, review);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Quit", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog, int which) {
                    review = false;
                    getselectedlist();
                    finish();
                }
            });

            alertDialog.show();

        }
    };

    private void setAnswer() {
        if (answer1.isChecked())
            selected[quesIndex] = 0;
        if (answer2.isChecked())
            selected[quesIndex] = 1;
        if (answer3.isChecked())
            selected[quesIndex] = 2;
        if (answer4.isChecked())
            selected[quesIndex] = 3;

        //Log.d("setAnswer",Arrays.toString(selected));
       // Log.d("setAnswer",Arrays.toString(correctAns));

    }

    private View.OnClickListener nextListener = new View.OnClickListener() {
        public void onClick(View v) {
            getselectedcheckbox(quesIndex);
            setAnswer();
            quesIndex++;
            if (quesIndex >= QuizFunActivity.getQuesList().length())
                quesIndex = QuizFunActivity.getQuesList().length() - 1;
            checkboxclear();
            showQuestion(quesIndex,review);
        }
    };

    private View.OnClickListener prevListener = new View.OnClickListener() {
        public void onClick(View v) {
            getselectedcheckbox(quesIndex);

            setAnswer();
            quesIndex--;
            if (quesIndex < 0)
                quesIndex = 0;
            checkboxclear();
            showQuestion(quesIndex,review);
        }
    };

    private void setScoreTitle() {
        questionno.setText((quesIndex+1)+ "/" + QuizFunActivity.getQuesList().length());
        this.setTitle("SciQuiz3     " + (quesIndex+1)+ "/" + QuizFunActivity.getQuesList().length());
    }


    public void checkboxclear()
    {
        answer1.setChecked(false);
        answer2.setChecked(false);
        answer3.setChecked(false);
        answer4.setChecked(false);
    }

    /*@Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.e("onCheckedChanged","onCheckedChanged b="+b);
        if(answer1.isChecked())
        {
            Log.e("onCheckedChanged","answer1 b="+b);
        }
        else if(answer2.isChecked())
        {
            Log.e("onCheckedChanged","answer2 b="+b);
        }
        else if(answer3.isChecked())
        {
            Log.e("onCheckedChanged","answer3 b="+b);
        }
        else if(answer4.isChecked())
        {
            Log.e("onCheckedChanged","answer4 b="+b);
        }
    }
    */
    public void getselectedcheckbox(int quesIndex)
    {
        if(quesIndex!=-1) {
            if (answer1.isChecked())
                selectedList1.set(quesIndex, "0");
            if (answer2.isChecked())
                selectedList2.set(quesIndex, "0");
            if (answer3.isChecked())
                selectedList3.set(quesIndex, "0");
            if (answer4.isChecked())
                selectedList4.set(quesIndex, "0");

            Log.d("getselectedcheckbox", "getselectedcheckbox quesIndex=" + quesIndex + " " + selectedList1.size() + ", selectedList2=" + selectedList2.size() + ", selectedList3=" + selectedList3.size()
                    + ", selectedList4=" + selectedList4.size());
        }
    }

    public void getselectedlist()
    {
        selectedList1.clear();
        selectedList2.clear();
        selectedList3.clear();
        selectedList4.clear();
        score=0;
        for(int i=0;i<QuizFunActivity.getQuesList().length();i++) {
            selectedList1.add("-1");
            selectedList2.add("-1");
            selectedList3.add("-1");
            selectedList4.add("-1");
        }
        Log.d("selectedList1","selectedList1="+selectedList1.size());
    }

    public void setchecked(int qIndex)
    {
        if(selectedList1.get(qIndex).equalsIgnoreCase("0"))
        {
            answer1.setChecked(true);
            answer1.setTextColor(Color.RED);
            Log.d("selectedList1","selectedList1="+selectedList1.size());
        }
        if(selectedList2.get(qIndex).equalsIgnoreCase("0"))
        {
            answer2.setChecked(true);
            answer2.setTextColor(Color.RED);
            Log.d("selectedList2","selectedList2="+selectedList1.size());
        }
        if(selectedList3.get(qIndex).equalsIgnoreCase("0"))
        {
            answer3.setChecked(true);
            answer3.setTextColor(Color.RED);
            Log.d("selectedList3","selectedList3="+selectedList1.size());
        }
        if(selectedList4.get(qIndex).equalsIgnoreCase("0"))
        {
            answer4.setChecked(true);
            answer4.setTextColor(Color.RED);
            Log.d("selectedList4","selectedList4="+selectedList1.size());
        }

    }
}
