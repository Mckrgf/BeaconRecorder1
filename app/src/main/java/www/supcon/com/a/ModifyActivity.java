package www.supcon.com.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyActivity extends AppCompatActivity {
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_num) EditText etNum;
    @BindView(R.id.et_x) EditText etX;
    @BindView(R.id.et_y) EditText etY;
    @BindView(R.id.et_z) EditText etZ;
    @BindView(R.id.et_floor) EditText etFloor;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private static final String TAG = "MainActivity";
    private List beacons;
    private BeaconDao beaconDao;
    private String numId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);


        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        beaconDao = daoSession.getBeaconDao();
        beacons = beaconDao.queryBuilder().build().list();
        setView();

    }

    private void setView() {
        numId = getIntent().getStringExtra("id");

        Beacon map1 = null;
        for (int i=0;i<beacons.size();i++){
            Beacon map = (Beacon) beacons.get(i);
            if (map.getNum().equals(numId)){
                map1 = map;
            }
        }
        if (map1 == null){
            return;
        }
        etNum.setText(map1.getNum());
        etNum.setEnabled(false);
        etId.setText(map1.getB_id());
        etX.setText(map1.getX());
        etY.setText(map1.getY());
        etZ.setText(map1.getZ());
        etFloor.setText(map1.getFloor());

    }

    @OnClick( R.id.bt_confirm)
    public void onViewClicked(View view) {
        confirmData();
    }

    private void confirmData() {
        String id = etId.getText().toString();
        String num = etNum.getText().toString();
        String x = etX.getText().toString();
        String y = etY.getText().toString();
        String z = etZ.getText().toString();
        String floor = etFloor.getText().toString();






        //修改数据
            List b = beaconDao.queryBuilder().where(BeaconDao.Properties.B_id.eq(id)).list();
            Beacon beacon1 = beaconDao.queryBuilder()
                    .where(BeaconDao.Properties.Num.eq(numId))
                    .unique();
            if (b.size()>0){
                Beacon beaconB = (Beacon) b.get(0);
                if (!beaconB.getNum().equals(numId)) {
                    Toast.makeText(this, "id重复", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            beacon1.setB_id(id);
            beacon1.setNum(numId);
            beacon1.setX(x);
            beacon1.setY(y);
            beacon1.setZ(z);
            beacon1.setFloor(floor);
            beaconDao.update(beacon1);
            Toast.makeText(this, "修改成功~", Toast.LENGTH_SHORT).show();
            finish();
            return;


    }
}
