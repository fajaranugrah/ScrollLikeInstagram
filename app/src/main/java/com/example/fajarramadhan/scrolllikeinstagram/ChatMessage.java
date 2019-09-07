package com.example.fajarramadhan.scrolllikeinstagram;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatMessage extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    List<Items> listItems = new ArrayList<>();
    int totalPage, totalCoun;

    private static final int MAX_ITEMS_PER_REQUEST = 20;
    private static final int NUMBER_OF_ITEMS = 100;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1500;

    public ProgressBar progressBar;

    private LinearLayoutManager layoutManager;
    private List<Items> items;
    private int page = 1, pageCount = 1;
    private boolean loading = false, loading2 = false, alreadyLoad = false, firstTimeLoad = false, shortorLong = false;
    List<Items> itemsLocal;
    //String uuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        retrieveMessageTask();
    }

    public void retrieveMessageTask(){
        new RetrieveMessageSupportTask(getApplicationContext(), false) {
            @Override
            public void OnApiResult(int responseCode, int statusCode, String failReason) {
                if (statusCode == 1){
                    try {
                        totalPage = getTotalPg();
                        totalCoun = getTotalCount();
                        init(getInfo());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else if (statusCode == 2){
                    Toast.makeText(ChatMessage.this, "errorMsg", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ChatMessage.this, "errorMsg", Toast.LENGTH_SHORT).show();
                }
            }
        }.callApi("", "1");
    }

    public void loadMore(){
        new RetrieveMessageSupportTask(getApplicationContext(), false) {
            @Override
            public void OnApiResult(int responseCode, int statusCode, String failReason) {
                if (statusCode == 1){
                    try {
                        totalPage = getTotalPg();
                        totalCoun = getTotalCount();
                        temp = new ArrayList<>();
                        temp.addAll(getInfo().getItems());
                        listItems.addAll(temp);
                        getItemsToBeLoaded();

                        //progressBar.setVisibility(View.GONE);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else if (statusCode == 2){
                    Toast.makeText(ChatMessage.this, "errorMsg", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ChatMessage.this, "errorMsg", Toast.LENGTH_SHORT).show();
                }
            }
        }.callApi("", String.valueOf(pageCount));
    }

    ArrayList<Items> temp;
    private void init(final MessageInfo myDataset){

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        /*recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);*/
        recyclerView.setLayoutManager(layoutManager);

        temp = new ArrayList<>();
        temp.addAll(myDataset.getItems());
        listItems.addAll(temp);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(listItems);
        recyclerView.setAdapter(mAdapter);

        // add InfiniteScrollListener as OnScrollListener
        recyclerView.addOnScrollListener(createInfiniteScrollListener());
        if (!firstTimeLoad){
            scrollToBottom();
            firstTimeLoad = true;
        }
    }

    @NonNull private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(MAX_ITEMS_PER_REQUEST, layoutManager) {
            @Override public void onScrolledToEnd(final int firstVisibleItemPosition) {
                Log.e("checkLoad", "not show progress " + firstVisibleItemPosition);
                if (!loading2){
                    simulateLoading();
                }
                if (alreadyLoad){
                    if (pageCount <= totalPage) {
                        if (!loading){
                            Log.e("checkLoad", "not show progress " + firstVisibleItemPosition + " && " + mAdapter.getItemCount());
                            refreshView(recyclerView, new MyAdapter(itemsLocal), firstVisibleItemPosition);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Log.e("checkLoad", "show progress");
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("checkLoad", "yeah load");
                    alreadyLoad = false;
                }
            }
        };
    }

    @NonNull
    private void getItemsToBeLoaded() {
        List<Items> newItems = listItems;
        final List<Items> oldItems = ((MyAdapter) recyclerView.getAdapter()).getItems();
        final List<Items> itemsLocals = new LinkedList<>();
        itemsLocals.addAll(oldItems);
        itemsLocals.addAll(newItems);
        itemsLocal = itemsLocals;
        loading = false;
        loading2 = false;
        alreadyLoad = true;
        progressBar.setVisibility(View.GONE);
        //customeAdapter.setLoadEarlierMsgs(false);
        //return itemsLocal;
    }

    /**
     * WARNING! This method is only for demo purposes!
     * Don't do anything like that in your regular project!
     */
    private void simulateLoading() {
        //new simuLoading(ChatMessage.this).execute();
        new AsyncTask<Void, Void, Void>() {
            @Override protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override protected Void doInBackground(Void... params) {
                try {
                    try {
                        if (pageCount <= totalPage) {
                            pageCount = pageCount + 1;
                            Log.e("checkPage", "yeah load " + pageCount);
                            loadMore();
                            loading = true;
                            loading2 = true;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Thread.sleep(SIMULATED_LOADING_TIME_IN_MS);
                } catch (InterruptedException e) {
                    Log.e("MainActivity", e.getMessage());
                }
                return null;
            }

            @Override protected void onPostExecute(Void param) {
                //progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    /*public static class simuLoading extends AsyncTask<Void, Void, Void> {
        private final WeakReference<ChatMessage> mActivityRef;
        public simuLoading(ChatMessage activity){
            mActivityRef = new WeakReference<>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mActivityRef.get().progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void param) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                try {
                    if (mActivityRef.get().pageCount <= mActivityRef.get().totalPage) {
                        if (!mActivityRef.get().loading){
                            mActivityRef.get().pageCount = mActivityRef.get().pageCount + 1;
                            Log.e("checkPage", "yeah load " + mActivityRef.get().pageCount);
                            mActivityRef.get().loadMore();
                            mActivityRef.get().loading = true;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                Thread.sleep(SIMULATED_LOADING_TIME_IN_MS);
            } catch (InterruptedException e) {
                Log.e("MainActivity", e.getMessage());
            }
            return null;
        }
    }*/

    public void scrollToBottom() {
        if (mAdapter!=null){
            if (mAdapter.getItemCount() > 1){
                try {
                    recyclerView.getLayoutManager().scrollToPosition(0);
                }catch (Exception e){
                    //Log.e("IndexOutOfBounds", String.valueOf(e));
                    e.printStackTrace();
                }
            }
        }
    }
}
