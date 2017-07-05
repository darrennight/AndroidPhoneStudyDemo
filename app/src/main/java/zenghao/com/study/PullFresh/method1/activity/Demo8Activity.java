package zenghao.com.study.PullFresh.method1.activity;
 
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity; 
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager; 
import android.support.v7.widget.RecyclerView; 
import android.support.v7.widget.Toolbar; 
import android.support.v7.widget.helper.ItemTouchHelper; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.PullFresh.method1.DefaultFooter;
import zenghao.com.study.PullFresh.method1.DefaultHeader;
import zenghao.com.study.PullFresh.method1.SpringView;
import zenghao.com.study.R;

public class Demo8Activity extends AppCompatActivity { 
    private List<String> mDatas = new ArrayList<String>();
    private SpringView springView;
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo8);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 
        initData(); 
 
        springView = (SpringView) findViewById(R.id.springview);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override 
            public void onRefresh() { 
                new Handler().postDelayed(new Runnable() {
                    @Override 
                    public void run() { 
                        springView.onFinishFreshAndLoad();
                    } 
                }, 1000); 
            } 
 
            @Override 
            public void onLoadmore() { 
                new Handler().postDelayed(new Runnable() {
                    @Override 
                    public void run() { 
                        springView.onFinishFreshAndLoad();
                    } 
                }, 1000); 
            } 
        }); 
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
 
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(R.layout.item_pull_fresh, mDatas);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
 
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(recyclerAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    } 
 
    private void initData(){ 
        for (int i = 0; i < 20; i++) {
            mDatas.add(i==0?"SpringView处理了水平滑动的手势冲突，侧滑删除试试":"item"+i);
        } 
    } 
 
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements MyItemTouchCallback.ItemTouchAdapter { 
 
        private Context context;
        private int src;
        private List<String> results;
 
        public RecyclerAdapter(int src, List<String> results){
            this.results = results;
            this.src = src;
        } 
 
        @Override 
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            this.context = parent.getContext();
            View itemView = LayoutInflater.from(parent.getContext()).inflate(src, parent, false);
            return new MyViewHolder(itemView);
        } 
 
        @Override 
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.textView.setText(results.get(position));
        } 
 
        @Override 
        public int getItemCount() { 
            return results.size();
        } 
 
        @Override 
        public void onMove(int fromPosition, int toPosition) {
        } 
 
        @Override 
        public void onSwiped(int position) {
            results.remove(position);
            notifyItemRemoved(position);
        } 
 
        public class MyViewHolder extends RecyclerView.ViewHolder{ 
 
            public TextView textView;
 
            public MyViewHolder(View itemView) {
                super(itemView);
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                layoutParams.height = 100;
                itemView.setLayoutParams(layoutParams);
                textView = (TextView) itemView.findViewById(R.id.item_text);
            } 
        } 
    } 
 
} 