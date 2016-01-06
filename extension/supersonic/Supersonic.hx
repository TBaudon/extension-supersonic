package extension.supersonic;

#if cpp
import cpp.Lib;
#elseif neko
import neko.Lib;
#end

#if (android && openfl)
import openfl.utils.JNI;
#end


class Supersonic {
	
	static var mInstance : Supersonic;
	
	public static function init(appKey : String) : Supersonic{
		if (mInstance == null)
			mInstance = new Supersonic(appKey);
		else
			trace("Supersonic already inited");
		return mInstance;
	}
	
	public static function getInstance() : Supersonic {
		if (mInstance == null){
			trace("supersonic is not inited!");
			return null;
		}else
			return mInstance;
	}
	
	function new(appKey : String) {
		#if android
		jni_init(appKey, this);
		#end
	}
	
	public function showRewardedVideo(placementId : String = "") {
		#if android
		jni_showRewardedVideo(placementId);
		#end
	}
	
	#if android
	static var jni_init : Dynamic = JNI.createStaticMethod("org.haxe.extension.supersonic.ExtensionSupersonic", "init", "(Ljava/lang/String;Lorg/haxe/lime/HaxeObject;)V");
	static var jni_showRewardedVideo : Dynamic = JNI.createStaticMethod("org.haxe.extension.supersonic.ExtensionSupersonic", "showRewardedVideo", "(Ljava/lang/String;)V");
	#elseif ios
	#end
	
}