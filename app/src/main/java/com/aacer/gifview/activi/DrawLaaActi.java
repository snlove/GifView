package com.aacer.gifview.activi;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aacer.gifview.R;
import com.aacer.gifview.adapaters.DLRecAdapater;
import com.aacer.gifview.fragments.DLInfoFrag;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by acer on 2016/2/15.
 */
public class DrawLaaActi extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView dlConten;
    private List<String> list;
    private DLRecAdapater recAdapater;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_layout);
        intiData();
        initView();
        initListener();

    }


    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.dlNav);
        dlConten = (RecyclerView) findViewById(R.id.dlContent);
        recAdapater = new DLRecAdapater(this, list);
        dlConten.setAdapter(recAdapater);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dlConten.setLayoutManager(linearLayoutManager);

        //设置ToolBAR
        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        setSupportActionBar(toolbar);

    }

    private void intiData() {
        list = new LinkedList<String>();
        list.add("姓名");
        list.add("个人签名");
        list.add("性别");
        list.add("爱好");
    }

    private void initListener() {
        recAdapater.setOnItemClickLitener(new DLRecAdapater.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                Fragment contentFragment = new DLInfoFrag();
                Bundle args = new Bundle();
                args.putString("text", list.get(position));
                contentFragment.setArguments(args);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.flContent, contentFragment)
                        .commit();

                //hide current drawlayout
                drawerLayout.closeDrawer(dlConten);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
        switch (id) {
            case R.id.action_search :

                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
