package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animals_plate.R;

import models.functionListModel;

public class functionListAdapter extends RecyclerView.Adapter<functionListAdapter.ViewHolder>{
    private functionListModel[] functionListModels;
    private Context context;

    public functionListAdapter(functionListModel[] functionListModels, Context context) {
        this.functionListModels = functionListModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.setting_functions_sample,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final functionListModel myListData = functionListModels[position];
        holder.name.setText(functionListModels[position].getName());
        holder.logo.setImageResource(functionListModels[position].getLogo());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (myListData.getName()){
                    case "關於我們":

                        break;
                    case "目前版本號":
                        AlertDialog.Builder adv = new AlertDialog.Builder(context);
                        adv.setTitle("目前最新版本");
                        adv.setMessage("202303.V1"+"\n"+"設計人員:"+"平凡的小朋友");
                        adv.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        adv.show();
                        break;
                    default:
                        Toast.makeText(context,myListData.getName(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return functionListModels.length;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public ImageView logo;

        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mobile_name);
            logo = itemView.findViewById(R.id.listlogo);
            relativeLayout = itemView.findViewById(R.id.setting_list);

        }
    }
}
