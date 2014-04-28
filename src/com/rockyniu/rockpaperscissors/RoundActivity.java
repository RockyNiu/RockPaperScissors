/**
* Rocky, Paper, Scissors / 猜拳小游戏
* @version 0.2, 2014-04-27
* @version 0.1, 2013-08-15
* @author Lei Niu
* @since Android 4.2.2
*/
package com.rockyniu.rockpaperscissors;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RoundActivity extends Activity {
	
	// user information
	private String userId;
	private String userName;
	
	// play type
	private int playType;
	private int competitorIcon;
	private final int PLAY_WITH_HOME = 0;
	private final int PLAY_WITH_COMPUTER = 1; 
	
	// view information
	private ImageView aImageView;
	private ImageView bImageView;
	private RadioGroup aRadioGroup;
	private RadioGroup bRadioGroup;
	private Button playButton;
	private ImageView aResultImageView;
	private ImageView bResultImageView;
	private ImageView choiceAImageView;
	private ImageView choiceBImageView;
	private ImageView resultImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		userId = bundle.getString("com.rockyniu.rockpaperscissors.userid");
		userName = bundle.getString("com.rockyniu.rockpaperscissors.username");
		this.setTitle(userName);
		
		aImageView = (ImageView)findViewById(R.id.aImageViewId);
		bImageView = (ImageView)findViewById(R.id.bImageViewId);
		aRadioGroup = (RadioGroup)findViewById(R.id.aRadioGroupId);
		bRadioGroup = (RadioGroup)findViewById(R.id.bRadioGroupId);
		
		playButton =(Button)findViewById(R.id.okButtonId);
		
		aImageView = (ImageView)findViewById(R.id.aImageViewId);
		bImageView = (ImageView)findViewById(R.id.bImageViewId);
		aResultImageView = (ImageView)findViewById(R.id.aImageViewId_result);
		bResultImageView = (ImageView)findViewById(R.id.bImageViewId_result);
		choiceAImageView = (ImageView)findViewById(R.id.choiceAImageViewId);
		choiceBImageView = (ImageView)findViewById(R.id.choiceBImageViewId);
		resultImageView = (ImageView)findViewById(R.id.resultImageViewId);
		
		ButtonListener buttonListener = new ButtonListener();
		playButton.setOnClickListener(buttonListener);
		
		RadioGroupListener radioGroupListener = new RadioGroupListener();
		aRadioGroup.setOnCheckedChangeListener(radioGroupListener);
		bRadioGroup.setOnCheckedChangeListener(radioGroupListener);
		
		playType = PLAY_WITH_HOME;
		int randAInt = RandomCheckRadioGroup (aRadioGroup);
		int randBint = RandomCheckRadioGroup (bRadioGroup);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setPlayerIcon();
	}
	
	private void setPlayerIcon(){
		switch (playType){
		case PLAY_WITH_HOME:
			competitorIcon = R.drawable.a_default;
			break;
		case PLAY_WITH_COMPUTER:
			competitorIcon = R.drawable.computer;
			break;
		default:
			competitorIcon = R.drawable.a_default;
		}
		
		aImageView.setImageResource(competitorIcon);
		aResultImageView.setImageResource(competitorIcon);
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
						resultImageView.setImageResource(R.drawable.a_default);
						break;
					case 0:
						resultImageView.setImageResource(R.drawable.rps);
						break;
					case -1:
						resultImageView.setImageResource(R.drawable.b_default);
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
		getMenuInflater().inflate(R.menu.menu_round, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_playwithcomputer:
			playType = PLAY_WITH_COMPUTER;
			setPlayerIcon();
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
