package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TmbActivity extends AppCompatActivity {

    private EditText editHeight;
    private EditText editWeight;
    //private EditText editSexo;
    private EditText editAge;
    private Spinner spinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);
        editHeight = findViewById(R.id.edit_tmb_height);
        editWeight = findViewById(R.id.edit_tmb_weight);
        editAge = findViewById(R.id.edit_tmb_age);
        // editSexo = findViewById(R.id.edit_tmb_sexo);
        spinnerSexo = findViewById(R.id.spinner_tmb_sex);


        Button btnSend = findViewById(R.id.btn_tmb_send);
        btnSend.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(TmbActivity.this, R.string.fields_messages, Toast.LENGTH_LONG).show();

            } else {

                String sHeight = editHeight.getText().toString();
                String sWeight = editWeight.getText().toString();
                String sAge = editAge.getText().toString();
                //String sSexo = editSexo.getText().toString();

                int height = Integer.parseInt(sHeight);
                int weight = Integer.parseInt(sWeight);
                int age = Integer.parseInt(sAge);
                //int sexo = Integer.parseInt(sSexo);

                double result = calculateTmb(height, weight, age);
                Log.d("Teste", "resultado é " + result);


                AlertDialog dialog = new AlertDialog.Builder(TmbActivity.this)
                        .setTitle(getString(R.string.tmb_result, result))
                        .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {

                        })
                        .setNegativeButton(R.string.save, (dialog1, which) -> new Thread(() -> {
                            long calcId = SqlHelper.getInstance(TmbActivity.this).addItem("tmb", result);
                            runOnUiThread(() -> {
                                if (calcId > 0)
                                    Toast.makeText(TmbActivity.this, R.string.saved, Toast.LENGTH_LONG).show();
                                openListCalcActivity();
                            });
                        }).start())
                        .create();

                dialog.show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editWeight.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editAge.getWindowToken(), 0);


            }
        });
    }

    private double calculateTmb(int height, int weight, int age) {
        int index = spinnerSexo.getSelectedItemPosition();
        switch (index) {
            case 1:
                return 10 * weight + 6.25 * height - 5 * age - 161;
            case 0:
                return 10 * weight + 6.25 * height - 5 * age + 5;
            default:
                return 0;
        }
        // if (sexo == 2) {
        //    return 10 * weight + 6.25 * height - 5 * age - 161;
        // } else if (sexo == 1) {
        //    return 10 * weight + 6.25 * height - 5 * age + 5;
        //}

    }

    private boolean validate() {
        return !editWeight.getText().toString().startsWith("0")
                && !editWeight.getText().toString().isEmpty()
                && !editHeight.getText().toString().startsWith("0")
                && !editHeight.getText().toString().isEmpty()
                && !editAge.getText().toString().startsWith("0")
                && !editAge.getText().toString().isEmpty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_list) {
            openListCalcActivity();
            return true;
        }
        return false;
        //switch (item.getItemId()) {
        //   case R.id.menu_list:
        //     openListCalcActivity();
        //   return true;
        //default:
        //  return super.onOptionsItemSelected(item);
        // }
    }

    private void openListCalcActivity() {
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
        intent.putExtra("type", "tmb");
        startActivity(intent);
    }
}
