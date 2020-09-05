package com.thesis.project.repository.firebase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.thesis.project.models.arnote.ArNote;
import com.thesis.project.repository.firebase.interfaces.ShortCodeListener;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;

/** Helper class for Firebase storage of cloud anchor IDs. */
public class StorageManager {

    public ArNote resolvedArNote;
    public static String storedCloudAnchorId;

    /** Listener for a new Cloud Anchor ID from the Firebase Database. */
    public interface CloudAnchorIdListener
    {
        void onCloudAnchorIdAvailable(String cloudAnchorId);
    }

    private static final String TAG = StorageManager.class.getName();
    private static final String KEY_ROOT_DIR = "shared_anchor_codelab_root";
    private static final String TEXT_FROM_OTHER_PERSON = "text_string";
    private static final String KEY_NEXT_SHORT_CODE = "next_short_code";
    private static final String KEY_PREFIX = "anchor;";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TEXT = "text";
    private static final String KEY_DATE = "date";
    private static final String KEY_CLOUDANCHOR_ID = "cloudanchorid";
    private static final String KEY_SHORTCODE = "shortcode";
    private static final int INITIAL_SHORT_CODE = 142;
    private final DatabaseReference rootRef;

    public StorageManager(Context context)
    {
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(context);
        assert firebaseApp != null;
        rootRef = FirebaseDatabase.getInstance(firebaseApp).getReference().child(KEY_ROOT_DIR);
        DatabaseReference.goOnline();
    }

    public String getStoredCloudAnchorId()
    {
        return storedCloudAnchorId;
    }
    public ArNote getResolvedArNote()
    {
        return resolvedArNote;
    }

    /** Gets a new short code that can be used to store the anchor ID. */
    public void nextShortCode(ShortCodeListener listener)
    {
        // Run a transaction on the node containing the next short code available. This increments the
        // value in the database and retrieves it in one atomic all-or-nothing operation.
         rootRef.child(KEY_NEXT_SHORT_CODE).runTransaction(getTransactionHandler(listener));
    }

    /** Stores the cloud anchor ID in the configured Firebase Database. */
    public void storeUsingShortCode(int shortCode, String cloudAnchorId, String text, String type,String date)
    {
        storedCloudAnchorId = cloudAnchorId;
        rootRef.child(KEY_PREFIX + shortCode).child(KEY_CLOUDANCHOR_ID).setValue(cloudAnchorId);
        rootRef.child(KEY_PREFIX + shortCode).child(KEY_TYPE).setValue(type);
        rootRef.child(KEY_PREFIX + shortCode).child(KEY_TEXT).setValue(text);
        rootRef.child(KEY_PREFIX + shortCode).child(KEY_DATE).setValue(date);
        rootRef.child(KEY_PREFIX + shortCode).child(KEY_SHORTCODE).setValue(String.valueOf(shortCode));
    }


    /**
     * Retrieves the cloud anchor ID using a short code. Returns an empty string if a cloud anchor ID
     * was not stored for this short code.
     */
    public void getCloudAnchorID(int shortCode, CloudAnchorIdListener listener)
    {
         rootRef.child(KEY_PREFIX + shortCode).addListenerForSingleValueEvent(getValueEventListener(listener,shortCode));
    }

    private ValueEventListener getValueEventListener(CloudAnchorIdListener listener,Integer shortCode)
    {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot)
            {
                String cloudanchorid = dataSnapshot.child("cloudanchorid").getValue(String.class);
                String type = dataSnapshot.child("type").getValue(String.class);
                String text = dataSnapshot.child("text").getValue(String.class);
                String date = dataSnapshot.child("date").getValue(String.class);
                resolvedArNote = new ArNote(0,type,text,date,shortCode.toString(),cloudanchorid);
                resolvedArNote.setCloudAnchorId(cloudanchorid);
                resolvedArNote.setType(type);
                resolvedArNote.setText(text);
                resolvedArNote.setDate(date);
                listener.onCloudAnchorIdAvailable(cloudanchorid);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) { listener.onCloudAnchorIdAvailable(null); }
        };
    }

    private Transaction.Handler getTransactionHandler(ShortCodeListener listener)
    {
        return new Transaction.Handler()
        {
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NotNull MutableData currentData)
            {
                Integer shortCode = currentData.getValue(Integer.class);

                if (shortCode == null) { shortCode = INITIAL_SHORT_CODE - 1; }

                currentData.setValue(shortCode + 1);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData)
            {
                if (!committed) { listener.onShortCodeAvailable(null); }
                else { listener.onShortCodeAvailable(currentData.getValue(Integer.class)); }
            }
        };
    }
}