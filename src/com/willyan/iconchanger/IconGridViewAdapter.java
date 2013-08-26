package com.willyan.iconchanger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.willyan.iconchanger.imageloader.ImageLoader;

public class IconGridViewAdapter extends BaseAdapter {

	private Context mComtext;
	private int mStyle;
	private int[] mRes;
	private Holder holder = null;
	private ImageLoader mImageLoader;
	
	public static final int STYLE_SMARTISAN_1 = 2001;
	public static final int STYLE_SMARTISAN_2 = 2002;
	public static final int STYLE_SMARTISAN_3 = 2003;
	public static final int STYLE_SMARTISAN_4 = 2004;
	
	public static final int STYLE_IPHONE = 2005;
	public static final int STYLE_COLD_1 = 2006;
	public static final int STYLE_COLD_2 = 2007;
	public static final int STYLE_COLD_3 = 2008;
	
	public static final int[] STYLE = new int[]{STYLE_SMARTISAN_1,STYLE_SMARTISAN_2,
		STYLE_SMARTISAN_3,STYLE_SMARTISAN_4,STYLE_IPHONE,STYLE_COLD_1/*,STYLE_COLD_2,STYLE_COLD_3*/};

	public IconGridViewAdapter(Context context, int style) {
		super();
		mComtext = context;
		mStyle = style;
		mImageLoader = new ImageLoader(context, false);
		
		switch(mStyle){
		case STYLE_SMARTISAN_1:
			mRes = smartisan_res_1;
			break;
		case STYLE_SMARTISAN_2:
			mRes = smartisan_res_2;
			break;
		case STYLE_SMARTISAN_3:
			mRes = smartisan_res_3;
			break;
		case STYLE_SMARTISAN_4:
			mRes = smartisan_res_4;
			break;
		case STYLE_IPHONE:
			mRes = iphone_res;
			break;
		case STYLE_COLD_1:
			mRes = cold_res_1;
			break;
		default:
			break;
		}
	}

	@Override
	public int getCount() {
		return mRes.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mComtext);
			convertView = inflater.inflate(R.layout.item_icongrid, null);
			holder = new Holder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (mRes != null && position < mRes.length){
//			holder.imageView.setImageResource(mRes[position]);
			mImageLoader.displayImage(mRes[position], holder.imageView);
		}
		return convertView;
	}

	class Holder {
		ImageView imageView;
	}
	
	public static final int[] smartisan_res_1 = new int[]{R.drawable.smartisan_alipay,
		R.drawable.smartisan_amazon,R.drawable.smartisan_angrybirds,R.drawable.smartisan_bdmobile,
		R.drawable.smartisan_bestbuy,R.drawable.smartisan_calculator,R.drawable.smartisan_chrome_icon,
		R.drawable.smartisan_clock,R.drawable.smartisan_contacts,R.drawable.smartisan_ctrip,
		R.drawable.smartisan_dolphin,R.drawable.smartisan_dropbox,R.drawable.smartisan_facebook,
		R.drawable.smartisan_imdb,R.drawable.smartisan_instagram,R.drawable.smartisan_jingdong,
		R.drawable.smartisan_kuwo_player,R.drawable.smartisan_launcher,R.drawable.smartisan_linkedin,
		R.drawable.smartisan_meitu};
	
	public static final int[] smartisan_res_2 = new int[]{R.drawable.smartisan_xiaomi,
		R.drawable.smartisan_weather,R.drawable.smartisan_viber,R.drawable.smartisan_tudou,
		R.drawable.smartisan_tencent_weibo,R.drawable.smartisan_tencent_mm_icon,R.drawable.smartisan_sohu,
		R.drawable.smartisan_sogou,R.drawable.smartisan_sms,R.drawable.smartisan_sina_weibo,
		R.drawable.smartisan_settings,R.drawable.smartisan_popcap_pvz,R.drawable.smartisan_play,
		R.drawable.smartisan_phone,R.drawable.smartisan_note,R.drawable.smartisan_netease,
		R.drawable.smartisan_mymoney,R.drawable.smartisan_music,R.drawable.smartisan_momo,
		R.drawable.smartisan_meituan};
	
	public static final int[] smartisan_res_3 = new int[]{R.drawable.com_autonavi_minimap_icon,
		R.drawable.com_baidumap_icon,R.drawable.com_batterydoctor,R.drawable.com_blovestorm_icon,
		R.drawable.com_changba_icon,R.drawable.com_dictionary_icon,R.drawable.com_estrongs,
		R.drawable.com_google_android_gm_icon,R.drawable.com_google_android_street_icon,R.drawable.com_google_maps,
		R.drawable.com_googlequicksearchbox,R.drawable.com_halfbrick_fruitninjafree_icon,R.drawable.com_handsgo_jiakao_android_icon,
		R.drawable.com_hulu_plus_icon,R.drawable.com_ijinshan_mguard_icon,R.drawable.com_jb_gosms_icon,
		R.drawable.com_kingsoft_icon,R.drawable.com_merriamwebster_icon,R.drawable.com_nd_android_smarthome_icon,
		R.drawable.com_netflix_mediaclient_icon};
	
	public static final int[] smartisan_res_4 = new int[]{R.drawable.com_zinio,
		R.drawable.com_zeptolab_gamebase,R.drawable.com_zeptolab,R.drawable.com_yoloho_dayima_icon,
		R.drawable.com_xinmei365_font_icon,R.drawable.com_wuba_icon,R.drawable.com_wali_walisms_icon,
		R.drawable.com_uc_icon,R.drawable.com_tumblr_icon,R.drawable.com_tiros,
		R.drawable.com_tencent_qqpimsecure_icon,R.drawable.com_tencent_qqpim_icon,R.drawable.com_tencent_qqmusic_icon,
		R.drawable.com_storm_smart_icon,R.drawable.com_skype_rover_icon,R.drawable.com_sketchbookexpress_icon,
		R.drawable.com_qunar_icon,R.drawable.com_qiyi_video_market_icon,R.drawable.com_pandora_android_icon,
		R.drawable.com_olivephone_edit_icon};
	
	public static final int[] iphone_res = new int[]{R.drawable.iphone_browser,R.drawable.iphone_cal,
		R.drawable.iphone_calblank,R.drawable.iphone_calc,R.drawable.iphone_camera,
		R.drawable.iphone_chat,R.drawable.iphone_clock,R.drawable.iphone_dial,
		R.drawable.iphone_graph,R.drawable.iphone_ipod,R.drawable.iphone_mail,
		R.drawable.iphone_map,R.drawable.iphone_notes,R.drawable.iphone_sms,
		R.drawable.iphone_tools,R.drawable.iphone_wallpaper,R.drawable.iphone_weather,
		R.drawable.iphone_browser,R.drawable.iphone_dial,R.drawable.iphone_mail};
	
	public static final int[] cold_res_1 = new int[]{R.drawable.cold_24sata,R.drawable.cold_ace,
		R.drawable.cold_adblock,R.drawable.cold_aldiko,R.drawable.cold_amazon,
		R.drawable.cold_basketball,R.drawable.cold_baby,R.drawable.cold_angrybirds,
		R.drawable.cold_bootstrap,R.drawable.cold_calculator,R.drawable.cold_catan,
		R.drawable.cold_chrome,R.drawable.cold_clock,R.drawable.cold_dial,
		R.drawable.cold_disney,R.drawable.cold_dropbox,R.drawable.cold_email,
		R.drawable.cold_facebook,R.drawable.cold_file,R.drawable.cold_fludnews};
}
