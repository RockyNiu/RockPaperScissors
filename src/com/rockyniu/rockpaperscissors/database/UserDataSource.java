package com.rockyniu.rockpaperscissors.database;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {

	// Database fields
	private SQLiteDatabase database;
	private RpsSQLiteHelper dbHelper;
	private String[] allColumns = { 
			RpsSQLiteHelper.COLUMN_ID,
			RpsSQLiteHelper.COLUMN_USERNAME, 
			RpsSQLiteHelper.COLUMN_PASSWORD,
			};

	public UserDataSource(Context context) {
		dbHelper = new RpsSQLiteHelper(context);
	}

	private void openWritableDatabase() throws SQLException {
		database = dbHelper.getWritableDatabase();
		database.execSQL("PRAGMA foreign_keys=ON;");
	}

	private void openReadableDatabase() throws SQLException {
		database = dbHelper.getReadableDatabase();
	}
	
	private void closeDatabase() {
		database.close();
	}
	
	public User createUsers(String name, String pwd) {
		String insertId = UUID.randomUUID().toString();
		return createUsers(name, pwd, insertId);
	}

	public User createUsers(String name, String pwd, String insertId) {
		String selection = RpsSQLiteHelper.COLUMN_ID + " =  ? ";
		String[] selectionArgs = new String[]{insertId};
		
		ContentValues values = new ContentValues();
		values.put(RpsSQLiteHelper.COLUMN_USERNAME, name);
		values.put(RpsSQLiteHelper.COLUMN_PASSWORD, pwd);
		values.put(RpsSQLiteHelper.COLUMN_ID, insertId);

		openWritableDatabase();
		database.insert(RpsSQLiteHelper.TABLE_USERS, null, values);

		Cursor cursor = database.query(RpsSQLiteHelper.TABLE_USERS,
				allColumns, selection,selectionArgs, null, null, null);
		cursor.moveToFirst();
		User newUser = cursorToUsers(cursor);
		cursor.close();
		closeDatabase();
		return newUser;
	}
	
	// if user doesn't exist, create a new one
	public User selectUser(String name) {
		String selection = RpsSQLiteHelper.COLUMN_USERNAME + " =  ? ";
		String[] selectionArgs = new String[] { name };

		openReadableDatabase();
		Cursor cursor = database.query(RpsSQLiteHelper.TABLE_USERS,
				allColumns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		if (cursor.isAfterLast()) {
			User newUser = createUsers(name, "");
			cursor.close();
			closeDatabase();
			return newUser;
		}
		User newUser = cursorToUsers(cursor);
		cursor.close();
		closeDatabase();
		return newUser;
	}
	
	
	
	// if user doesn't exist, create a new one
	public User selectUser(String name, String token) {
		String selection = RpsSQLiteHelper.COLUMN_USERNAME + " =  ? ";
		String[] selectionArgs = new String[]{name};
		
		openReadableDatabase();
		Cursor cursor = database.query(RpsSQLiteHelper.TABLE_USERS,
				allColumns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		if (cursor.isAfterLast()){
			if (token==null){
				token = "";
			}
			User newUser = createUsers(name, token);
			cursor.close();
			closeDatabase();
			return newUser;
		}
		User newUser = cursorToUsers(cursor);
		cursor.close();
		closeDatabase();
		return newUser;
	}
	
	public User getUserById(String id) {
		String selection = RpsSQLiteHelper.COLUMN_ID + " =  ? ";
		String[] selectionArgs = new String[]{id};
		
		openReadableDatabase();
		Cursor cursor = database.query(RpsSQLiteHelper.TABLE_USERS,
				allColumns, selection, selectionArgs,
				null, null, null);
		cursor.moveToFirst();
		if (cursor.isAfterLast()){
			cursor.close();
			closeDatabase();
			return null;
		}
			
		User newUser = cursorToUsers(cursor);
		cursor.close();
		closeDatabase();
		return newUser;
	}

	public int updateUsers(User user) {
		String id = user.getId();
		String selection = RpsSQLiteHelper.COLUMN_ID + " =  ? ";
		String[] selectionArgs = new String[]{id};
		
		ContentValues values = new ContentValues();
		values.put(RpsSQLiteHelper.COLUMN_USERNAME, user.getName());
		values.put(RpsSQLiteHelper.COLUMN_PASSWORD, user.getPwd());

		openWritableDatabase();
		int rows = database.update(RpsSQLiteHelper.TABLE_USERS, values,
				selection, selectionArgs);
		closeDatabase();
		return rows;
	}
	  
	public void deleteUser(User user) {
		String id = user.getId();
		String selection = RpsSQLiteHelper.COLUMN_ID + " =  ? ";
		String[] selectionArgs = new String[]{id};
		openWritableDatabase();
		database.delete(RpsSQLiteHelper.TABLE_USERS,
				selection, selectionArgs);
		closeDatabase();
		System.out.println("User deleted with id: " + id);
	}

	public List<User> getAllUsers() {
		List<User> allusers = new ArrayList<User>();
		openReadableDatabase();
		Cursor cursor = database.query(RpsSQLiteHelper.TABLE_USERS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToUsers(cursor);
			allusers.add(user);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		closeDatabase();
		return allusers;
	}

	private User cursorToUsers(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getString(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_ID)));
		user.setName(cursor.getString(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_USERNAME)));
		user.setPwd(cursor.getString(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_PASSWORD)));
		return user;
	}
}
