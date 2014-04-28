<<<<<<< HEAD
package com.rockyniu.rockpaperscissors;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.rockyniu.rockpaperscissors.database.User;
import com.rockyniu.rockpaperscissors.database.UserDataSource;

public class MainActivity extends Activity implements View.OnClickListener {

	static final String TAG = "MainActiviy";
	static final int REQUEST_ACCOUNT_PICKER = 114;
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 911;
	UserDataSource userDataSource;
	AccountManager mAccountManager;
	// String token;
	// int numAsyncTasks;

	// Users user;
	List<String> namesList;
	// String userName;
	// String userId;
	ListView mUserNamesList;
	SignInButton mAddUserButton;
	ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userDataSource = new UserDataSource(this);

		// get account names
		mAccountManager = AccountManager.get(this);
		namesList = getAccounts(mAccountManager);

		mUserNamesList = (ListView) findViewById(R.id.namesList);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, namesList);
		mUserNamesList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		mUserNamesList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View arg1,
							int pos, long id) {

						String userName = parent.getItemAtPosition(pos)
								.toString();
						goToPlay(userName);
					}
				});

		mAddUserButton = (SignInButton) findViewById(R.id.add_account_button);
//		mAddUserButton.setSize(SignInButton.SIZE_WIDE);
		mAddUserButton.setOnClickListener(MainActivity.this);
		mAddUserButton.setContentDescription("Add User");

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ACCOUNT_PICKER && resultCode == RESULT_OK) {
			String userName = data
					.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			goToPlay(userName);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshView();
		checkGooglePlaySerViceAvailable(MainActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	private void checkGooglePlaySerViceAvailable(Activity activity) {
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(MainActivity.this);
		if (status != ConnectionResult.SUCCESS) {
			Log.v(TAG, "GoolgePlayService is not available.");
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				GooglePlayServicesUtil.getErrorDialog(status, this,
						REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
			} else {
				Toast.makeText(this, "This device is not supported.",
						Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}

	private List<String> getAccounts(AccountManager mAccountManager) {
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		List<String> namesList = new ArrayList<String>();
		for (int i = 0; i < accounts.length; i++) {
			namesList.add(accounts[i].name);
		}
		return namesList;
	}

	private void goToPlay(String userName) {
		User user = userDataSource.selectUser(userName);
		String userId = user.getId();
		String token = user.getPwd();
		Intent intent = new Intent(MainActivity.this, RoundActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("com.rockyniu.rockpaperscissors.userid", userId);
		bundle.putString("com.rockyniu.rockpaperscissors.username", userName);
		// bundle.putString("com.example.cs6300todolist.token", token);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private void refreshView() {
		namesList = getAccounts(mAccountManager);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, namesList);
		mUserNamesList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view) {
		if (view == findViewById(R.id.add_account_button)) {
			
			Intent intent = AccountPicker.newChooseAccountIntent(null, null,
					new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, false,
					null, null, null, null);
			try{
				startActivityForResult(intent, REQUEST_ACCOUNT_PICKER);
			} catch (Exception e){
				checkGooglePlaySerViceAvailable(MainActivity.this);
			}
		}
=======
/**
* Rocky, Paper, Scissors / 猜拳小游戏
* @version 0.1, 2013-08-15
* @author RockyNiu
* @since JDK2.2
*/
package com.rockyniu.rockpaperscissors;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {
	private RadioGroup aRadioGroup, bRadioGroup;
	private Button okButton;
	private ImageView choiceAImageView, choiceBImageView, resultImageView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		aRadioGroup = (RadioGroup)findViewById(R.id.aRadioGroupId);
		bRadioGroup = (RadioGroup)findViewById(R.id.bRadioGroupId);
		
		okButton =(Button)findViewById(R.id.okButtonId);
		
		choiceAImageView = (ImageView)findViewById(R.id.choiceAImageViewId);
		choiceBImageView = (ImageView)findViewById(R.id.choiceBImageViewId);
		resultImageView = (ImageView)findViewById(R.id.resultImageViewId);
		
		ButtonListener buttonListener = new ButtonListener();
		okButton.setOnClickListener(buttonListener);
		
		RadioGroupListener radioGroupListener = new RadioGroupListener();
		aRadioGroup.setOnCheckedChangeListener(radioGroupListener);
		bRadioGroup.setOnCheckedChangeListener(radioGroupListener);
		
		int randAInt = RandomCheckRadioGroup (aRadioGroup);
		int randBint = RandomCheckRadioGroup (bRadioGroup);
	}

	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.okButtonId){
				choiceAImageView.setVisibility(View.VISIBLE);
				choiceBImageView.setVisibility(View.VISIBLE);				
				resultImageView.setVisibility(View.VISIBLE);
				int win = Win (aRadioGroup, bRadioGroup);
				switch (win){
					case 1:
						resultImageView.setImageResource(R.drawable.a);
						break;
					case 0:
						resultImageView.setImageResource(R.drawable.rps);
						break;
					case -1:
						resultImageView.setImageResource(R.drawable.b);
						break;
				}
			}
		}
	}
	
	
	class RadioGroupListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
			choiceAImageView.setVisibility(View.INVISIBLE);
			choiceBImageView.setVisibility(View.INVISIBLE);				
			resultImageView.setVisibility(View.INVISIBLE);
			
			int idx = Id2Index (group);
			int imageId = Index2ImageId(idx);
			if (group.getId() == R.id.aRadioGroupId){
				choiceAImageView.setImageResource(imageId);
			}
			else if (group.getId() == R.id.bRadioGroupId){
				choiceBImageView.setImageResource(imageId);
			}
		}
	}
	
	int Index2ImageId (int idx){
		int imageId = 0;
		switch (idx){
			case 0:
				imageId = R.drawable.rock;
				break;
			case 1:
				imageId = R.drawable.paper;
				break;
			case 2:
				imageId = R.drawable.scissors;
				break;
		}
		
		return imageId;
	}
	
	void ChangeBackground (RadioGroup group, int checkedId) {
		int idx = Id2Index (group);
		group.setBackgroundColor(0xFFFF0000);
	}
	
	int Win (RadioGroup groupA, RadioGroup groupB) {
		int win = 0;
		int aIndex = Id2Index(groupA);
		int bIndex = Id2Index(groupB);
		int dif = aIndex - bIndex;
		if (dif == 0){
			win = 0;
		}
		else if ((dif == -1)||(dif == 2)){
			win = -1;
		}
		else if ((dif == -2)||(dif == 1)){
			win = 1;
		}
		return win;
	}
	
	int Id2Index (RadioGroup group){
		int radioButtonID = group.getCheckedRadioButtonId();
		View radioButton = group.findViewById(radioButtonID);
		int idx = group.indexOfChild(radioButton);
		return idx;
	}

	int RandomCheckRadioGroup (RadioGroup radioGroup){
		Random random = new Random();
		int randInt = random.nextInt(3);
		RadioButton radioButton = (RadioButton) radioGroup.getChildAt(randInt);
		radioButton.setChecked(true);
		return randInt;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
>>>>>>> c48b6bddab32913974cac2472e7d393ce9af2daf
	}

}
