package com.company.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class about_app extends AppCompatActivity {

    TextView text;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        text = findViewById(R.id.textView);
        back = findViewById(R.id.button5);

        String changeText = "כשנשאלתי על מה אעשה את הפרוייקט גמר שלי ידעתי שאענה במשהו שקשור לאוכל.\n" +
                "מילדות ישבתי ליד סבתי במטבח וראיתי כל צעד וכל נגיעה שלה בסירים. האוכל היה דרך החיים שלה שהזכות הכי יפה הייתה הקירוב בין אנשים ולאוכל ללא הבדל בדת, צבע ומין.\n" +
                "כולם באו לאכול אצלה בכל ימות השבוע ובכל השעות.\n" +
                "בזכות הידע והכלים שקיבלתי ממנה אני היום יודע לגשת ולגעת בכל מתכון שאראה ברשת וארצה לנסות ללא חשש.\n" +
                "\n" +
                "מוקדש לסבתא אמי יצחק, הסבתא המושלמת שליוותה אותי 30 שנה בכל צעד.";

        text.setText(changeText);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menu.class));
                finish();
            }
        });
    }
}