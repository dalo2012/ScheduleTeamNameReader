package com.example.davidreetz.scheduleteamnamereader;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    Button setTeam;
    EditText matchNumber, groupNumber;
    TextView teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTeam = (Button)findViewById(R.id.setTeam);
        matchNumber = (EditText)findViewById(R.id.matchNumber);
        groupNumber = (EditText)findViewById(R.id.groupNumber);
        teamName = (TextView)findViewById(R.id.teamName);

        setTeam.setOnClickListener(listen);
    }

    private ArrayList<String> readFile() {
        try {
            FileReader fr = new FileReader(Environment.getExternalStorageDirectory() + "/schedule.csv");
            Scanner scanner = new Scanner(fr);
            scanner.useDelimiter("\n");
            ArrayList<String> matches = new ArrayList<String>();
            ArrayList<String> teams = new ArrayList<String>();
            while (scanner.hasNext()){
                String team = scanner.next().trim();
                matches.add(team);
            }
            scanner.close();
            for (String s:matches) {
                Scanner scanner1 = new Scanner(s);
                scanner1.useDelimiter(",");
                while (scanner.hasNext()) {
                    String team = scanner.next().trim();
                    teams.add(team);
                }
            }
            return teams;
        }
        catch (FileNotFoundException e){
            Toast t = Toast.makeText(this,
                    "File not found!", Toast.LENGTH_LONG);
            t.show();
            return new ArrayList<String>();
        }
    }

    private String getTeam(int matchNum, int group) {
        ArrayList<String> teams = readFile();
        return teams.get((matchNum-1)*6+group);
    }

    private View.OnClickListener listen = new View.OnClickListener(){
        public void onClick(View v){
            switch(v.getId()) {
                case R.id.setTeam:
                    teamName.setText(getTeam(Integer.parseInt(matchNumber.getText().toString()), Integer.parseInt(groupNumber.getText().toString())));
                    break;
            }
        }
    };
}
