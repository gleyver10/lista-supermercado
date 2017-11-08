package br.m.listamercado.app;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list_view;
    EditText txt_produto;
    ProdutoAdapter adapter;
    Context context = this;

    View.OnClickListener click_ck = new View.OnClickListener() {
        public void onClick(View view) {
            CheckBox ck = (CheckBox) view;
            int posicao = (int) ck.getTag();
            Produto produtoSelecionado = adapter.getItem(posicao);

            Produto produtoDB= Produto.findById(Produto.class, produtoSelecionado.getId());
            //salva na memoria . ...___--->>produtoSelecionado.setAtivo(true);


            // checka no banco se ta ativo e salva no banco
            if (ck.isChecked()){
                produtoDB.setAtivo(true);
                produtoDB.save();
                produtoSelecionado.setAtivo(true);
            }else {
                produtoDB.setAtivo(false);
                produtoDB.save();
                produtoSelecionado.setAtivo(false);
            }



            adapter.notifyDataSetChanged();

            //Toast.makeText(context, "olaaaa",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Context context = this;
        list_view = (ListView) findViewById(R.id.list_view);
        txt_produto = (EditText) findViewById(R.id.txt_produto);


        List<Produto> lstProdutos =  Produto.listAll(Produto.class);

        /*Dados fake
        lstProdutos.add(new Produto("Arroz", false));
        lstProdutos.add(new Produto("Feijão", true));
        lstProdutos.add(new Produto("Luis", true));*/

        adapter  = new ProdutoAdapter(this, lstProdutos);

        list_view.setAdapter(adapter);
    }

    public void inserirItem(View view) {
        String produto = txt_produto.getText().toString();

        if (produto.isEmpty())return;
        //Criando o obj produto
        Produto p = new Produto(produto, false);

        //Adiciona na lista
        adapter.add(p);

        //adiconando no banco
        p.save();

        //limpar caixinha
        txt_produto.setText(null);
    }

    //Adapter obejeto responsavel para pegar os itens do array list e joga na lista
    private  class ProdutoAdapter extends ArrayAdapter<Produto>{
        public ProdutoAdapter(Context context, List<Produto> itens){
            super(context, 0, itens);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View v = convertView;

            if (v == null){
                 v= LayoutInflater.from(getContext()).inflate(R.layout.item_lista, null);
            }

            Produto item = getItem(position);

            TextView txt_item_produto =v.findViewById(R.id.txt_item_produto);
            CheckBox ck_item_produto = v.findViewById(R.id.ck_item_produto);

            txt_item_produto.setText(item.getNome());
            ck_item_produto.setChecked(item.isAtivo());
            //guardando a posição do item
            ck_item_produto.setTag(position);
            ck_item_produto.setOnClickListener(click_ck);

            return v;

        }
    }

}
