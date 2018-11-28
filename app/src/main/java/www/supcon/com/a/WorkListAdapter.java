package www.supcon.com.a;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;


/**
 * Created by yaobing on 2018/4/4.
 * Description xxx
 */

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.ViewHolder> implements View.OnClickListener {
    private List datas;
    private Context context;
    private OnItemClickListener mItemClickListener;
    private BeaconDao beaconDao;

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    public void setData(List data, BeaconDao beaconDao) {
        datas = data;
        this.beaconDao = beaconDao;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public WorkListAdapter(Context context) {
        this.context = context;
    }

    public WorkListAdapter(List data, Context context) {
        this.datas = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beacon, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    private static final String TAG = "WorkListAdapter";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Beacon map = (Beacon) datas.get(position);
        holder.tv_num.setText(map.getNum());
        holder.tv_id.setText(map.getB_id());
        holder.tv_x.setText(map.getX());
        holder.tv_y.setText(map.getY());
        holder.tv_z.setText(map.getZ());
        holder.tv_floor.setText(map.getFloor());

        holder.bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        map.setNum(String.valueOf(holder.tv_num.getText()));
//                        map.setB_id(String.valueOf(holder.tv_id.getText()));
//                        map.setX(String.valueOf(holder.tv_x.getText()));
//                        map.setY(String.valueOf(holder.tv_y.getText()));
//                        map.setZ(String.valueOf(holder.tv_z.getText()));
//                        map.setFloor(String.valueOf(holder.tv_floor.getText()));
//                        beaconDao.update(map);
//
//                        setData(beaconDao.loadAll(), beaconDao);


                        //回到 MainActivity 修改数据
                        Intent intent = new Intent(context,ModifyActivity.class);
                        intent.putExtra("id",map.getNum());
                        context.startActivity(intent);

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "取消");
                        dialog.cancel();
                    }
                });
                builder.setMessage("确定要修改吗");
                builder.setTitle("提示");
                builder.show();
            }
        });

        //删除
        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DeleteQuery<Beacon> tableDeleteQuery = beaconDao.queryBuilder()
                                .where(BeaconDao.Properties.Num.eq(map.getNum()))
                                .buildDelete();
                        tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                        datas.remove(position);
                        notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "取消");
                        dialog.cancel();
                    }
                });
                builder.setMessage("确定要修改吗");
                builder.setTitle("提示");
                builder.show();

            }
        });

        holder.itemView.setTag(position);


    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_num;
        TextView tv_id;
        TextView tv_x;
        TextView tv_y;
        TextView tv_z;
        TextView tv_floor;
        Button bt_change;
        Button bt_delete;

        private ViewHolder(View view) {
            super(view);
            tv_num = view.findViewById(R.id.tv_num);
            tv_id = view.findViewById(R.id.tv_id);
            tv_x = view.findViewById(R.id.tv_x);
            tv_y = view.findViewById(R.id.tv_y);
            tv_z = view.findViewById(R.id.tv_z);
            tv_floor = view.findViewById(R.id.tv_floor);
            bt_change = view.findViewById(R.id.bt_change);
            bt_delete = view.findViewById(R.id.bt_delete);
        }
    }

}
