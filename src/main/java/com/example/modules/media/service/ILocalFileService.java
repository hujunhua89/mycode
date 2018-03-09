package com.example.modules.media.service;

import java.util.List;

import com.example.modules.media.entity.LocalVideo;

public interface ILocalFileService {
	
	void doInitLocalVideo();
	
	List<LocalVideo> queryLocalVideo(LocalVideo video);

}
