package com.mxgsa.tvimg;

import java.io.File;
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

import com.mxgsa.tvimg.DownLoadUtils.OnDownloadListener;

public class ImageActivity2 extends Activity {

	private TextView tView;
	private DownLoadUtils downLoadUtils;
	//保存文件路径
	private final String path="/mnt/sdcard/downimg/";
	//设置text的值
	String sText = "测试图片信息：<br><img src=\"http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg\" />";

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
		new File(path).mkdirs();
		//初始化下载类
		downLoadUtils=new DownLoadUtils();
		//设置下载类监听事件
		downLoadUtils.setOnDownloadListener(onDownloadListener);
		//给Textview赋值
		tView.setText(Html.fromHtml(sText, imageGetter, null));
	}

	final Html.ImageGetter imageGetter = new Html.ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			String fileString=path+String.valueOf(source.hashCode());
			Log.i("DEBUG", fileString+"");
			Log.i("DEBUG", source+"");
			//判断SD卡里面是否存在图片文件
			if (new File(fileString).exists()) {
				Log.i("DEBUG", fileString+"  eixts");
				//获取本地文件返回Drawable
				drawable=Drawable.createFromPath(fileString);
				//设置图片边界
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				return drawable;
			}else {
				Log.i("DEBUG", fileString+" Do not eixts");
				//启动新线程下载
				downLoadUtils.download(source, path+String.valueOf(source.hashCode()));
				return drawable;
			}
			
		};

	};
	OnDownloadListener onDownloadListener=new OnDownloadListener() {
		
		//下载进度
		public void onDownloadUpdate(DownLoadUtils manager, int percent) {
			// TODO Auto-generated method stub
			Log.i("DEBUG", percent+"");
		}
		
		//下载失败
		public void onDownloadError(DownLoadUtils manager, Exception e) {
			// TODO Auto-generated method stub
			
		}
		
		//开始下载
		public void onDownloadConnect(DownLoadUtils manager) {
			// TODO Auto-generated method stub
			Log.i("DEBUG", "Start  //////");
		}
		
		//完成下载
		public void onDownloadComplete(DownLoadUtils manager, Object result) {
			// TODO Auto-generated method stub
			Log.i("DEBUG", result.toString());
			//替换sTExt的值，就是把图片的网络路径换成本地SD卡图片路径(最早想法，可以不需要这样做了)
			//sText.replace(result.toString(), path+String.valueOf(result.hashCode()));
			//再一次赋值给Textview
			tView.setText(Html.fromHtml(sText, imageGetter, null));
		}
	};
	
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
}
