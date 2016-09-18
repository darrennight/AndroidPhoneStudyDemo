package zenghao.com.study.RecyclerViewDemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/8/29.
 *
 * 这里注意：如果RecyclerView中加载了大量数据，那么算法可能不会马上完成，要注意ANR的问题，可以开启单独的线程进行计算。
 */
public class TestDiffUtilActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Button mNomalNoti;
    private Button mDiffUtil;
    private MyAdapter adapter;
    private List<Student> students;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_diffutil);
        mNomalNoti = ((Button) findViewById(R.id.btn_noti));
        mDiffUtil = ((Button) findViewById(R.id.btn_diff));
        mRecyclerView = ((RecyclerView) findViewById(R.id.rv_list));

        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyAdapter(this);
        adapter.setData(students);
        mRecyclerView.setAdapter(adapter);

        mNomalNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeData();
                adapter.setData(students);
                adapter.notifyDataSetChanged();
            }
        });

        mDiffUtil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }


    private void initData() {
        students = new ArrayList<>();
        Student s1 = new Student("John", 1);
        Student s2 = new Student("Curry", 2);
        Student s3 = new Student("Rose", 3);
        Student s4 = new Student("Dante", 4);
        Student s5 = new Student("Lunar", 5);
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
    }

    private void changeData() {
        students.set(1, new Student("Fndroid", 2));
        students.add(new Student("Jason", 8));

        Student s2 = students.get(2);
        students.remove(2);
        students.add(s2);

    }

    public void change() {
        students.set(1, new Student("Fndroid", 2));
        students.add(new Student("Jason", 8));
        Student s2 = students.get(2);
        students.remove(2);
        students.add(s2);

        ArrayList<Student> old_students = adapter.getData();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyDiffCallback(old_students, students), true);
        adapter.setData(students);
        result.dispatchUpdatesTo(adapter);
    }

    class MyAdapter extends RecyclerView.Adapter {

        private ArrayList<Student> data;
        private Context mContext;


        public MyAdapter(Context context) {
            this.mContext = context;
        }

        public ArrayList<Student> getData() {
            return data;
        }

        public void setData(List<Student> data) {
            this.data = new ArrayList<>(data);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_test_diff, parent, false);
            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Student student = data.get(position);
            myViewHolder.tv.setText(student.getNum() + "." + student.getName());
        }


        /*@Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
            //super.onBindViewHolder(holder, position, payloads);

            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Student student = data.get(position);
            myViewHolder.tv.setText(student.getNum() + "." + student.getName());
        }*/

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.item_tv);
            }
        }
    }

}
