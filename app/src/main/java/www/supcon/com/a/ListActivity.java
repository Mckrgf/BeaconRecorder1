package www.supcon.com.a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.list) RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        BeaconDao beaconDao = daoSession.getBeaconDao();
        List beacons = beaconDao.queryBuilder().build().list();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        WorkListAdapter workListAdapter = new WorkListAdapter(this);


        workListAdapter.setData(beacons,beaconDao);
        list.setAdapter(workListAdapter);
    }
}
