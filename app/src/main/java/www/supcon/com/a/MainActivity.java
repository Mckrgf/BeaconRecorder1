package www.supcon.com.a;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_id) EditText etId;
    @BindView(R.id.et_num) EditText etNum;
    @BindView(R.id.et_x) EditText etX;
    @BindView(R.id.et_y) EditText etY;
    @BindView(R.id.et_z) EditText etZ;
    @BindView(R.id.et_floor) EditText etFloor;
    @BindView(R.id.bt_confirm) Button btConfirm;
    @BindView(R.id.bt_look) Button btLook;
    private static final String TAG = "MainActivity";
    @BindView(R.id.bt_save) Button btSave;
    private List beacons;
    private BeaconDao beaconDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        beaconDao = daoSession.getBeaconDao();
        beacons = beaconDao.queryBuilder().build().list();


        Log.d(TAG, beacons.size() + "====");
        etNum.setText(beacons.size() + 1 + "");
    }



    @OnClick({R.id.et_id, R.id.et_num, R.id.et_x, R.id.et_y, R.id.et_z, R.id.et_floor, R.id.bt_confirm, R.id.bt_look, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_id:
                break;
            case R.id.et_num:
                break;
            case R.id.et_x:
                break;
            case R.id.et_y:
                break;
            case R.id.et_z:
                break;
            case R.id.et_floor:
                break;
            case R.id.bt_confirm:
                confirmData();
                break;
            case R.id.bt_look:
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_save:
                String sss = "序号id		x	y	z	楼层\n";
                List bbb = beaconDao.queryBuilder().build().list();
                if (bbb.size()>0) {
                    for (int i = 0; i < bbb.size(); i++) {
                        Beacon beacon = (Beacon) bbb.get(i);
                        sss = sss+beacon.getNum()+"	"+beacon.getB_id()+"	"+beacon.getX()+"	"+beacon.getY()+"	"+beacon.getZ()+"	"+beacon.getFloor()+"\n";
                    }
                    Log.d(TAG,sss);
                }else {
                    Toast.makeText(this,"没有记录",Toast.LENGTH_SHORT).show();
                }

                try {
                    FileWriter fw = new FileWriter("/sdcard" + "/cmd.xls");//SD卡中的路径
                    fw.flush();
                    fw.write(sss);
                    fw.close();
                    Toast.makeText(this,"保存成功，路径为sd卡下 cmd.xls",Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void confirmData() {
        String id = etId.getText().toString();
        String num = etNum.getText().toString();
        String x = etX.getText().toString();
        String y = etY.getText().toString();
        String z = etZ.getText().toString();
        String floor = etFloor.getText().toString();




        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("num", num);
        data.put("x", x);
        data.put("y", y);
        data.put("z", z);
        data.put("floor", floor);
        Beacon beacon = new Beacon();
        beacon.setB_id(id);
        beacon.setNum(num);
        beacon.setX(x);
        beacon.setY(y);
        beacon.setZ(z);
        beacon.setFloor(floor);



        //插入前先判断该id和num有没有已有的beacon

        List a = beaconDao.queryBuilder().where(BeaconDao.Properties.Num.eq(beacon.getNum())).list();
        List b = beaconDao.queryBuilder().where(BeaconDao.Properties.B_id.eq(beacon.getB_id())).list();

        if (a.size() == 0 && b.size() == 0) {
            beaconDao.insert(beacon);
        } else if (a.size() == 0) {
            Toast.makeText(this, "序号重复", Toast.LENGTH_SHORT).show();
        } else if (b.size() == 0) {
            Toast.makeText(this, "id重复", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "保存后记录数量：" + beaconDao.queryBuilder().build().list().size());

        etNum.setText(beaconDao.queryBuilder().build().list().size() + 1 + "");
        etId.setText(beaconDao.queryBuilder().build().list().size() + 1 + "");
        etX.setText("");
        etY.setText("");
        etZ.setText("");
        etFloor.setText("");
    }
}
