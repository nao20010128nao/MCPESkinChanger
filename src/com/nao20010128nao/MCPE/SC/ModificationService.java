package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import com.nao20010128nao.SpoofBrowser.classes.*;
import com.nao20010128nao.ToolBox.*;
import java.io.*;
import java.lang.ref.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;
import kellinwood.security.zipsigner.*;

public class ModificationService extends ServiceX {
	Map<String,URI> skins=ModificateActivity.skins;
	public static WeakReference<ModificationService> instance=new WeakReference<>(null);
	@Override
	public IBinder onBind() {
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(int flags, int startId) {
		// TODO: Implement this method
		instance = new WeakReference<ModificationService>(this);
		final Notification.Builder n=new Notification.Builder(this);
		//final Notification ntf;
		n.setContentTitle(getResources().getString(R.string.app_name));
		n.setContentText("");
		n.setContentIntent(PendingIntent.getActivity(this, -1, new Intent().setClass(this, ModificateActivity.class).putExtra("mode", "noservice"), Intent.FLAG_ACTIVITY_CLEAR_TOP));
		n.setSmallIcon(R.drawable.paw).setLargeIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.paw)).getBitmap());
		startForeground(100, n.build());
		final NotificationManager mNM=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNM.notify(100, n.build());
		//Toast.makeText(this,"",Toast.LENGTH_LONG).show();
		new AsyncTask<Void,Integer,Void>(){
			public Void doInBackground(Void[] p) {
				ModificateActivity.set(-1, -1, -1, -1, null);
				ModificateActivity.set(getResources().getStringArray(R.array.modSteps).length - 1, 0, -1, -1, null);
				try {
					List<String> args=new ArrayList<>();
					args.add("/system/bin/dalvikvm");
					args.add("-classpath");
					args.add(getApplicationInfo().sourceDir);
					args.add(IsolatedChanger.class.getName());
					args.add("-apk");
					args.add(openAPK());
					args.add("-output");
					args.add(saveAPK());
					args.add("-changes");
					args.add(buildChanges());
					args.add("-cache");
					args.add(getCacheDir().toString());
					java.lang.Process proc=
						new ProcessBuilder()
							.command(args)
							.directory(getCacheDir())
							.redirectErrorStream(true)
							.start();
					BufferedReader br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
					while(true){
						String s=br.readLine();
						if(s==null)break;
						Log.d("line",s);
						String[] dat=s.split("\\:");
						String cmd=dat[0];
						if("Status".equals(cmd)){
							String stat=dat[1];
							String[] sv=stat.split("\\;");
							ModificateActivity.set(new Integer(sv[0]),new Integer(sv[1]),new Integer(sv[2]),new Integer(sv[3]),null);
							publishProgress(new Integer(sv[4]));
						}else if("ErrorToast".equals(cmd)){
							Toast.makeText(ModificationService.this,new Integer(dat[2]),1).show();
							finishForExit();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (PackageManager.NameNotFoundException e) {

				}
				//
				if(Tools.getSettings("output.mode", 0, ModificationService.this)==1){
					String dst=Tools.getSettings("output.where", "", ModificationService.this);
					String frm=new File(getCacheDir(),"result.apk").toString();
				}
				return null;
			}
			public String buildChanges(){
				StringBuilder sb=new StringBuilder();
				for(Map.Entry<String,URI> ent:skins.entrySet()){
					sb
						.append(';')
						.append(ent.getKey())
						.append(':')
						.append(ent.getKey());
				}
				return sb.substring(2).toString();
			}
			public void finishForExit(){
				Activity a=null;
				if ((a=ModificateActivity.instance.get()) != null){
					try {
						findFinish().invoke(a);
					} catch (InvocationTargetException e) {
					} catch (IllegalAccessException e) {
					} catch (IllegalArgumentException e) {
					}
				}
			}
			public void onProgressUpdate(Integer[] a) {
				n.setContentText(getResources().getStringArray(R.array.modSteps)[a[0]]);
				mNM.notify(100, n.build());
				ModificateActivity.set(-1, a[0], -1, -1, getResources().getStringArray(R.array.modSteps)[a[0]]);
			}
			String openAPK() throws PackageManager.NameNotFoundException {
				switch (Tools.getSettings("input.mode", 0, ModificationService.this)) {
					case 0://installed
						return createPackageContext("com.mojang.minecraftpe", CONTEXT_IGNORE_SECURITY).getPackageCodePath();
					case 1://select
						return Tools.getSettings("input.where", "", ModificationService.this);
					default:
						return null;
				}
			}
			String saveAPK(){
				switch (Tools.getSettings("output.mode", 0, ModificationService.this)) {
					case 0://installed
						File f=new File(Environment.getExternalStorageDirectory(), "games/com.mojang");
						(f = new File(f, "skinchanger")).mkdirs();
						return new File(f, "signed.apk").getAbsolutePath();
					case 1://select
						return new File(getCacheDir(),"result.apk").toString();
					default:
						return null;
				}
			}
			Method findFinish(){
				Class actClas=Activity.class;
				try{
					return actClas.getDeclaredMethod("finishAndRemoveTask");
				}catch(Throwable ex){
					ex.printStackTrace();
				}
				try{
					//finish() must be found
					return actClas.getDeclaredMethod("finish");
				}catch(Throwable ex){
					//unreachable
					ex.printStackTrace();
				}
				return null;//unreachable
			}
		}.execute();
		return START_NOT_STICKY;
	}
}
