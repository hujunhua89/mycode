package com.example.modules.media.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/media")
public class PictureController {

	@RequestMapping("/picture")
	@ResponseBody
	public String getPicId() {
		return "";
	}
}
