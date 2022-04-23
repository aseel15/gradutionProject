package com.example.graduationproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


import org.json.JSONArray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.graduationproject.Model.Post;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class postNews extends AppCompatActivity {


    private RequestQueue queue;
    private RequestQueue queue1;
    private static final String BASE_URL = "http://10.0.2.2:82/GraduationProject/getPostData.php";
    RecyclerView recycler;

    List<Post> posts=new ArrayList<>();

    //Toolbar toolbar;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    CircleImageView imageView;
    String UserName,imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.post_recycler);
        imageView=findViewById(R.id.imageView);
        getName();
         //   toolbar = findViewById(R.id.toolbar);
       //     setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Posts");
           // drawerLayout = findViewById(R.id.drawer_layout);
         //   ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_Drawer_Open, R.string.navigation_Drawer_Close);
          //  drawerLayout.addDrawerListener(toggle);
          //  toggle.syncState();

          //  NavigationView navigationView = findViewById(R.id.nav_view);
           // navigationView.setNavigationItemSelectedListener(this);

            queue = Volley.newRequestQueue(this);
            queue1 = Volley.newRequestQueue(this);
            if (savedInstanceState!=null)
                onRestoreInstanceState(savedInstanceState);


            populateAllData();


    }
    public void populateAllData(){

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray roomList=new JSONArray(response);
                            for(int i=0;i<roomList.length();i++){
                                JSONObject jsonObject= roomList.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String date = jsonObject.getString("date");

                                String text = jsonObject.getString("post");
                                String imageName = jsonObject.getString("image");

                                Post post = new Post(name, date, text, imageName);
                                posts.add(post);

                            }

                            recycler.setLayoutManager(new LinearLayoutManager(postNews.this));
                            postCardView adapter = new postCardView(postNews.this,posts);

                            recycler.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(postNews.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
    public void btnClkPost(View view) {
        Intent intent =new Intent(postNews.this,writePost.class);
        intent.putExtra("name",UserName);
        intent.putExtra("image",imageName);
        startActivity(intent);
    }


    public void getName() {
        int id = 2;
        String url = "http://10.0.2.2:82/GraduationProject/searchName.php?id=" + id;
        RequestQueue queue = Volley.newRequestQueue(postNews.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    UserName = jsonObject.getString("name");
                    imageName=jsonObject.getString("image");
                    Glide.with(postNews.this).load(imageName).into(imageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(postNews.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }}
//    List<Post> ListOfPost;
//    RecyclerView recyclerView;
//    TextView textView;
//    TextView textView2;
//
//    // Button get;
//    String HTTP_JSON_URL;
//    String Image_Name_JSON = "id";
//    String Image_URL_JSON = "image_path";
//    JsonArrayRequest RequestOfJSonArray ;
//    RequestQueue requestQueue ;
//    View view ;
//    int RecyclerViewItemPosition ;
//    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
//    RecyclerView.Adapter recyclerViewadapter;
//    ArrayList<String> ImageTitleNameArrayListForClick;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
//        textView = findViewById(R.id.Name);
//        textView = findViewById(R.id.Date);
//
//        ImageTitleNameArrayListForClick = new ArrayList<>();
//
//        ListOfPost = new ArrayList<>();
//
//        recyclerView = (RecyclerView) findViewById(R.id.post_recycler);
//
//        recyclerView.setHasFixedSize(true);
//
//        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
//
//        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);
//
//
//
//        // Implementing Click Listener on RecyclerView.
//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//
//            GestureDetector gestureDetector = new GestureDetector(postNews.this, new GestureDetector.SimpleOnGestureListener() {
//
//                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {
//
//                    return true;
//                }
//
//            });
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
//
//                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
//
//                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {
//
//                    //Getting RecyclerView Clicked Item value.
//                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);
//
//                    // Showing RecyclerView Clicked Item value using Toast.
//                    Toast.makeText(postNews.this, ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
//
//
//
//                }
//
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
//
//
//    }
//
//    public void JSON_HTTP_CALL(){
//
//        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,
//
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        ParseJSonResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        requestQueue = Volley.newRequestQueue(postNews.this);
//
//        requestQueue.add(RequestOfJSonArray);
//    }
//
//    public void ParseJSonResponse(JSONArray array){
//
//        for(int i = 0; i<array.length(); i++) {
//
//            Post GetDataAdapter2 = new Post();
//
//            JSONObject json = null;
//            try {
//
//                json = array.getJSONObject(i);
//
//
//                // Adding image title name in array to display on RecyclerView click event.
//                ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
//
//                GetDataAdapter2.setImageURL(json.getString(Image_URL_JSON));
//
//
//
//            } catch (JSONException e) {
//
//                e.printStackTrace();
//            }
//            ListOfPost.add(GetDataAdapter2);
//        }
//
//        recyclerViewadapter = new postCardView(this, ListOfPost);
//
//        recyclerView.setAdapter(recyclerViewadapter);
//    }
//
//    private void getImage() {
//        String id = textView.getText().toString().trim();
//        class GetImage extends AsyncTask<String,Void, Bitmap> {
//
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap b) {
//                super.onPostExecute(b);
//
//                JSON_HTTP_CALL();
//            }
//
//            @Override
//            protected Bitmap doInBackground(String... params) {
//                String id = params[0];
//                HTTP_JSON_URL = "paste_your_url_address?id="+id;
//                URL url = null;
//                Bitmap image = null;
//                try {
//                    url = new URL(HTTP_JSON_URL);
//                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return image;
//            }
//        }
//
//        GetImage gi = new GetImage();
//        gi.execute(id);
//    }
//
//}
