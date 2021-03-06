package com.nao20010128nao.MCPE.SC;
import android.content.*;
import android.content.pm.*;
import android.content.pm.PackageManager.*;
import android.content.res.*;
import android.graphics.*;
import android.util.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import java.io.*;
import java.util.*;

public class Utils
{
	public static final Size[] MC_HUMAN_SKIN_VALID_SIZES=new Size[]{
		new Size(64,32),//Old skin
		new Size(64,64),//New skin
	};
	public static int getVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        int versionCode = 0;
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        }catch(NameNotFoundException e){
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        }catch(NameNotFoundException e){
            e.printStackTrace();
        }
        return versionName;
	}
	
	public static String getBetaVersion(Context c){
		Resources res=c.getResources();
		int build=res.getInteger(R.integer.beta);
		if(build==0){
			return res.getString(R.string.notbeta);
		}else{
			return res.getString(R.string.onbeta).replace("@BETA@",Integer.toString(build));
		}
	}
	
	public static class MobNames{
		private static final Map<String,Integer> fromFileName;
		static{
			Map<Integer,String> temp=new HashMap<>();
			temp.put(R.string.Steve, "assets/images/mob/char.png");
			temp.put(R.string.SteveN, "assets/images/mob/steve.png");
			temp.put(R.string.AlexN, "assets/images/mob/alex.png");
			temp.put(R.string.Zombie, "assets/images/mob/zombie.png");
			temp.put(R.string.ZombiePigman, "assets/images/mob/pigzombie.png");
			temp.put(R.string.Cow, "assets/images/mob/cow.png");
			temp.put(R.string.Creeper, "assets/images/mob/creeper.png");
			temp.put(R.string.Pig, "assets/images/mob/pig.png");
			temp.put(R.string.Skeleton, "assets/images/mob/skeleton.png");
			temp.put(R.string.Sheep0, "assets/images/mob/sheep_0.png");
			temp.put(R.string.Sheep1, "assets/images/mob/sheep_1.png");
			temp.put(R.string.Sheep2, "assets/images/mob/sheep_2.png");
			temp.put(R.string.Sheep3, "assets/images/mob/sheep_3.png");
			temp.put(R.string.Sheep4, "assets/images/mob/sheep_4.png");
			temp.put(R.string.Sheep5, "assets/images/mob/sheep_5.png");
			temp.put(R.string.Sheep6, "assets/images/mob/sheep_6.png");
			temp.put(R.string.Sheep7, "assets/images/mob/sheep_7.png");
			temp.put(R.string.Sheep8, "assets/images/mob/sheep_8.png");
			temp.put(R.string.Sheep9, "assets/images/mob/sheep_9.png");
			temp.put(R.string.Sheep10, "assets/images/mob/sheep_10.png");
			temp.put(R.string.Sheep11, "assets/images/mob/sheep_11.png");
			temp.put(R.string.Sheep12, "assets/images/mob/sheep_12.png");
			temp.put(R.string.Sheep13, "assets/images/mob/sheep_13.png");
			temp.put(R.string.Sheep14, "assets/images/mob/sheep_14.png");
			temp.put(R.string.Sheep15, "assets/images/mob/sheep_15.png");

			temp.put(R.string.GhastNormal, "assets/images/mob/ghast.png");
			temp.put(R.string.GhastShooting, "assets/images/mob/ghast_shooting.png");
			temp.put(R.string.SnowGolem, "assets/images/mob/snow_golem.png");
			temp.put(R.string.IronGolem, "assets/images/mob/iron_golem.png");
			temp.put(R.string.Squid, "assets/images/mob/squid.png");
			temp.put(R.string.WolfNormal, "assets/images/mob/wolf.png");
			temp.put(R.string.WolfAngry, "assets/images/mob/wolf_angry.png");
			temp.put(R.string.Bat, "assets/images/mob/bat.png");
			temp.put(R.string.Blaze, "assets/images/mob/blaze.png");
			temp.put(R.string.Chicken, "assets/images/mob/chicken.png");
			temp.put(R.string.Mooshroom, "assets/images/mob/mooshroom.png");
			temp.put(R.string.MagmaCube, "assets/images/mob/magmacube.png");
			temp.put(R.string.SilverFish, "assets/images/mob/silverfish.png");
			temp.put(R.string.Slime, "assets/images/mob/slime.png");
			temp.put(R.string.WitherSkeleton, "assets/images/mob/wither_skeleton.png");
			
			fromFileName=swapMap(temp);
		}
		private static <K,V> Map<K,V> swapMap(Map<V,K> from){
			Map<K,V> toReturn=new HashMap<>(from.size());
			for(V key:from.keySet()){
				toReturn.put(from.get(key),key);
			}
			return toReturn;
		}
		public static int getResourceIdFromFileName(String s){
			return fromFileName.get(s);
		}
		public static String localizeMobNameFromFileName(String s,Context ctx){
			return ctx.getResources().getString(getResourceIdFromFileName(s));
		}
		public static boolean isNameAvaliable(String s){
			return fromFileName.containsKey(s);
		}
	}
	public static String getRandomString() {
		StringBuilder sb=new StringBuilder("cache_");
		Random r=new Random();
		for (int i=0;i < 9;i++) {
			String append=String.format("%06x", r.nextInt() & 0xff).substring(4);
			sb.append(append);
		}
		Log.d("random", sb.toString());
		return sb.toString();
	}
	public static Size getImageSize(InputStream is){
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, bitmapOptions).recycle();
		int width  = bitmapOptions.outWidth;
		int height = bitmapOptions.outHeight;
		return new Size(width,height);
	}
	public static boolean isValidHumanSkin(Size s){
		for(Size ss:MC_HUMAN_SKIN_VALID_SIZES){
			if(ss.equals(s)|s.equals(ss)){
				return true;
			}
		}
		return false;
	}
}
