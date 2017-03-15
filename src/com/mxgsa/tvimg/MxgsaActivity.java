package com.mxgsa.tvimg;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class MxgsaActivity extends Activity{

	private TextView tView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mxgsa_activity);
		findControl();
		setData();
	}

	private void findControl() {
		tView = (TextView) findViewById(R.id.tvImage);
	}

	private void setData() {
		// TODO Auto-generated method stub
		final String sText = "测试自定义标签：<br><h1><mxgsa>测试自定义标签</mxgsa></h1>";
		tView.setText(Html.fromHtml(sText, null, new MxgsaTagHandler(this)));
		tView.setClickable(true);
		tView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	

}
