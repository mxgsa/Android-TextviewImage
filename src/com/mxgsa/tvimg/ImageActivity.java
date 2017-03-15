package com.mxgsa.tvimg;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class ImageActivity extends Activity {

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

	public static List<String> getImgStr(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	private void setData() {
		// TODO Auto-generated method stub
		final String sText = "测试图片信息：<br><img src=\"http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg\" /><img src=\""+R.drawable.market_none_image+"\" />";
		final String sText1 = "测试图片信息：<img src=\""+R.drawable.market_none_image+"\" />";
		final String sText2 = "测试图片信息：<img src=\"/mnt/sdcard/temp/1.jpg\" />";
		// List<String> list = getImgStr(sText);
		// for (String s:list) {
		// ImageLoader imageLoader=new ImageLoader(this);
		// Drawable drawable= imageLoader.getDrawable(s, this);
		// Log.d(this.getClass().toString(), drawable.toString());
		// }
		tView.setText(Html.fromHtml(sText, imageGetter, null));
	}

	final Html.ImageGetter imageGetter = new Html.ImageGetter() {
		// 重载获取source下的方法
		// 例如HTML:<img
		// src="http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg" />
		// 则source的值就是：http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			// 第一种方式，Android2.3的可以显示图片，Android4.0的不能显示图片
			URL url;
			try {
				url = new URL(source);
				drawable = Drawable.createFromStream(url.openStream(), "20121108_091749_1.jpg");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			Log.d(this.getClass().toString(), drawable.toString());
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());			
			return drawable;
			//项目资源文件
//			Drawable drawable=null;
//			int rId=Integer.parseInt(source);
//			drawable=getResources().getDrawable(rId);
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//			return drawable;
			
			//本地SD里面图片
//			Drawable drawable=null;
//			drawable=Drawable.createFromPath(source);
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//			return drawable;
			
			
		};

	};
}
