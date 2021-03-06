package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.drawable.*;
import java.io.*;
import java.lang.reflect.*;
import android.os.*;
import java.util.*;

// quoted almost code from
// https://github.com/android/platform_packages_apps_packageinstaller/blob/master/src/com/android/packageinstaller/PackageInstallerActivity.java
// https://github.com/android/platform_packages_apps_packageinstaller/blob/master/src/com/android/packageinstaller/PackageUtil.java
// https://github.com/android/platform_frameworks_base/blob/master/core/java/android/content/pm/PackageParser.java
public class AndroidPackage
{
	static int necessaryFlags=PackageManager.GET_CONFIGURATIONS|PackageManager.GET_INSTRUMENTATION|PackageManager.GET_META_DATA|PackageManager.GET_SIGNATURES;
	static String pPrefix="android.content.pm.";
	static Class packageParser=declare("PackageParser");
	static Class packagE=declare("PackageParser$Package");
	static Class packageUserState=declare("PackageUserState");
	static Class genPackInfoSet=loadClassGpis();
	static Class inCl=int.class;
	static Class declare(String name){
		try {
			return Class.forName(pPrefix + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	static Class loadClassGpis(){
		if(Build.VERSION.SDK_INT>=23){
			return Set.class;
		}else{
			try {
				return Class.forName("android.util.ArraySet");
			} catch (ClassNotFoundException e) {
				
			}
		}
		return Set.class;
	}
	
	Object parser,packaGE;
	PackageInfo info;
	public AndroidPackage(String file){
		this(new File(file));
	}
	public AndroidPackage(File file){
		Throwable ex=null;
		try {
			parser = packageParser.newInstance();
			packaGE = packageParser.getMethod("parseMonolithicPackage", File.class, inCl).invoke(parser, file, 0);
			packageParser.getMethod("collectManifestDigest", packagE).invoke(parser, packaGE);
			info = (PackageInfo)packageParser.getMethod("generatePackageInfo", packagE, int[].class, inCl, long.class, long.class, genPackInfoSet, packageUserState).invoke(null, packaGE, null, necessaryFlags, 0, 0, null, packageUserState.newInstance());
		} catch (InvocationTargetException e) {
			(ex=e).printStackTrace();
		} catch (IllegalArgumentException e) {
			(ex=e).printStackTrace();
		} catch (InstantiationException e) {
			(ex=e).printStackTrace();
		} catch (IllegalAccessException e) {
			(ex=e).printStackTrace();
		} catch (NoSuchMethodException e) {
			(ex=e).printStackTrace();
		}
		if(info==null){
			throw new RuntimeException("An error occured while parsing the file.",ex);
		}
	}
	public PackageInfo getResult(){
		return info;
	}
	
	static public class AppSnippet {
        CharSequence label;
        Drawable icon;
        public AppSnippet(CharSequence label, Drawable icon) {
            this.label = label;
            this.icon = icon;
        }
    }

    public static AppSnippet getAppSnippet(
		Activity pContext, ApplicationInfo appInfo, File sourceFile) {
        final String archiveFilePath = sourceFile.getAbsolutePath();
        Resources pRes = pContext.getResources();
		AssetManager assmgr=null;
        try {
			assmgr = AssetManager.class.newInstance();
			AssetManager.class.getMethod("addAssetPath", String.class).invoke(assmgr, archiveFilePath);
		} catch (InvocationTargetException e) {
			
		} catch (IllegalArgumentException e) {
			
		} catch (InstantiationException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (NoSuchMethodException e) {
			
		}
        Resources res = new Resources(assmgr, pRes.getDisplayMetrics(), pRes.getConfiguration());
        CharSequence label = null;
        if (appInfo.labelRes != 0) {
            try {
                label = res.getText(appInfo.labelRes);
            } catch (Resources.NotFoundException e) {
            }
        }
        if (label == null) {
            label = (appInfo.nonLocalizedLabel != null) ?
				appInfo.nonLocalizedLabel : appInfo.packageName;
        }
        Drawable icon = null;
        if (appInfo.icon != 0) {
            try {
                icon = res.getDrawable(appInfo.icon);
            } catch (Resources.NotFoundException e) {
            }
        }
        if (icon == null) {
            icon = pContext.getPackageManager().getDefaultActivityIcon();
        }
        return new AppSnippet(label, icon);
    }
}
