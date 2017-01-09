package quiz.ranjeet.android.androidquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONArray;

import android.app.Activity;
import android.os.Handler;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class QuizFunActivity extends Activity{
    Intent menu = null;
	BufferedReader bReader = null;
	static JSONArray quesList = null;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Button button = (Button)findViewById(R.id.button);
		Button button2 = (Button)findViewById(R.id.button2);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(QuizFunActivity.this, QuestionActivity.class);
				startActivity(intent);
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(QuizFunActivity.this, MultiQuizActivity.class);//QuestionActivity
				startActivity(intent);
			}
		});
        Thread thread = new Thread() {
        	public void run() {
		        try {
		        	Thread.sleep(3*1000);
			        finish();
			        loadQuestions();
			        //Intent intent = new Intent(QuizFunActivity.this, MultiQuizActivity.class);//QuestionActivity
			        //QuizFunActivity.this.startActivity(intent);
	        	} catch (Exception e) {
	        	} 
        	}
        };
        thread.start();

     }
    
    private void loadQuestions() throws Exception {
    	try {
		InputStream questions = this.getBaseContext().getResources()
				.openRawResource(R.raw.questions);
		bReader = new BufferedReader(new InputStreamReader(questions));
		StringBuilder quesString = new StringBuilder();
		String aJsonLine = null;
		while ((aJsonLine = bReader.readLine()) != null) {
			quesString.append(aJsonLine);
		}
		Log.d(this.getClass().toString(), quesString.toString());
		JSONObject quesObj = new JSONObject(quesString.toString());
		quesList = quesObj.getJSONArray("Questions");
		Log.d(this.getClass().getName(),
				"Num Questions " + quesList.length());
    	} catch (Exception e){
    		
    	} finally {
			try {
				bReader.close();
			} catch (Exception e) {
				Log.e("", e.getMessage().toString(), e.getCause());
			}

    	}
    	

    }
    
    public static JSONArray getQuesList() {
    	return quesList;
    }
    
}