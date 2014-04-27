package com.rockyniu.rockpaperscissors.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class RpsSQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_USERS = "users";
	public static final String TABLE_RESULTS = "results";

	public static final String COLUMN_ID = "uuid";

	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "pwd";

	public static final String COLUMN_RESLUT = "result";
	public static final String COLUMN_USERID = "userid";
	public static final String COLUMN_SELFCHOICE = "selfChoice";
	public static final String COLUMN_COMPETITORID = "competitorId";
	public static final String COLUMN_COMPETITORCHOICE = "competitorChoice";
	public static final String COLUMN_SELFPLAYTIME = "selfPlayTime";
	public static final String COLUMN_COMPETITORPLAYTIME = "competitorPlayTime";
	public static final String COLUMN_MODIFIEDTIME = "modifiedTime"; // last modified time
	public static final String COLUMN_DELETED = "deleted";

	public static final String DATABASE_NAME = "rps_results.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String USER_DATABASE_CREATE = "create table "
			+ TABLE_USERS + "(" 
			+ COLUMN_ID + " text primary key, " 
			+ COLUMN_USERNAME + " text not null, " 
			+ COLUMN_PASSWORD + " text default ''" 
			+ ");";

	private static final String TODOLIST_DATABASE_CREATE = "create table "
			+ TABLE_RESULTS + "(" 
			+ COLUMN_ID + " text primary key, " 
			+ COLUMN_RESLUT + " integer not null default 0, "
			+ COLUMN_USERID + " text not null, " 
			+ COLUMN_SELFCHOICE + " integer not null default 0, " 
			+ COLUMN_COMPETITORID + " text not null, "
			+ COLUMN_COMPETITORCHOICE + " integer not null default 0, " 
			+ COLUMN_SELFPLAYTIME + " integer not null default 0, " 
			+ COLUMN_COMPETITORPLAYTIME + " integer not null default 0, " 
			+ COLUMN_MODIFIEDTIME + " integer not null default 0, " 
			+ COLUMN_DELETED + " integer not null default 0, " 
			+ " FOREIGN KEY (" + COLUMN_USERID + ") REFERENCES " 
			+ TABLE_USERS + "(" + COLUMN_ID + ")" + " ON DELETE CASCADE ON UPDATE CASCADE"			
			+ ");";

	public RpsSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public RpsSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public RpsSQLiteHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public RpsSQLiteHelper(Context context, String name) {
		this(context, name, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		if (!database.isReadOnly()) {
			// Enable foreign key constraints
			database.execSQL("PRAGMA foreign_keys=ON;");
		}
		database.execSQL(USER_DATABASE_CREATE);
		database.execSQL(TODOLIST_DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(RpsSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
		onCreate(db);
	}

}
