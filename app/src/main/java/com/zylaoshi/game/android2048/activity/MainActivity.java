package com.zylaoshi.game.android2048.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.zylaoshi.game.android2048.R;
import com.zylaoshi.game.android2048.view.AnimLayer;
import com.zylaoshi.game.android2048.view.GameView;

public class MainActivity extends Activity implements View.OnClickListener {
	private int score = 0;
	private TextView tvScore,tvBestScore;
	private RelativeLayout root = null;
	private TextView btnNewGame,tvBack;
	private GameView gameView;
	private AnimLayer animLayer = null;
	private int ScreenWidth;
	private int ScreenHeight;

	private static MainActivity mainActivity = null;

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	public static final String SP_KEY_BEST_SCORE = "bestScore";

	public MainActivity() {
		mainActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		root = (RelativeLayout) findViewById(R.id.container);
		root.setBackgroundColor(0xfffaf8ef);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvBestScore = (TextView) findViewById(R.id.tvBestScore);
		gameView = (GameView) findViewById(R.id.gameView);
		btnNewGame = (TextView) findViewById(R.id.btReset);
		btnNewGame.setOnClickListener(new View.OnClickListener() {@Override
		public void onClick(View v) {
			gameView.startGame();
		}});
		animLayer = (AnimLayer) findViewById(R.id.animLayer);
		ScreenWidth=ScreenHeight=getScreenHeight(this);
		tvBack= (TextView) findViewById(R.id.tv_back);
		tvBack.setOnClickListener(this);
		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

	}

	public void clearScore(){
		score = 0;
		showScore();
	}

	public void showScore(){
		tvScore.setText(score+"");
	}

	public void addScore(int s){
		score+=s;
		showScore();

		int maxScore = Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}

	public void saveBestScore(int s){
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putInt(SP_KEY_BEST_SCORE, s);
		e.commit();
	}

	public int getBestScore(){
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
	}

	public void showBestScore(int s){
		tvBestScore.setText(s+"");
	}
	
	public AnimLayer getAnimLayer() {
		return animLayer;
	}


	/**
	 * 获取屏幕高度
	 *
	 * @param context
	 */
	private int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}

	@Override
	public void onClick(View view) {
		MainActivity.this.finish();
	}
}
