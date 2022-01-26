package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extras = getIntent().getExtras();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_list);

        if (extras != null) {
            String type = extras.getString("type");

            new Thread(() -> {
                List<Register> registers = SqlHelper.getInstance(this).getRegisterBy(type);

                runOnUiThread(() -> {
                    Log.d("Teste", registers.toString());
                    ListCalcAdapter adapter = new ListCalcAdapter(registers);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(adapter);

                });
            }).start();
        }
    }

    class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder> {

        private List<Register> datas;

        public ListCalcAdapter(List<Register>datas) {
            this.datas = datas;
        }

        @NonNull
        @Override
        public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //1º - onCreateViewHolder (a qual a gente informa o layout que deve ser utilizado)
            // 2º onBindViewHolder ao qual manipulamos a posição
            // 3º getItemCount é onde informamos a quantidade de itens
            return new ListCalcViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListCalcViewHolder holder, int position) {
            Register data = datas.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        // É como se fosse a VIEW DA CELULA que está dentro do RecyclerView
        class ListCalcViewHolder extends RecyclerView.ViewHolder {

            public ListCalcViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(Register data) {
                String formatted = "";

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "br"));
                    Date dateSaved = sdf.parse(data.createdDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "br"));
                    formatted = dateFormat.format(dateSaved);
                } catch (ParseException e) {
                }
                ((TextView) itemView).setText(
                        getString(R.string.list_response, data.response, formatted));
            }
        }
    }
}