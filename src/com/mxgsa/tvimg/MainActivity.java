package com.mxgsa.tvimg;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	private TextView tView;
	private Button btnTestImg;
	private Button btnCustom;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findControl();
		setData();
	}

	private void findControl() {
		//tView = (TextView) findViewById(R.id.tvImage);
		btnCustom=(Button)findViewById(R.id.btnTestCustomer);
		btnTestImg=(Button)findViewById(R.id.btnTestImage);
	}

	private void setData() {
		// TODO Auto-generated method stub
//		final String sText = "测试图片信息：<br><img src=\"http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg\" />";
//		tView.setText(Html.fromHtml(sText, imageGetter, null));
		btnCustom.setOnClickListener(this);
		btnTestImg.setOnClickListener(this);
	}

	final Html.ImageGetter imageGetter = new Html.ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			URL url;
			try {
				url = new URL(source);
				drawable = Drawable.createFromStream(url.openStream(), "");
			} catch (Exception e) {
				return null;
			}
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			return drawable;
		};

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==btnCustom.getId()) {
			startActivity(new Intent(this,MxgsaActivity.class));
		}else if(v.getId()==btnTestImg.getId()){
			startActivity(new Intent(this,ImageActivity2.class));
		}
	}
}
