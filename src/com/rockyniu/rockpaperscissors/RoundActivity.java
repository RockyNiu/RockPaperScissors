/**
 * Rocky, Paper, Scissors / 猜拳小游戏
 * @version 0.2, 2014-04-27
 * @version 0.1, 2013-08-15
 * @author Lei Niu
 * @since Android 4.2.2
 */
package com.rockyniu.rockpaperscissors;

import java.util.Random;

import com.rockyniu.rockpaperscissors.database.RoundResult;
import com.rockyniu.rockpaperscissors.database.RoundResult.Choice;

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

public class RoundActivity extends Activity implements View.OnClickListener {

	private final int CHOICE_ERROR = -1;

	// user information
	private String userId;
	private String userName;

	// play type
	private int playType;
	private int competitorIconId;
	private final int PLAY_WITH_HOME = 0;
	private final int PLAY_WITH_COMPUTER = 1;
	private final int PLAY_WITH_REMOTE = 2;

	// view information
	private Menu menu;
	private ImageView aImageView;
	private ImageView bImageView;
	private RadioGroup aRadioGroup;
	private RadioGroup bRadioGroup;
	private Button a1RadioButton;
	private Button a2RadioButton;
	private Button a3RadioButton;
	private Button b1RadioButton;
	private Button b2RadioButton;
	private Button b3RadioButton;
	private Button playButton;
	private ImageView aResultImageView;
	private ImageView bResultImageView;
	private ImageView choiceAImageView;
	private ImageView choiceBImageView;
	private ImageView resultImageView;

	// players' choices
	private Choice aChoice;
	private Choice bChoice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		userId = bundle.getString("com.rockyniu.rockpaperscissors.userid");
		userName = bundle.getString("com.rockyniu.rockpaperscissors.username");
		this.setTitle(userName);

		aImageView = (ImageView) findViewById(R.id.aImageViewId);
		bImageView = (ImageView) findViewById(R.id.bImageViewId);
		aRadioGroup = (RadioGroup) findViewById(R.id.aRadioGroupId);
		bRadioGroup = (RadioGroup) findViewById(R.id.bRadioGroupId);
		a1RadioButton = (Button) findViewById(R.id.a1RadioButtonId);
		a2RadioButton = (Button) findViewById(R.id.a2RadioButtonId);
		a3RadioButton = (Button) findViewById(R.id.a3RadioButtonId);
		b1RadioButton = (Button) findViewById(R.id.b1RadioButtonId);
		b2RadioButton = (Button) findViewById(R.id.b2RadioButtonId);
		b3RadioButton = (Button) findViewById(R.id.b3RadioButtonId);

		playButton = (Button) findViewById(R.id.okButtonId);

		aImageView = (ImageView) findViewById(R.id.aImageViewId);
		bImageView = (ImageView) findViewById(R.id.bImageViewId);
		aResultImageView = (ImageView) findViewById(R.id.aImageViewId_result);
		bResultImageView = (ImageView) findViewById(R.id.bImageViewId_result);
		choiceAImageView = (ImageView) findViewById(R.id.choiceAImageViewId);
		choiceBImageView = (ImageView) findViewById(R.id.choiceBImageViewId);
		resultImageView = (ImageView) findViewById(R.id.resultImageViewId);

//		playButton.setOnClickListener(this);
//		aRadioGroup.setOnClickListener(this);
//		bRadioGroup.setOnClickListener(this);

//		RadioGroupChangeListener radioGroupChangeListener = new RadioGroupChangeListener();
//		aRadioGroup.setOnCheckedChangeListener(radioGroupChangeListener);
//		bRadioGroup.setOnCheckedChangeListener(radioGroupChangeListener);

		playType = PLAY_WITH_COMPUTER;
		aChoice = Choice.ROCK;
		bChoice = Choice.ROCK;
	}

	@Override
	protected void onResume() {
		super.onResume();
		setPlayer();
	}

	private void setPlayer() {
		switch (playType) {
		case PLAY_WITH_HOME:
			competitorIconId = R.drawable.a_default;
			enableDisableRaidoGroup(aRadioGroup, true);
			break;
		case PLAY_WITH_COMPUTER:
			competitorIconId = R.drawable.computer;
			enableDisableRaidoGroup(aRadioGroup, false);
			randomCheckButton(aRadioGroup);
			break;
		case PLAY_WITH_REMOTE:
			competitorIconId = R.drawable.remote_player;
			enableDisableRaidoGroup(aRadioGroup, false);
			randomCheckButton(aRadioGroup);
			break;
		default:
			competitorIconId = R.drawable.a_default;
			enableDisableRaidoGroup(aRadioGroup, true);
		}
		aImageView.setImageResource(competitorIconId);
		aResultImageView.setImageResource(competitorIconId);
	}

//	class RadioGroupChangeListener implements OnCheckedChangeListener {
//
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//			resultImageView.setVisibility(View.INVISIBLE);
//
//			if (group.getId() == R.id.aRadioGroupId) {
//				choiceAImageView.setVisibility(View.INVISIBLE);
//				aChoice = getChoice(group);
//				int imageId = choice2ImageId(aChoice);
//				choiceAImageView.setImageResource(imageId);
//			} else if (group.getId() == R.id.bRadioGroupId) {
//				choiceBImageView.setVisibility(View.INVISIBLE);
//				bChoice = getChoice(group);
//				int imageId = choice2ImageId(bChoice);
//				choiceBImageView.setImageResource(imageId);
//				if (playType == PLAY_WITH_COMPUTER) {
//					randomCheckRadioGroup(aRadioGroup);
//				}
//			}
//		}
//	}

	private Choice getChoice(RadioGroup group) {
		int radioButtonID = group.getCheckedRadioButtonId();
		View radioButton = group.findViewById(radioButtonID);
		int index = group.indexOfChild(radioButton);
		return Choice.setChoice(index);
	}

	private int choice2ImageId(Choice choice) {
		int imageId = CHOICE_ERROR;
		switch (choice) {
		case ROCK:
			imageId = R.drawable.rock;
			break;
		case PAPER:
			imageId = R.drawable.paper;
			break;
		case SCISSORS:
			imageId = R.drawable.scissors;
			break;
		default:
			imageId = CHOICE_ERROR;
			break;
		}

		return imageId;
	}

	private void enableDisableRaidoGroup(RadioGroup radioGroup, boolean enabled) {
		radioGroup.setEnabled(enabled);
		int childCount = radioGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = radioGroup.getChildAt(i);
			view.setEnabled(enabled);
		}
	}

	private Choice randomCheckButton(RadioGroup radioGroup) {
		Random random = new Random();
		int randInt = random.nextInt(3);
		RadioButton radioButton = (RadioButton) radioGroup.getChildAt(randInt);
		radioButton.setChecked(true);
		return getChoice(radioGroup);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_round, menu);
		menu.findItem(R.id.choose_player).setIcon(competitorIconId);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_playwithcomputer:
			playType = PLAY_WITH_COMPUTER;
			break;
		case R.id.menu_playwithhome:
			playType = PLAY_WITH_HOME;
			break;
		case R.id.menu_palywithremote:
			playType = PLAY_WITH_REMOTE;
			break;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		setPlayer();
		menu.findItem(R.id.choose_player).setIcon(competitorIconId);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.okButtonId) {
			choiceAImageView.setVisibility(View.VISIBLE);
			choiceBImageView.setVisibility(View.VISIBLE);
			resultImageView.setVisibility(View.VISIBLE);
			int win = RoundResult.getResult(aChoice, bChoice);
			switch (win) {
			case 1:
				resultImageView.setImageResource(competitorIconId);
				break;
			case 0:
				resultImageView.setImageResource(R.drawable.rps);
				break;
			case -1:
				resultImageView.setImageResource(R.drawable.b_default);
				break;
			}
		} else if (id == R.id.a1RadioButtonId || id == R.id.a2RadioButtonId
				|| id == R.id.a3RadioButtonId) {

			choiceAImageView.setVisibility(View.INVISIBLE);
			choiceBImageView.setVisibility(View.INVISIBLE);
			resultImageView.setVisibility(View.INVISIBLE);

			switch (view.getId()) {
			case R.id.a1RadioButtonId:
				aChoice = Choice.ROCK;
				choiceAImageView.setImageResource(choice2ImageId(aChoice));
				break;
			case R.id.a2RadioButtonId:
				aChoice = Choice.PAPER;
				choiceAImageView.setImageResource(choice2ImageId(aChoice));
				break;
			case R.id.a3RadioButtonId:
				aChoice = Choice.SCISSORS;
				choiceAImageView.setImageResource(choice2ImageId(aChoice));
				break;
			}
		} else if (id == R.id.b1RadioButtonId || id == R.id.b2RadioButtonId
				|| id == R.id.b3RadioButtonId) {
			choiceAImageView.setVisibility(View.INVISIBLE);
			choiceBImageView.setVisibility(View.INVISIBLE);
			resultImageView.setVisibility(View.INVISIBLE);
			
			switch (id) {
			case R.id.b1RadioButtonId:
				bChoice = Choice.ROCK;
				choiceBImageView.setImageResource(choice2ImageId(bChoice));
				break;
			case R.id.b2RadioButtonId:
				bChoice = Choice.PAPER;
				choiceBImageView.setImageResource(choice2ImageId(bChoice));
				break;
			case R.id.b3RadioButtonId:
				bChoice = Choice.SCISSORS;
				choiceBImageView.setImageResource(choice2ImageId(bChoice));
				break;
			}
			if (playType == PLAY_WITH_COMPUTER) {
				aChoice = randomCheckButton(aRadioGroup);
				choiceAImageView.setImageResource(choice2ImageId(aChoice));
			}
		}
	}
}
