package com.nao20010128nao.MCPE.SC;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import com.nao20010128nao.ToolBox.*;
import com.nao20010128nao.ToolBox.HandledPreference.*;
import java.io.*;
import java.lang.ref.*;
import java.net.*;
import java.util.*;
import android.content.pm.PackageManager.*;
import android.content.res.Resources.*;
import com.nao20010128nao.MCPE.SC.misc.*;
import com.nao20010128nao.MCPE.SC.plugin.*;

public class ControllerActivity extends SHablePreferenceActivity {
	public static WeakReference<ControllerActivity> instance=new WeakReference<>(null);
	static boolean preventStart=false;;
	Map<String,URI> skins=ModificateActivity.skins;
	String changeTmp=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		instance = new WeakReference<ControllerActivity>(this);
		if (ModificationService.instance.get() != null) {
			startActivity(new Intent(this, ModificateActivity.class).putExtra("mode", "noservice"));
			ControllerActivity.preventStart();
		}
		if(preventStart){
			finish();
			return;
		}
       	addPreferencesFromResource(R.xml.pref_main);
		sH("startChange", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					if (skins.size() == 0) {
						new AlertDialog.Builder(ControllerActivity.this)
							.setTitle(R.string.err_title)
							.setCancelable(true)
							.setMessage(R.string.err_no_skins)
							.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface a, int b) {
									a.cancel();
								}
							})
							.create()
							.show();
						return;
					}
					startActivity(new Intent(ControllerActivity.this, ModificateActivity.class).putExtra("mode", "normal"));
				}
			});
		sH("wantUpdate", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					for (ResolveInfo i:getPackageManager().queryIntentActivities(new Intent().setAction("android.intent.action.VIEW").setData(Uri.parse("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe")), 0)) {
						Log.d("dbg", i.activityInfo.packageName + ";" + i.activityInfo.name);
						if (i.activityInfo.packageName.equals("com.android.vending")) {
							startActivity(new Intent().setClassName(i.activityInfo.packageName, i.activityInfo.name).setAction("android.intent.action.VIEW").setData(Uri.parse("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe")));
						}
					}
				}
			});
		link("selectSteve", "assets/images/mob/char.png");
		link("selectSteveN", "assets/images/mob/steve.png");
		link("selectAlexN", "assets/images/mob/alex.png");
		link("selectZombie", "assets/images/mob/zombie.png");
		link("selectZombiePigman", "assets/images/mob/pigzombie.png");
		link("selectCow", "assets/images/mob/cow.png");
		link("selectCreeper", "assets/images/mob/creeper.png");
		link("selectPig", "assets/images/mob/pig.png");
		link("selectSkeleton", "assets/images/mob/skeleton.png");
		link("selectSheep0", "assets/images/mob/sheep_0.png");
		link("selectSheep1", "assets/images/mob/sheep_1.png");
		link("selectSheep2", "assets/images/mob/sheep_2.png");
		link("selectSheep3", "assets/images/mob/sheep_3.png");
		link("selectSheep4", "assets/images/mob/sheep_4.png");
		link("selectSheep5", "assets/images/mob/sheep_5.png");
		link("selectSheep6", "assets/images/mob/sheep_6.png");
		link("selectSheep7", "assets/images/mob/sheep_7.png");
		link("selectSheep8", "assets/images/mob/sheep_8.png");
		link("selectSheep9", "assets/images/mob/sheep_9.png");
		link("selectSheep10", "assets/images/mob/sheep_10.png");
		link("selectSheep11", "assets/images/mob/sheep_11.png");
		link("selectSheep12", "assets/images/mob/sheep_12.png");
		link("selectSheep13", "assets/images/mob/sheep_13.png");
		link("selectSheep14", "assets/images/mob/sheep_14.png");
		link("selectSheep15", "assets/images/mob/sheep_15.png");
		
		link("selectGhastNormal", "assets/images/mob/ghast.png");
		link("selectGhastShooting", "assets/images/mob/ghast_shooting.png");
		link("selectSnowGolem", "assets/images/mob/snow_golem.png");
		link("selectIronGolem", "assets/images/mob/iron_golem.png");
		link("selectSquid", "assets/images/mob/squid.png");
		link("selectWolfNormal", "assets/images/mob/wolf.png");
		link("selectWolfAngry", "assets/images/mob/wolf_angry.png");
		link("selectBat", "assets/images/mob/bat.png");
		link("selectBlaze", "assets/images/mob/blaze.png");
		link("selectChicken", "assets/images/mob/chicken.png");
		link("selectMooshroom", "assets/images/mob/mooshroom.png");
		link("selectMagmaCube", "assets/images/mob/magmacube.png");
		link("selectSilverFish", "assets/images/mob/silverfish.png");
		link("selectSlime", "assets/images/mob/slime.png");
		link("selectWitherSkeleton", "assets/images/mob/wither_skeleton.png");
		
		sH("inputFrom", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					final int revert=Tools.getSettings("input.mode", 0, ControllerActivity.this);
					new AlertDialog.Builder(ControllerActivity.this).
						setTitle(R.string.inputFrom).
						setSingleChoiceItems(R.array.inputList, revert, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("input.mode", where, ControllerActivity.this);
							}
						}).
						setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("input.mode", revert, ControllerActivity.this);
							}
						}).
						setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								switch (Tools.getSettings("input.mode", 0, ControllerActivity.this)) {
									case 0://installed
										break;
									case 1://select
										startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("application/vnd.android.package-archive"), 456);
										break;
								}
							}
						}).
						show();
				}
			});
		sH("outputTo", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					final int revert=Tools.getSettings("output.mode", 0, ControllerActivity.this);
					new AlertDialog.Builder(ControllerActivity.this).
						setTitle(R.string.outputTo).
						setSingleChoiceItems(R.array.outputList, revert, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("output.mode", where, ControllerActivity.this);
							}
						}).
						setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								Tools.setSettings("output.mode", revert, ControllerActivity.this);
							}
						}).
						setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								switch (Tools.getSettings("output.mode", 0, ControllerActivity.this)) {
									case 0://install
										break;
									case 1://select
										startActivityForResult(new Intent(Intent.ACTION_CREATE_DOCUMENT).setType("application/vnd.android.package-archive"), 789);
										break;
								}
							}
						}).
						show();
				}
			});
		sH("chgList", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					startActivity(new Intent(ControllerActivity.this, ChangingListEditor.class));
				}
			});
		Preference p;
		(p=findPreference("version")).setSummary(Utils.getVersionName(this));
		p.setEnabled(false);
		(p=findPreference("betabuild")).setSummary(Utils.getBetaVersion(this));
		p.setEnabled(false);
		sH("openPlayStore",new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())),getResources().getString(R.string.openPlayStore)));
				}
			});
		sH("plugins", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					startActivity(new Intent(ControllerActivity.this, PluginLauncher.class));
				}
			});
		sH("openSourceLic",new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					startActivity(new Intent(ControllerActivity.this, OpenSourceActivity.class));
				}
			});
		sH("appInfo", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					startActivity(new Intent(ControllerActivity.this, AppInfoActivity.class));
				}
			});
		sH("changerImpl", new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					final int revert=RunOnceApplication.instance.getChangerImpl();
					new AlertDialog.Builder(ControllerActivity.this).
						setTitle(R.string.changerImpl).
						setSingleChoiceItems(R.array.changerImplSelections, revert, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								RunOnceApplication.instance.setChangerImpl(where);
							}
						}).
						setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								RunOnceApplication.instance.setChangerImpl(revert);
							}
						}).
						setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface di, int where) {
								di.cancel();
							}
						}).
						show();
					}
			});
		try {
			if(!RunOnceApplication.instance.getLiteMode())
				ImageLoader.startLoadImagesAsync(getApkPath());
		} catch (PackageManager.NameNotFoundException e) {
			
		}
    }
	void selectFileForSkin(String name){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/png");
		changeTmp = name;
		startActivityForResult(intent, 123);
	}
	void link(String pref,String name){
		sH(pref,createListenerForPerf(name));
		Object data=findPreference(pref);
		if(data instanceof ImageHandler.ImageHandlerReceiver){
			String[] arr;
			ImageHandler.registerReceiver((arr=name.split("¥¥/"))[arr.length-1],(ImageHandler.ImageHandlerReceiver)data);
		}
	}
	OnClickListener createListenerForPerf(final String name){
		return new OnClickListener(){
			public void onClick(String p1, String p2, String p3) {
				selectFileForSkin(name);
			}
		};
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 123:
				if (resultCode == RESULT_OK) {
					Log.d("dbg", "INTENT:" + data.getDataString());
					try {
						skins.put(changeTmp, new URI(data.getDataString()));
					} catch (URISyntaxException e) {

					}
					startActivityForResult(new Intent(this, ContentFileLocalCopyActivity.class).
										   setData(Uri.parse(data.getDataString())).
										   putExtra("path", changeTmp), 1231);
				}
				changeTmp = null;
				break;
			case 1231:
				try {
					skins.put(data.getStringExtra("path"), new File(data.getStringExtra("result")).toURI());
				} catch (Throwable e) {

				}
				break;
			case 456:
				if (resultCode == RESULT_OK) {
					Tools.setSettings("input.where", data.getDataString(), this);
					startActivityForResult(data.setClass(this, ContentFileLocalCopyActivity.class).putExtra("dest", new File(getFilesDir(), "mcpeCopy_unchecked.apk") + ""), 4561);
				} else if (Tools.getSettings("input.where", (String)null, this) == null)
					Tools.setSettings("input.mode", 0, this);
				break;
			case 4561:
				final File unchecked=new File(data.getStringExtra("result"));
				File checked=new File(getFilesDir(), "mcpeCopy.apk");
				if(unchecked.exists()){
					try {
						AndroidPackage pak=new AndroidPackage(unchecked);
						PackageInfo info=pak.getResult();
						if ("com.mojang.minecraftpe".equals(info.packageName)) {
							unchecked.renameTo(checked);
							if(!RunOnceApplication.instance.getLiteMode())
								ImageLoader.startLoadImagesAsync(checked.toString());
						} else {
							ApplicationInfo appInfo=info.applicationInfo;
							AndroidPackage.AppSnippet as=AndroidPackage.getAppSnippet(this, appInfo, unchecked);
							CharSequence s=as.label;
							String mes=getResources().getString(R.string.fakeapp).replace("@APP@", s);
							new AlertDialog.Builder(this).
								setMessage(mes).
								setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
									public void onClick(DialogInterface a, int w) {
										a.cancel();
										unchecked.delete();
									}
								}).
								show();
						}
					} catch (Throwable e) {
						new AlertDialog.Builder(this).
							setMessage(R.string.badapk).
							setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface a, int w) {
									a.cancel();
									unchecked.delete();
								}
							}).
							show();
					}
				} else {
					return;
				}
				Tools.setSettings("input.where", data.getStringExtra("result"), this);
				break;
			case 789:
				if (resultCode == RESULT_OK)
					Tools.setSettings("output.where", data.getDataString(), this);
				else if (Tools.getSettings("output.where", (String)null, this) == null)
					Tools.setSettings("output.mode", 0, this);
				break;
		}
	}
	public static void preventStart(){
		preventStart=true;
	}
	String getApkPath() throws PackageManager.NameNotFoundException {
		switch (Tools.getSettings("input.mode", 0, this)) {
			case 0://installed
				return createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY).getPackageCodePath();
			case 1://select
				return Tools.getSettings("input.where", "", this);
			default:
				return null;
		}
	}
}
