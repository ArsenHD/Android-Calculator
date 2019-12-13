package com.example.user.calculator;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.calculator.expression.Expression;
import com.example.user.calculator.expression.parser.Parser;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private boolean didJustFinishCalculation = false;
    private int leftBracketsCounter = 0;
    private int rightBracketsCounter = 0;

    private TextView input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button subtract = findViewById(R.id.btnSubtract);
        Button backspace = findViewById(R.id.btnBackspace);
        Button clear = findViewById(R.id.btnClear);
        Button leftBracket = findViewById(R.id.btnLeftBracket);
        Button rightBracket = findViewById(R.id.btnRightBracket);
        Button dot = findViewById(R.id.btnDot);
        Button equals = findViewById(R.id.btnEquals);

        input = findViewById(R.id.tvInput);

        input.setMovementMethod(new ScrollingMovementMethod());

        subtract.setOnClickListener(v -> {
            clearErrorMessage();
            String str = input.getText().toString();
            int len = str.length();
            if (len == 0) {
                input.setText("-");
                return;
            }

            char last = str.charAt(len - 1);
            if (last == '+' || last == '-' || last == '*' || last == '/' || last == '.') {
                return;
            }
            input.setText(str.concat("-"));
        });

        backspace.setOnClickListener(v -> {
            if (input.getText().length() != 0) {
                String str = input.getText().toString();
                int len = str.length();
                char last = str.charAt(len - 1);
                if (last == '(') {
                    leftBracketsCounter--;
                } else if (last == ')') {
                    rightBracketsCounter--;
                }
                input.setText(input.getText().subSequence(0, input.getText().length() - 1));
            }
        });

        clear.setOnClickListener(v -> {
            leftBracketsCounter = 0;
            rightBracketsCounter = 0;
            input.setText("");
        });

        leftBracket.setOnClickListener(v -> {
            clearErrorMessage();
            String  str = input.getText().toString();
            int len = str.length();
            if (len == 0) {
                input.setText("(");
                leftBracketsCounter++;
            } else {
                char last = str.charAt(len - 1);
                if (last != '.') {
                    if (last != '*' && last != '(' && last != '/' && last != '-' && last != '+') {
                        input.setText(str.concat("*("));
                    } else {
                        input.setText(str.concat("("));
                    }
                    leftBracketsCounter++;
                    if (didJustFinishCalculation) {
                        didJustFinishCalculation = false;
                    }
                }
            }
        });

        rightBracket.setOnClickListener(v -> {
            if (input.getText().toString().equals("Error")) {
                return;
            }
            String str = input.getText().toString();
            int len = str.length();

            if (len == 0) {
                return;
            }

            char last = str.charAt(len - 1);

            if (last == '+' || last == '-' || last == '.' || last == '*' || last == '/' || last == '(') {
                return;
            }

            if (rightBracketsCounter < leftBracketsCounter) {
                input.setText(str.concat(")"));
                rightBracketsCounter++;
            }
        });

        dot.setOnClickListener(v -> {
            if (input.getText().toString().equals("Error")) {
                return;
            }

            String str = input.getText().toString();
            int len = str.length();

            if (len == 0) {
                return;
            }

            char last = str.charAt(len - 1);

            if (last == '+' || last == '-' || last == '(' || last == ')' || last == '*' || last == '/' || last == '.') {
                return;
            }

            if (isAbleToPutDot(input.getText())) {
                input.setText(str.concat("."));
            }
        });

        equals.setOnClickListener(v -> {
            String str = input.getText().toString();
            int len = str.length();

            if (len == 0) {
                return;
            }

            char last = str.charAt(len - 1);

            if (last == '+' || last == '-' || last == '*' || last == '/') {
                return;
            }

            Parser parser = new Parser();
            try {
                Expression expr = parser.parse(str);
                Double result = expr.evaluate();
                if (result == Math.floor(result) && !Double.isInfinite(result)) {
                    input.setText(String.valueOf(result.intValue()));
                } else {
                    input.setText(String.valueOf(result));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            didJustFinishCalculation = true;
            leftBracketsCounter = 0;
            rightBracketsCounter = 0;
        });

        if (savedInstanceState != null) {
            input.setText(savedInstanceState.getCharSequence("input"));
            didJustFinishCalculation = savedInstanceState.getBoolean("didJustFinishCalculation");
            leftBracketsCounter = savedInstanceState.getInt("leftBracketsCounter");
            rightBracketsCounter = savedInstanceState.getInt("rightBracketsCounter");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("input", input.getText());
        outState.putBoolean("didJustFinishCalculation", didJustFinishCalculation);
        outState.putInt("leftBracketsCounter", leftBracketsCounter);
        outState.putInt("rightBracketsCounter", rightBracketsCounter);
    }

    void clearErrorMessage() {
        if (input.getText().toString().equals("Error"))
            input.setText("");
    }

    boolean isRightBracketLast(CharSequence str) {
        if (str.length() == 0) {
            return false;
        }
        return str.charAt(str.toString().length() - 1) == ')';
    }

    boolean isDigitOrRightBracketLast(CharSequence str) {
        int len = str.length();
        if (len == 0) {
            return false;
        }

        char last = str.charAt(len - 1);

        return !(last == '+' || last == '-' || last == '(' || last == '*' || last == '/' || last == '.');
    }

    boolean isAbleToPutDot(CharSequence str) {
        int ind = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                ind = i;
                break;
            }
        }
        for (int i = str.length() - 1; i > ind; i--) {
            if (str.charAt(i) == '.') {
                return false;
            }
        }
        return true;
    }

    public void onClickNumber(View view) {
        clearErrorMessage();
        if (isRightBracketLast(input.getText()))
            return;
        input.setText(input.getText().toString().concat(((Button)view).getText().toString()));
        if (didJustFinishCalculation)
            didJustFinishCalculation = false;
    }

    public void onClickOperator(View view) {
        String str = input.getText().toString();

        if (str.equals("Error")) {
            return;
        }

        if (!isDigitOrRightBracketLast(input.getText())) {
            return;
        }

        input.setText(input.getText().toString().concat(((Button) view).getText().toString()));
    }
}
