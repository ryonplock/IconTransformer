package com.willyan.iconchanger;

import com.willyan.iconchanger.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class IconGridViewAdapter extends BaseAdapter {

	private Context mComtext;
	private int mStyle;
	private int[] mRes;
	private Holder holder = null;
	
	public static final int STYLE_SMARTISAN_1 = 2001;
	public static final int STYLE_SMARTISAN_2 = 2002;
	public static final int STYLE_SMARTISAN_3 = 2003;
	public static final int STYLE_SMARTISAN_4 = 2004;
	
	public static final int STYLE_MARCE = 2005;
	public static final int STYLE_MATT = 2006;
	
	public static final int[] STYLE = new int[]{STYLE_SMARTISAN_1,STYLE_SMARTISAN_2,
		STYLE_SMARTISAN_3,STYLE_SMARTISAN_4,STYLE_MARCE,STYLE_MATT};

	public IconGridViewAdapter(Context context, int style) {
		super();
		mComtext = context;
		mStyle = style;
		
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
		case STYLE_MARCE:
			mRes = marce_res;
			break;
		case STYLE_MATT:
			mRes = matt_res;
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
			holder.imageView.setImageResource(mRes[position]);
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
	
	public static final int[] marce_res = new int[]{R.drawable.marce_appstore,R.drawable.marce_calendar,
		R.drawable.marce_camera,R.drawable.marce_clock,R.drawable.marce_contacts,
		R.drawable.marce_ipod,R.drawable.marce_itunes,R.drawable.marce_mail,
		R.drawable.marce_maps,R.drawable.marce_music,R.drawable.marce_notes,
		R.drawable.marce_phone,R.drawable.marce_photos,R.drawable.marce_safari,
		R.drawable.marce_settings,R.drawable.marce_stocks,R.drawable.marce_text,
		R.drawable.marce_videos,R.drawable.marce_weather,R.drawable.marce_youtube};
	
	public static final int[] matt_res = new int[]{R.drawable.matt_addressbook,R.drawable.matt_appstore,
		R.drawable.matt_calculator,R.drawable.matt_mail,R.drawable.matt_maps,
		R.drawable.matt_messages,R.drawable.matt_mobilecal,R.drawable.matt_mobilestore,
		R.drawable.matt_mobiletimer,R.drawable.matt_notes,R.drawable.matt_phone,
		R.drawable.matt_preferences,R.drawable.matt_safari,R.drawable.matt_stocks,
		R.drawable.matt_winterboard,R.drawable.matt_youtube};
	
	/**
	 * icon styles:
	 * marce: 20 Ciceronian @ Marcelo Marfil http://findicons.com/pack/2101/ciceronian
	 * matt: 16 Austerity @ Mattrich http://findicons.com/pack/2206/austerity
	 */
}
