package com.example.gurki.cst2335_finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*
@Author: Mahdi Khiari
 */
public class CBCNewsReaderActivity extends AppCompatActivity {

    ListView newsList;
    Button searchButton;
    EditText searchEdit;
    ImageButton cbcImage;
    Context context = this;
    ArrayList<String> listView = new ArrayList<String>();
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbcnews_reader);

        newsList = findViewById(R.id.cbc_newsList);
        searchButton = findViewById(R.id.cbc_searchButton);
        searchEdit = findViewById(R.id.cbc_searchEdit);
        cbcImage = findViewById(R.id.cbc_newsImage);
        doc = null;

//        try {
//            URL url = new URL("https://www.cbc.ca/cmlink/rss-world");
//            URLConnection obj = url.openConnection();
//            DocumentBuilderFactory factory = DocumentBuil;derFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            doc = builder.parse(obj.getInputStream());
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        NodeList items = doc.getElementsByTagName("item");
//
//        for (int i = 0; i < items.getLength(); i++){
//            Element element = (Element) items.item(i);
//            System.out.print(element.getElementsByTagName("title").item(i).getTextContent());
//        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listView );
        newsList.setAdapter(arrayAdapter);

        cbcImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.cbc_snackBarMessage), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEdit.getText().toString().isEmpty()){
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(getString((R.string.cbc_searchErrorTitle)));
                    alertDialogBuilder.setMessage(getString(R.string.cbc_searchErrorMessage));
                    alertDialogBuilder.setPositiveButton(getString(R.string.cbc_searchErrorOk),new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.cbc_searchErrorClosed), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                    alertDialogBuilder.show();
                }
                else {

                }
            }
        });

    }
}
