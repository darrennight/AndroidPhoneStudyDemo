package zenghao.com.study.VHRecyleView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zenghao.com.study.R;

public class VPRecyleMainActivity extends AppCompatActivity {

    MaterialLeanBack materialLeanBack;

    Toolbar toolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vh_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        materialLeanBack = (MaterialLeanBack) findViewById(R.id.materialLeanBack);

        materialLeanBack.setCustomizer(new MaterialLeanBack.Customizer() {
            @Override
            public void customizeTitle(TextView textView) {
                textView.setTypeface(null, Typeface.BOLD);
            }
        });

        //Picasso.with(getApplicationContext())
        //        .load("http://www.journaldugeek.com/wp-content/blogs.dir/1/files/2015/01/game-of-thrones-saison-5-documentaire.jpg")
        //        .fit().centerCrop()
        //        .into(materialLeanBack.getImageBackground());

        materialLeanBack.setAdapter(new MaterialLeanBack.Adapter<TestViewHolder>() {
            @Override
            public int getLineCount() {
                return 10;
            }

            @Override
            public int getCellsCount(int line) {
                return 5;
            }

            @Override
            public TestViewHolder onCreateViewHolder(ViewGroup viewGroup, int line) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_test, viewGroup, false);
                return new TestViewHolder(view);
            }

            @Override
            public void onBindViewHolder(TestViewHolder viewHolder, int i) {
                viewHolder.textView.setText("test " + i);

                String url = "http://www.lorempixel.com/40" + viewHolder.row + "/40" + viewHolder.cell + "/";
            }

            @Override
            public String getTitleForRow(int row) {
                return "Line " + row;
            }

            //region customView
            @Override
            public RecyclerView.ViewHolder getCustomViewForRow(ViewGroup viewgroup, int row) {
                if (row >= 3 && row <= 6) {
                    View view = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.layout_header_vh, viewgroup, false);
                    return new RecyclerView.ViewHolder(view) {};
                } else
                    return null;
            }

            @Override
            public boolean isCustomView(int row) {
                if(row >= 3 && row <= 6 ){
                    return true;
                }
                return false;
            }

            @Override
            public void onBindCustomView(RecyclerView.ViewHolder viewHolder, int row) {
                super.onBindCustomView(viewHolder, row);
            }

            //endregion

        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }
}
