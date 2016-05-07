package myinterface;

import java.util.List;

import com.zbin.FileManager.Category_music.OnProgresslistener;
import com.zbin.bean.Category_music_info;

public interface musicinterface {

	// ÔÝÍ£²¥·Å
	public void dopause();

	// ¼ÌÐø²¥·Å
	public void doresume();

	// ²¥·Å
	public void dostart(int pos);

	// Í£Ö¹²¥·Å
	public void dostop();

	//
	// public <onMusicOver> void init(SeekBar seekbar,onMusicOver over);
	public void doRset();

	public void dorevmo();

	public void doppre();

	public void donext();

	public void setList(List<Category_music_info> playList);

	public List<Category_music_info>  getList(List<Category_music_info> playList);

	public void setOnProgressListener(OnProgresslistener listener);
}
