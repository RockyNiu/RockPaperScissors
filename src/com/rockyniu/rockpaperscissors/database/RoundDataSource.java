package com.rockyniu.rockpaperscissors.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RoundDataSource {

	// Database fields
	private SQLiteDatabase database;
	private RpsSQLiteHelper dbHelper;
	private String[] allColumns = { 
			RpsSQLiteHelper.COLUMN_ID,
			RpsSQLiteHelper.COLUMN_RESLUT,
			RpsSQLiteHelper.COLUMN_USERID, 
			RpsSQLiteHelper.COLUMN_SELFCHOICE,
			RpsSQLiteHelper.COLUMN_COMPETITORID, 
			RpsSQLiteHelper.COLUMN_COMPETITORNAME,
			RpsSQLiteHelper.COLUMN_COMPETITORCHOICE,
			RpsSQLiteHelper.COLUMN_SELFPLAYTIME,
			RpsSQLiteHelper.COLUMN_COMPETITORPLAYTIME,
			RpsSQLiteHelper.COLUMN_MODIFIEDTIME,
			RpsSQLiteHelper.COLUMN_DELETED,
			};
	private String[] onlyColumnId = { RpsSQLiteHelper.COLUMN_ID, };

	public RoundDataSource(Context context) {
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

	private boolean roundIdExsit(String id) {
		openReadableDatabase();
		Cursor cursor = database.query(RpsSQLiteHelper.TABLE_RESULTS,
				onlyColumnId, RpsSQLiteHelper.COLUMN_ID + " = ? ",
				new String[] { id }, null, null, null);
		boolean exsit = cursor.getCount() > 0 ? true : false;
		cursor.close();
		closeDatabase();
		return exsit;
	}

	public Round getRoundByRoundId(String id) {

		openWritableDatabase();
		Cursor cursor = database.query(RpsSQLiteHelper.TABLE_RESULTS,
				allColumns, RpsSQLiteHelper.COLUMN_ID + " = ? ",
				new String[] { id }, null, null, null);

		cursor.moveToFirst();
		if (cursor.isAfterLast())
			return null;
		Round newItem = cursorToRound(cursor);

		closeDatabase();
		return newItem;
	}

	// remember to create an id for item:
	// item.setId(UUID.randomUUID().toString());
	// remember to set localOnly the value 0 for item came from remote
	public boolean insertRoundWithId(Round round) {
		if (round == null || round.getId() == null || roundIdExsit(round.getId())) {
			return false;
		}
		ContentValues values = new ContentValues();
		values.put(RpsSQLiteHelper.COLUMN_ID, round.getId());
		values.put(RpsSQLiteHelper.COLUMN_RESLUT, round.getResult());
		values.put(RpsSQLiteHelper.COLUMN_USERID, round.getUserId());
		values.put(RpsSQLiteHelper.COLUMN_SELFCHOICE, round.getSelfChoice());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORID, round.getCompetitorId());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORNAME, round.getCompetitorName());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORCHOICE, round.getCompetitorChoice());
		values.put(RpsSQLiteHelper.COLUMN_SELFPLAYTIME, round.getSelfPlayTime());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORPLAYTIME, round.getCompetitorPlayTime());
		values.put(RpsSQLiteHelper.COLUMN_MODIFIEDTIME, round.getModifiedTime());
		values.put(RpsSQLiteHelper.COLUMN_DELETED, round.isDeleted());
		
		openWritableDatabase();
		database.insert(RpsSQLiteHelper.TABLE_RESULTS, null, values);
		closeDatabase();
		return true;
	}

	public boolean deleteItem(Round item) {
		String id = item.getId();
		openWritableDatabase();
		int rows = database.delete(RpsSQLiteHelper.TABLE_RESULTS,
				RpsSQLiteHelper.COLUMN_ID + " = ?", new String[] { id });
		closeDatabase();
		return rows > 0 ? true : false;
	}

	// remember to set modifiedTime before use this method
	public boolean labelItemDeletedWithModifiedTime(Round item) {
		String id = item.getId();

		openWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(RpsSQLiteHelper.COLUMN_DELETED, 1);
		values.put(RpsSQLiteHelper.COLUMN_MODIFIEDTIME, item.getModifiedTime());

		int rows = database.update(RpsSQLiteHelper.TABLE_RESULTS, values,
				RpsSQLiteHelper.COLUMN_ID + " = ?", new String[] { id });

		closeDatabase();
		return rows > 0 ? true : false;
	}

	public int changeItemId(String oldId, String newId) {
		int rows = 0;
		openWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(RpsSQLiteHelper.COLUMN_ID, newId);
		rows = database.update(RpsSQLiteHelper.TABLE_RESULTS, values,
				RpsSQLiteHelper.COLUMN_ID + " = ?", new String[] { oldId });
		closeDatabase();
		return rows;
	}

	public int updateItem(Round round) {
		String id = round.getId();
		int rows = 0;

		ContentValues values = new ContentValues();
		values.put(RpsSQLiteHelper.COLUMN_RESLUT, round.getResult());
		values.put(RpsSQLiteHelper.COLUMN_USERID, round.getUserId());
		values.put(RpsSQLiteHelper.COLUMN_SELFCHOICE, round.getSelfChoice());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORID, round.getCompetitorId());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORNAME, round.getCompetitorName());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORCHOICE, round.getCompetitorChoice());
		values.put(RpsSQLiteHelper.COLUMN_SELFPLAYTIME, round.getSelfPlayTime());
		values.put(RpsSQLiteHelper.COLUMN_COMPETITORPLAYTIME, round.getCompetitorPlayTime());
		values.put(RpsSQLiteHelper.COLUMN_MODIFIEDTIME, round.getModifiedTime());
		values.put(RpsSQLiteHelper.COLUMN_DELETED, round.isDeleted());
		
		openWritableDatabase();
		rows = database.update(RpsSQLiteHelper.TABLE_RESULTS, values,
				RpsSQLiteHelper.COLUMN_ID + " = ?", new String[] { id });
		closeDatabase();
		return rows;

	}

	private Round cursorToRound(Cursor cursor) {
		Round round = new Round();
		round.setId(cursor.getString(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_ID)));
		round.setUserId(cursor.getString(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_USERID)));
		round.setSelfChoice(cursor.getInt(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_SELFCHOICE)));
		round.setCompetitorId(cursor.getString(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_COMPETITORID)));
		round.setCompetitorName(cursor.getString(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_COMPETITORNAME)));
		round.setCompetitorChoice(cursor.getInt(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_COMPETITORCHOICE)));
		round.setSelfPlayTime(cursor.getLong(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_SELFPLAYTIME)));
		round.setCompetitorPlayTime(cursor.getLong(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_COMPETITORPLAYTIME)));
		round.setModifiedTime(cursor.getLong(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_MODIFIEDTIME)));
		round.setDeleted(cursor.getInt(cursor
				.getColumnIndex(RpsSQLiteHelper.COLUMN_DELETED)) == 1 ? true
				: false);
		return round;
	}
}
