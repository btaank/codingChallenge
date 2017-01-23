package com.example.bharti.codingchallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.data;
//import static android.R.attr.name;
import static com.example.bharti.codingchallenge.R.id.tablerow;
//import static com.example.bharti.codingchallenge.R.id.text;

public class FirstPage extends AppCompatActivity {

    ArrayList<HashMap<String, String>> userList = new ArrayList();
    String tag;
    TableLayout tl;
    TableRow itemsName;
    //TextView tvItemName0;
    private static final String id1 = "Avatar";
    private static final String id2 = "Name";
    private static final String id3 = "Text";
   // TextView tvItemName1;
    public String url;
   // ImageView tvItemName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
    }

    @Override
    protected void onStart() {
        super.onStart();
         url = "https://alpha-api.app.net/stream/0/posts/stream/global";
        MyTask task = new MyTask();
        task.execute(url);
    }

    public class MyTask extends AsyncTask < String, String, ArrayList> {
        HttpURLConnection conn ;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream;
        URL downloadUrl;
        String data;


        @Override
        protected ArrayList<HashMap<String,String>> doInBackground(String... params) {
            try {

                downloadUrl = new URL(url);
                conn = (HttpURLConnection) downloadUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // conn.setDoOutput(true);
                conn.connect();
                int status = conn.getResponseCode();
                Log.d(tag, "" + status);
                inputStream = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = br.readLine()) != null)
                    buffer.append(line + "rn");

                inputStream.close();
                conn.disconnect();
                data = buffer.toString();
                JSONObject jObj = new JSONObject(data);

                JSONArray dataList = jObj.getJSONArray("data");
                // JSONObject time = mainObj.getJSONObject("created_at");

                for (int i = 0; i < dataList.length(); i++) {
                    JSONObject c = dataList.getJSONObject(i);
                    String time = c.getString("created_at");
                    String text = c.getString("text");

                    JSONObject user = c.getJSONObject("user");
                    String username = user.getString("username");

                    JSONObject userI = user.getJSONObject("avatar_image");
                    String imageUrl = userI.getString("url");

                    Log.d(tag, imageUrl);

                    HashMap<String, String> data1 = new HashMap<String, String>();

                    data1.put(id1, imageUrl);
                    data1.put(id2, username);
                    data1.put(id3, text);
                    data1.put("time", time);

                    userList.add(data1);
                    try {
                        Bitmap bit = BitmapFactory.decodeStream((InputStream) new URL(userList.get(i).get(id1)).getContent());
                        String temp = BitMapToString(bit);
                    }
                catch(IOException e){
                    e.printStackTrace();
                }
                    public String BitMapToString(Bitmap bit) {
                        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.PNG,100, baos);
                        byte [] b=baos.toByteArray();
                        String temp= Base64.encodeToString(b, Base64.DEFAULT);
                        return temp;
                    }
            }}

        catch (MalformedURLException e) {
                e.printStackTrace();
            }
                catch (IOException e) {
                e.printStackTrace();}
                catch (JSONException e) {
                e.printStackTrace();}

            return userList;
            }

        protected void onPostExecute(ArrayList<HashMap<String,String>> result) {
            super.onPostExecute(result);
            Log.d(tag, Integer.toString(userList.size()));
            for (int i = 0; i < userList.size(); i++) {

                itemsName = (TableRow) findViewById(tablerow);
                ImageView iv = (ImageView) itemsName.getChildAt(0);
                TextView tv1 = (TextView) itemsName.getChildAt(1);
                TextView tv2 = (TextView) itemsName.getChildAt(2);

                itemsName.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                String id = userList.get(i).get(id2);
                tv1.setText(id);

                tv2.setText(userList.get(i).get(id3));
               // Bitmap bmp = BitmapFactory.decodeFile(id1);
              /*  try {
                    Bitmap bit = BitmapFactory.decodeStream((InputStream)new URL(userList.get(i).get(id1)).getContent());
                    iv.setImageBitmap(bit);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                itemsName.addView(iv);
                itemsName.addView(tv1);
                itemsName.addView(tv2);
                tl.addView(itemsName, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.MATCH_PARENT));


            }
        }
    }
}
