package zenghao.com.study.activity.ClassifyView;
 
import android.os.Bundle;
import android.support.annotation.Nullable; 
import android.support.design.widget.FloatingActionButton; 
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog; 
import android.support.v7.widget.GridLayoutManager; 
import android.support.v7.widget.RecyclerView; 
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
 

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.R;
import zenghao.com.study.view.classify.ClassifyView;

/** 
 * <p/> 
 * Date: 16/6/12 10:09 
 * Author: zhendong.wu@shoufuyou.com 
 * <p/> 
 */ 
public class DemonstrateFragment extends Fragment{
    private NetManager mNetManager = new NetManager();
    private List<List<Book>> mBooks = new ArrayList<>();
    private BookListAdapter mAdapter;
    @Nullable 
    @Override 
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demonstrate_main,container,false);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.add_button);
        ClassifyView classifyView = (ClassifyView) view.findViewById(R.id.classify_view);
        mAdapter = new BookListAdapter(mBooks);
        classifyView.setAdapter(mAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View content = LayoutInflater.from(v.getContext()).inflate(R.layout.select_book,null);
                RecyclerView recyclerView = (RecyclerView) content.findViewById(R.id.select_list);
                final ProgressBar progressBar = (ProgressBar) content.findViewById(R.id.progress_bar);
                recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(),2));
                final SelectBookListAdapter selectBookListAdapter = new SelectBookListAdapter();
                recyclerView.setAdapter(selectBookListAdapter);

                builder.setView(content);
                final AlertDialog dialog = builder.show();
                selectBookListAdapter.setItemClickListener(new SelectBookListAdapter.ItemClickListener() {
                    @Override 
                    public void onItemClick(View parent, int position,Book book) {
                        List<Book> books = new ArrayList<>();
                        books.add(book);
                        mBooks.add(books);
                        mAdapter.notifyItemInsert(mBooks.size()-1);
                        dialog.hide();
                    } 
                }); 
                progressBar.setVisibility(View.VISIBLE);
                mNetManager.getBookList(new BookListener() {
                    @Override 
                    public void onSuccess(String result) {
                        Log.d("wzd",result);
                        progressBar.setVisibility(View.INVISIBLE);
                        List<Book> books = new ArrayList<>();
                        try { 
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.optJSONArray("list");
                            if(jsonArray != null){
                                for(int i = 0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.optJSONObject(i);
                                    if(object != null){
                                        Book book = new Book();
                                        book.id = object.optString("id");
                                        book.imageUrl = "http://tnfs.tngou.net/img"+object.optString("img");
                                        book.name = object.optString("name");
                                        book.summary = object.optString("summary");
                                        books.add(book);
                                    } 
                                } 
                            } 
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } 
                        selectBookListAdapter.setBookList(books);
                    } 
 
                    @Override 
                    public void onFailure(Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(),"出错:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    } 
                }); 
            } 
        }); 
        return view;
    } 
} 