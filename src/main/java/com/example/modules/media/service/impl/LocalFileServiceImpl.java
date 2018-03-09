package com.example.modules.media.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.base.init.InitBaseData;
import com.example.base.modules.seq.util.SeqManage;
import com.example.base.util.JsonUtil;
import com.example.modules.media.entity.LocalVideo;
import com.example.modules.media.mapper.LocalVideoMapper;
import com.example.modules.media.service.ILocalFileService;
import com.example.modules.media.util.MediaLocalFileUtil;
import com.example.modules.media.util.MediaUtil;

@Service
public class LocalFileServiceImpl implements ILocalFileService {

	private static final Logger log = LoggerFactory.getLogger(LocalFileServiceImpl.class);
	
	@Autowired
	private LocalVideoMapper localVideoMapper;
	
	@Override
	public void doInitLocalVideo() {
		MediaLocalFileUtil.searceVideo(new File(MediaLocalFileUtil.rootPath));
		Calendar cal = Calendar.getInstance();
		String id = DateFormatUtils.format(cal, "yyyyMMddHHmmssS");
		for(LocalVideo video : MediaLocalFileUtil.videoList) {
			long s = System.currentTimeMillis();
			video.setVideoId(id+SeqManage.getSeqVal("local_video_seq"));
			String filepath = MediaUtil.getCompletePath(video);
			String md5code = MediaUtil.getFileMD5(filepath);
			video.setMd5Code(md5code);
			//localVideoMapper.insertSelective(video);
			log.info("耗时:{},大小:{},位置:{}",(System.currentTimeMillis()-s),MediaUtil.formatFileSize(Long.parseLong(video.getFileSize())),filepath);
		}
	}
	public List<LocalVideo> queryLocalVideo(LocalVideo video) {
		List<LocalVideo> videoList = localVideoMapper.queryAll();
		return videoList;
	}
}
