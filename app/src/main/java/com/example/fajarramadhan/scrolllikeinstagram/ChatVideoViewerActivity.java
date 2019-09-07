package com.example.fajarramadhan.scrolllikeinstagram;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class ChatVideoViewerActivity extends FragmentActivity {


    String TYPE;
    VideoView chat_video_viewer;
    Context ctx;
    ProgressBar progressBar;
    TextView share_file;
    ImageView download_file;
    RelativeLayout header_image_view;
    final private int REQUEST_STORAGE_PERMISSION = 124;
    String[] PERMISSIONS_STO = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean showHeader = false;

    Handler mHandlerInit = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.e("checkTouch", "yeah post proses");
                if (showHeader){
                    Log.e("checkTouch", "yeah hide header 2");
                    header_image_view.setVisibility(View.VISIBLE);
                    //header_image_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                    mHandlerInit.removeCallbacks(mRunnable);
                    showHeader = false;
                } else {
                    Log.e("checkTouch", "yeah show header 1");
                    header_image_view.setVisibility(View.GONE);
                    mHandlerInit.removeCallbacks(mRunnable);
                    showHeader = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String date = sdf.format(new Date());
                        //new SaveWallpaperAsync(ChatVideoViewerActivity.this, getIntent().getStringExtra(PHOTO_URL), date).execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //new ThemeHelper(getApplicationContext(),this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat_image_viewer);
        chat_video_viewer = (VideoView) findViewById(R.id.chat_video_view);
        header_image_view = (RelativeLayout) findViewById(R.id.header_image_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        ctx = getApplicationContext();

        //MyApplication.fromPreviewImage = true;
        if(getIntent().hasExtra("type")){
            TYPE = getIntent().getStringExtra("type");
            if(TYPE.equals("chat_video")){
                init();
            }
        }

        share_file = (TextView) findViewById(R.id.share_file);
        download_file = (ImageView) findViewById(R.id.download_file);
        /*Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.share_file), iconFont);*/

        share_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //shareImage(getIntent().getStringExtra(PHOTO_URL));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        download_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!hasPermissions(ctx, PERMISSIONS_STO)){
                        ActivityCompat.requestPermissions(ChatVideoViewerActivity.this, PERMISSIONS_STO, REQUEST_STORAGE_PERMISSION);
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String date = sdf.format(new Date());
                        //new SaveWallpaperAsync(ChatVideoViewerActivity.this, getIntent().getStringExtra(PHOTO_URL), date).execute();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void init(){
        //Picasso.with(ctx).load(getIntent().getStringExtra(PHOTO_URL)).into(chat_image_viewer);
        chat_video_viewer.setVideoPath(getIntent().getStringExtra("url_video"));
        MediaController mc= new MediaController(ChatVideoViewerActivity.this);
        chat_video_viewer.setMediaController(mc);
        chat_video_viewer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                chat_video_viewer.seekTo(1);
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    private class SaveWallpaperAsync extends AsyncTask<String, String, String> {
        private Context context;
        private ProgressDialog pDialog;
        String image_url, ImageDate;
        URL ImageUrl;
        Bitmap bmImg = null;

        public SaveWallpaperAsync(Context context, String url, String date) {
            this.context = context;
            this.image_url = url;
            this.ImageDate = date;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("please wait");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            InputStream is = null;
            try {
                ImageUrl = new URL(image_url);
                //ImageUrl = new URL(args[0]);
                // myFileUrl1 = args[0];

                HttpURLConnection conn = (HttpURLConnection) ImageUrl
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassCastException er){
                er.printStackTrace();
                try {
                    ImageUrl = new URL(image_url);
                    HttpsURLConnection connection = (HttpsURLConnection) ImageUrl.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    is = connection.getInputStream();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    bmImg = BitmapFactory.decodeStream(is, null, options);
                } catch (IOException err){
                    err.printStackTrace();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            try {
                /*String path = ImageUrl.getPath();
                String idStr = path.substring(path.lastIndexOf('/') + 1);*/

                Long tsLong = System.currentTimeMillis();
                String NameImage = "PetBacker-"+ ImageDate + "-" + tsLong.toString() + ".jpg";

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsoluteFile() + "/PetBacker/");
                dir.mkdirs();
                String fileName = NameImage;
                File file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bmImg.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                File imageFile = file;
                MediaScannerConnection.scanFile(context,
                        new String[] { imageFile.getPath() },
                        new String[] { "image/jpeg" }, null);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            // TODO Auto-generated method stub
            if (bmImg == null) {
                Toast.makeText(context, "Image still loading...", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            } else {
                if (pDialog!=null) {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                }
                Toast.makeText(context, "Image Successfully Saved", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void shareImage(String photoUrl){
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            share.putExtra(Intent.EXTRA_STREAM, Uri.parse(photoUrl));
            startActivity(Intent.createChooser(share, "Share Image"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/Image Chat");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/PetBacker/ImageChat/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/PetBacker/ImageChat/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu, menu);

        menu.getItem(0).setVisible(false);
        if(TYPE.equals("my_service_image")){
            menu.getItem(0).setVisible(true);
        }*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            default:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
