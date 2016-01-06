package org.haxe.extension.supersonic;


import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.supersonic.mediationsdk.integration.IntegrationHelper;
import com.supersonic.mediationsdk.logger.SupersonicError;
import com.supersonic.mediationsdk.model.Placement;
import com.supersonic.mediationsdk.sdk.RewardedVideoListener;
import com.supersonic.mediationsdk.sdk.Supersonic;
import com.supersonic.mediationsdk.sdk.SupersonicFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


/* 
	You can use the Android Extension class in order to hook
	into the Android activity lifecycle. This is not required
	for standard Java code, this is designed for when you need
	deeper integration.
	
	You can access additional references from the Extension class,
	depending on your needs:
	
	- Extension.assetManager (android.content.res.AssetManager)
	- Extension.callbackHandler (android.os.Handler)
	- Extension.mainActivity (android.app.Activity)
	- Extension.mainContext (android.content.Context)
	- Extension.mainView (android.view.View)
	
	You can also make references to static or instance methods
	and properties on Java classes. These classes can be included 
	as single files using <java path="to/File.java" /> within your
	project, or use the full Android Library Project format (such
	as this example) in order to include your own AndroidManifest
	data, additional dependencies, etc.
	
	These are also optional, though this example shows a static
	function for performing a single task, like returning a value
	back to Haxe from Java.
*/
public class ExtensionSupersonic extends Extension implements RewardedVideoListener {
	
	static private Supersonic mMediationAgent;	
	
	static private String mUserId;
	static private String mAppKey;
	
	static private HaxeObject mHaxeObject;
	
	/**
	 * Called when an activity you launched exits, giving you the requestCode 
	 * you started it with, the resultCode it returned, and any additional data 
	 * from it.
	 */
	public boolean onActivityResult (int requestCode, int resultCode, Intent data) {	
		return true;
	}
	
	public ExtensionSupersonic(){
		super();
	}
	
	static void trace(String message){
		Log.i("trace", "extension-Supersonic : " + message);
	}
	
	static public void init(String appKey, HaxeObject obj){
		mAppKey = appKey;
		mHaxeObject = obj;
		
		trace("init");
		
		Info adInfo;
		String id = "Unique_User_Id";
		/*try {
			adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mainContext);
			id = adInfo.getId();
		} catch (Exception e) {
			trace("Couldn't generate uid : " + e.getMessage());
		} */
		mUserId = id;
		trace(mUserId);

		try {
			mMediationAgent.initRewardedVideo(mainActivity, mAppKey, mUserId);
		}catch (Exception e){
			trace(e.getMessage());
		}
	}
	
	static public  void showRewardedVideo(String placementId){
		trace("showRewardedVideo");
		if(placementId == "" || placementId == null)
			mMediationAgent.showRewardedVideo();
		else
			mMediationAgent.showRewardedVideo(placementId);
	}
	
	/**
	 * Called when the activity is starting.
	 */
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		trace("onCreate");
		
		mMediationAgent = SupersonicFactory.getInstance();
		mMediationAgent.setRewardedVideoListener(this);
	}
	
	/**
	 * Perform any final cleanup before an activity is destroyed.
	 */
	public void onDestroy () {
	}
	
	/**
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed.
	 */
	public void onPause () {
		if(mMediationAgent != null)
			mMediationAgent.onPause(mainActivity);
	}
	
	/**
	 * Called after {@link #onStop} when the current activity is being 
	 * re-displayed to the user (the user has navigated back to it).
	 */
	public void onRestart () {
	}
	
	/**
	 * Called after {@link #onRestart}, or {@link #onPause}, for your activity 
	 * to start interacting with the user.
	 */
	public void onResume () {
		if(mMediationAgent != null)
			mMediationAgent.onResume(mainActivity);
	}
	
	/**
	 * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when  
	 * the activity had been stopped, but is now again being displayed to the 
	 * user.
	 */
	public void onStart () {
	}
	
	
	/**
	 * Called when the activity is no longer visible to the user, because 
	 * another activity has been resumed and is covering this one. 
	 */
	public void onStop () {
	}

	@Override
	public void onRewardedVideoAdClosed() {
		trace("rewarded video closed");
	}

	@Override
	public void onRewardedVideoAdOpened() {
		trace("rewared video opened");
	}

	@Override
	public void onRewardedVideoAdRewarded(Placement arg0) {
		trace("onRewaredVideoAdReawared : " + arg0);
	}

	@Override
	public void onRewardedVideoInitFail(SupersonicError arg0) {
		trace("init fail : " + arg0);
	}

	@Override
	public void onRewardedVideoInitSuccess() {
		trace("init success");
	}

	@Override
	public void onRewardedVideoShowFail(SupersonicError arg0) {
		trace("error showing video : " +arg0 );
	}

	@Override
	public void onVideoAvailabilityChanged(boolean arg0) {
		trace("video availability changed : " + arg0);
	}

	@Override
	public void onVideoEnd() {
		trace("onVideoEnd");
	}

	@Override
	public void onVideoStart() {
		trace("onVideoStart");
	}
}