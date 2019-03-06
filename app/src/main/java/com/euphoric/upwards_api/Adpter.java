package com.euphoric.upwards_api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;

import java.util.List;

public class Adpter extends RecyclerView.Adapter<Adpter.view_Holder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private Context context;
    private List<data_list> data;
    static int n = 50;

    public Adpter(Context context,List<data_list> data) {
        this.context = context;
        this.data = data;
    }



    @NonNull
    @Override
    public view_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            if(i == TYPE_ITEM){
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.item_list_view,viewGroup,false);
                return new view_Holder(view , i);
            }else if(i == TYPE_FOOTER){
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.footer,viewGroup,false);
                return new view_Holder(view , i);
            }
            throw new RuntimeException("type not found ");
    }

    @Override
    public void onBindViewHolder(@NonNull view_Holder view_holder, int i) {
        if(i != data.size()){
            data_list dl = data.get(i);
            view_holder.txtview.setText(dl.getData());
        }

    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public class view_Holder extends  RecyclerView.ViewHolder{

        TextView txtview;
        Button btn;

        public view_Holder(@NonNull View itemView , int i) {
            super(itemView);
                if(i == TYPE_ITEM){
                    txtview =  itemView.findViewById(R.id.item_text_view);
                }if(i == TYPE_FOOTER){
                    btn = itemView.findViewById(R.id.loadmorebutton);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        MainActivity mn = new MainActivity();
                            try {
                                mn.Data_parse(n);
                                n += 50;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == data.size()){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }

    }

}
