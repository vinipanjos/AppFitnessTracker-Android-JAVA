package co.tiagoaguiar.codelab.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rv_main); //recicler view

        List<MainItem> mainItems = new ArrayList<>(); // modelo de dados
        mainItems.add(new MainItem(1, R.drawable.ic_baseline_accessibility_new_24, R.string.label_imc, Color.GREEN));
        mainItems.add(new MainItem(2, R.drawable.ic_baseline_visibility_24, R.string.label_tmb, Color.YELLOW));

        //Definir o comportamento de exibição do layout da RecyclerView
        //Pode ser: mosaic, grid e linear(Horizontal ou vertical)
        rvMain.setLayoutManager(new GridLayoutManager(this, 2)); //gerenciador de layout
        MainAdapter adapter = new MainAdapter(mainItems);
        adapter.setListener(id -> {
            switch (id) {
                case 1:
                    startActivity(new Intent(MainActivity.this, ImcActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, TmbActivity.class));
                    break;
            }

        });
        rvMain.setAdapter(adapter);
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

        private List<MainItem> mainItems;
        private OnItemClickListener listener;

        public MainAdapter(List<MainItem> mainItems) {
            this.mainItems = mainItems;
        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //1º - onCreateViewHolder (a qual a gente informa o layout que deve ser utilizado)
            // 2º onBindViewHolder ao qual manipulamos a posição
            // 3º getItemCount é onde informamos a quantidade de itens
            return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            MainItem mainItemCurrent = mainItems.get(position);
            holder.bind(mainItemCurrent);
        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }

        // É como se fosse a VIEW DA CELULA que está dentro do RecyclerView
        class MainViewHolder extends RecyclerView.ViewHolder {

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(MainItem item) {
                TextView txtName = itemView.findViewById(R.id.item_txt_name);
                ImageView imgIcon = itemView.findViewById(R.id.item_img_icon);
                LinearLayout container = (LinearLayout) itemView.findViewById(R.id.btn_imc);

                container.setOnClickListener(view -> {
                    listener.onClick(item.getId());

                });

                txtName.setText(item.getTextStringId());
                imgIcon.setImageResource(item.getDrawableId());
                container.setBackgroundColor(item.getColor());
            }
        }
    }
}
