package co.tiagoaguiar.codelab.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TmbActivity extends AppCompatActivity {

    private EditText editHeight;
    private EditText editWeight;
    private EditText editSexo;
    private EditText editAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);
        editHeight = findViewById(R.id.edit_tmb_height);
        editWeight = findViewById(R.id.edit_tmb_weight);
        editAge = findViewById(R.id.edit_tmb_age);
        editSexo = findViewById(R.id.edit_tmb_sexo);

        Button btnSend = findViewById(R.id.btn_tmb_send);
        btnSend.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(TmbActivity.this, R.string.fields_messages, Toast.LENGTH_LONG).show();
                return;
            } else {

                String sHeight = editHeight.getText().toString();
                String sWeight = editWeight.getText().toString();
                String sAge = editAge.getText().toString();
                String sSexo = editSexo.getText().toString();

                int height = Integer.parseInt(sHeight);
                int weight = Integer.parseInt(sWeight);
                int age = Integer.parseInt(sAge);
                int sexo = Integer.parseInt(sSexo);

                double result = calculateTmb(height, weight, age, sexo);
                Log.d("Teste", "resultado é " + result);


                AlertDialog dialog = new AlertDialog.Builder(TmbActivity.this)
                        .setTitle(getString(R.string.tmb_result, result))
                        .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {

                        })
                        .create();

                dialog.show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editWeight.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editAge.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editSexo.getWindowToken(), 0);


            }
        });
    }

        private double calculateTmb ( int height, int weight, int age, int sexo){
            if (sexo == 2) {
                return 10 * weight + 6.25 * height - 5 * age - 161;
            } else if (sexo == 1) {
                return 10 * weight + 6.25 * height - 5 * age + 5;
            }
            return 0;
        }
        private boolean validate () {
            if (!editWeight.getText().toString().startsWith(String.valueOf("0"))
                    && !editWeight.getText().toString().isEmpty()
                    && !editHeight.getText().toString().startsWith("0")
                    && !editHeight.getText().toString().isEmpty()
                    && !editAge.getText().toString().startsWith("0")
                    && !editAge.getText().toString().isEmpty()
                    && !editSexo.getText().toString().startsWith("0")
                    && !editSexo.getText().toString().isEmpty()) {
                return true;
            }
            return false;
        }
    }
