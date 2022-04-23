package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class writePost extends AppCompatActivity {




    TextView Name;
    EditText edtPost;
    Button btnPost;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        Intent intent=getIntent();
        String userName=intent.getStringExtra("name");
        String imageName=intent.getStringExtra("image");

        Name=findViewById(R.id.Name);
        Name.setText(userName);

        imageView=findViewById(R.id.imageView);
        Glide.with(writePost.this).load(imageName).into(imageView);

        edtPost=findViewById(R.id.Post);
        btnPost=findViewById(R.id.btnPost);

    }

    public void btnClkPost(View view) {
        int id=2;
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        String date = formattedDate ;

        String post=edtPost.getText().toString();
        addPost(String.valueOf(id),date,post);
        Intent intent =new Intent(writePost.this,postNews.class);
        startActivity(intent);

    }

    private void addPost(String userID, String date, String post) {


        String url = "http://10.0.2.2:82/GraduationProject/addPost.php";
        RequestQueue queue = Volley.newRequestQueue(writePost.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(writePost.this,
                            date, Toast.LENGTH_SHORT).show();
                    Toast.makeText(writePost.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(writePost.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", userID);
                params.put("date", date);
                params.put("post", post);


                return params;
            }
        };
        queue.add(request);
    }

//    public void btnClkShow(View view) {
//        if (!edtPost.getText().toString().isEmpty())
//            btnPost.setVisibility(View.VISIBLE);
//        else
//         btnPost.setVisibility(View.GONE);
//    }
}