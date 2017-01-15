package com.couchbase.todolite.util;


import android.util.Log;

import com.couchbase.todolite.TaskActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raj on 1/15/2017.
 */
public class FirebaseDeak {

    public void FirebaseDeakes(String category, final String aisle, final String title, final Map<String,String> hm){
        Log.v("uuuu", "uuuuuuuuuuuuu");
final String newCat = " ";
        //int aislenum = Integer.parseInt(aisle.substring(6));
        final String aisle1 = aisle; //"Aaisle:" + Integer.toString(aislenum %10);
       //Log.v("uuuu", "uuuuuuuuuuuuu");

      FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Stores");

         final String val = "";
        Log.v("uuuu", "uuuuuuuuuuuuu");

        myRef.child("Safeway").child(aisle1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(newCat)) {
                    // run some code
                } else {
                    Log.v(aisle, "uuurrrrrrrr");
                    myRef.child("Safeway").child(aisle1).child("item").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String val = "";
                            if(snapshot.getValue()!=null){
                            val = snapshot.getValue().toString();}
                            System.out.println(val + "aaaaaaaaaaaaaaaaaa");
                            String[] positions = val.split(",");
                            int r = Integer.parseInt(positions[0]);
                            int c = Integer.parseInt(positions[1]);
                            String coor = r+","+c;
                            Log.v("hhhhhh", "rerere");
                            if(hm.get(title) == null)
                            {
                                hm.put(title, coor);
                            }

                            return;

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });}}
