package com.nao20010128nao.MCPE.SC;
import android.app.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import com.nao20010128nao.MC_PE.SkinChanger.*;
import java.io.*;
import java.net.*;

public class ContentFileLocalCopyActivity extends Activity {
	TextView state;
	ProgressBar progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checker);
		state = (TextView)findViewById(R.id.tvState);
		progress = (ProgressBar)findViewById(R.id.pbProgress);
		state.setText(R.string.wait);
		progress.setIndeterminate(true);
		new AsyncTask<Void,Void,String>(){
			public String doInBackground(Void... a) {
				try {
					String contentUri=getIntent().getDataString();
					String saveFile=getIntent().getExtras().getString("dest", null);
					if (saveFile == null) {
						File f;
						(f = new File(getFilesDir(), "cache")).mkdir();
						saveFile = new File(f, Utils.getRandomString()).toString();
					}
					InputStream from=tryOpen(contentUri);
					OutputStream to=trySave(saveFile);
					byte[]buf=new byte[1000];
					while (true) {
						int r=from.read(buf);
						if (r <= 0) {
							break;
						}
						to.write(buf, 0, r);
					}
					return saveFile;
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
				return null;
			}
			public void onPostExecute(String a) {
				setResult(RESULT_OK, getIntent().putExtra("result", a));
				finish();
			}
		}.execute();
	}
	public InputStream tryOpen(String uri) throws IOException {
		Log.d("dbg", "tryOpen:" + uri);
		if (uri.startsWith("content://")) {
			return getContentResolver().openInputStream(Uri.parse(uri));
		} else if (uri.startsWith("/")) {
			return new FileInputStream(uri);
		} else {
			return URI.create(uri).toURL().openConnection().getInputStream();
		}
	}
	public OutputStream trySave(String uri) throws IOException {
		Log.d("dbg", "trySave:" + uri);
		if (uri.startsWith("content://")) {
			return getContentResolver().openOutputStream(Uri.parse(uri));
		} else if (uri.startsWith("/")) {
			return new FileOutputStream(uri);
		} else {
			return URI.create(uri).toURL().openConnection().getOutputStream();
		}
	}
}
