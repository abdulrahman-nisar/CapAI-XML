package com.example.capai_xml.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.capai_xml.domain.database.CapAiDataBase;
import com.example.capai_xml.domain.model.CaptionItem;
import com.example.capai_xml.domain.model.TranscriptionItem;
import com.example.capai_xml.domain.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class CapAiDataBaseImpl extends SQLiteOpenHelper implements CapAiDataBase {

    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_IS_NEW_USER = "isNewUser";

    private static final String TABLE_CAPTION_ITEM = "CaptionItem";
    private static final String COLUMN_CAPTION_ID = "id";
    private static final String COLUMN_INSTAGRAM = "instagramCaption";
    private static final String COLUMN_FACEBOOK = "facebookCaption";
    private static final String COLUMN_TWITTER = "twitterCaption";
    private static final String COLUMN_PINTEREST = "pinterestCaption";
    private static final String COLUMN_LINKEDIN = "linkedinCaption";
    private static final String COLUMN_THREAD = "threadCaption";
    private static final String COLUMN_SNAPCHAT = "snapChatCaption";
    private static final String COLUMN_TIKTOK = "tiktokCaption";
    private static final String COLUMN_IMAGE_URI = "imageUri";

    private static final String TABLE_TRANSCRIPTION_ITEM = "TranscriptionItem";
    private static final String COLUMN_TRANSCRIPTION_ID = "id";
    private static final String COLUMN_TRANSCRIPTION_TEXT = "transcriptionText";
    private static final String COLUMN_VIDEO_URI = "videoUri";
    private static final String COLUMN_SOURCE = "source";

    public CapAiDataBaseImpl(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUser = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_IS_NEW_USER + " INTEGER)";
        sqLiteDatabase.execSQL(createTableUser);

        String createTableCaptionItem = "CREATE TABLE " + TABLE_CAPTION_ITEM + " (" +
                COLUMN_CAPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INSTAGRAM + " TEXT, " +
                COLUMN_FACEBOOK + " TEXT, " +
                COLUMN_TWITTER + " TEXT, " +
                COLUMN_PINTEREST + " TEXT, " +
                COLUMN_LINKEDIN + " TEXT, " +
                COLUMN_THREAD + " TEXT, " +
                COLUMN_SNAPCHAT + " TEXT, " +
                COLUMN_TIKTOK + " TEXT, " +
                COLUMN_IMAGE_URI + " TEXT)";
        sqLiteDatabase.execSQL(createTableCaptionItem);

        String createTableTranscriptionItem = "CREATE TABLE " + TABLE_TRANSCRIPTION_ITEM + " (" +
                COLUMN_TRANSCRIPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRANSCRIPTION_TEXT + " TEXT, " +
                COLUMN_VIDEO_URI + " TEXT, " +
                COLUMN_SOURCE + " TEXT)";
        sqLiteDatabase.execSQL(createTableTranscriptionItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CAPTION_ITEM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSCRIPTION_ITEM);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addUser(@NotNull User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.getId());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_IS_NEW_USER, user.isNewUser() ? 1 : 0);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    @Override
    public User getCurrentUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, null, null, null, null, null);
        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_USER_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int isNewUserIndex = cursor.getColumnIndex(COLUMN_IS_NEW_USER);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String email = cursor.getString(emailIndex);
            boolean isNewUser = cursor.getInt(isNewUserIndex) == 1;

            user = new User(id, name, email, isNewUser);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return user;
    }

    @Override
    public boolean deleteCurrentUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_USER, null, null);
        db.close();
        return rowsDeleted > 0;
    }

    @Override
    public void addCaptionToHistory(@NotNull CaptionItem captionItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INSTAGRAM, captionItem.getInstagramCaption());
        values.put(COLUMN_FACEBOOK, captionItem.getFacebookCaption());
        values.put(COLUMN_TWITTER, captionItem.getTwitterCaption());
        values.put(COLUMN_PINTEREST, captionItem.getPinterestCaption());
        values.put(COLUMN_LINKEDIN, captionItem.getLinkedinCaption());
        values.put(COLUMN_THREAD, captionItem.getThreadCaption());
        values.put(COLUMN_SNAPCHAT, captionItem.getSnapChatCaption());
        values.put(COLUMN_TIKTOK, captionItem.getTiktokCaption());
        values.put(COLUMN_IMAGE_URI, captionItem.getImageUri());

        db.insert(TABLE_CAPTION_ITEM, null, values);
        db.close();
    }

    @Override
    public boolean clearCaptionHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_CAPTION_ITEM, null, null);
        db.close();
        return rowsDeleted > 0;
    }

    @Override
    public boolean deleteCaptionFromHistory(@NotNull CaptionItem captionItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_CAPTION_ITEM, COLUMN_CAPTION_ID + " = ?", new String[]{String.valueOf(captionItem.getId())});
        db.close();
        return rowsDeleted > 0;
    }

    @Override
    public void addTranscriptionToHistory(@NotNull TranscriptionItem transcriptionItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSCRIPTION_TEXT, transcriptionItem.getTranscriptionText());
        values.put(COLUMN_VIDEO_URI, transcriptionItem.getVideoUri());
        values.put(COLUMN_SOURCE, transcriptionItem.getSource().name());

        db.insert(TABLE_TRANSCRIPTION_ITEM, null, values);
        db.close();
    }

    @Override
    public boolean deleteTranscriptionFromHistory(@NotNull TranscriptionItem transcriptionItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_CAPTION_ITEM, COLUMN_CAPTION_ID + " = ?", new String[]{String.valueOf(transcriptionItem.getId())});
        db.close();
        return rowsDeleted > 0;
    }

    @Override
    public @NotNull List<@NotNull CaptionItem> getAllCaptionHistory() {
        List<CaptionItem> captionList = new java.util.ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CAPTION_ITEM, null, null, null, null, null, COLUMN_CAPTION_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_CAPTION_ID);
                int instagramIndex = cursor.getColumnIndex(COLUMN_INSTAGRAM);
                int facebookIndex = cursor.getColumnIndex(COLUMN_FACEBOOK);
                int twitterIndex = cursor.getColumnIndex(COLUMN_TWITTER);
                int pinterestIndex = cursor.getColumnIndex(COLUMN_PINTEREST);
                int linkedinIndex = cursor.getColumnIndex(COLUMN_LINKEDIN);
                int threadIndex = cursor.getColumnIndex(COLUMN_THREAD);
                int snapchatIndex = cursor.getColumnIndex(COLUMN_SNAPCHAT);
                int tiktokIndex = cursor.getColumnIndex(COLUMN_TIKTOK);
                int imageUriIndex = cursor.getColumnIndex(COLUMN_IMAGE_URI);

                int id = cursor.getInt(idIndex);

                String instagram = cursor.getString(instagramIndex);
                String facebook = cursor.getString(facebookIndex);
                String twitter = cursor.getString(twitterIndex);
                String pinterest = cursor.getString(pinterestIndex);
                String linkedin = cursor.getString(linkedinIndex);
                String thread = cursor.getString(threadIndex);
                String snapchat = cursor.getString(snapchatIndex);
                String tiktok = cursor.getString(tiktokIndex);
                String imageUri = cursor.getString(imageUriIndex);

                CaptionItem item = new CaptionItem(
                        id,
                        instagram,
                        facebook,
                        twitter,
                        pinterest,
                        linkedin,
                        thread,
                        snapchat,
                        tiktok,
                        imageUri != null ? imageUri : "",
                        com.example.capai_xml.domain.model.SourceTable.CAPTION
                );
                captionList.add(item);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return captionList;
    }

    @Override
    public @NotNull List<@NotNull TranscriptionItem> getAllTranscriptionHistory() {
        return Collections.emptyList();
    }
}
