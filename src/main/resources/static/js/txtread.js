(function() {
	'use strict'
	var Util = (function() {
		var prefix = 'html5_reader_'
		// 读取
		var StorageGetter = function(key) {
			return localStorage.getItem(prefix + key);
		}
		// 存储
		var StorageSetter = function(key, val) {
			return localStorage.setItem(prefix + key, val);
		}
		var getBSONP = function(url, callback) {
			return $.jsonp({
				url : url,
				cache : true,
				callback : 'duokan_fiction_chapter',
				success : function(result) {
					var data = $.base64.decode(result);
					var json = decodeURIComponent(escape(data));
					callback(json);
				}
			})
		}
		return {
			getBSONP : getBSONP,
			StorageGetter : StorageGetter,
			StorageSetter : StorageSetter
		}
	})();

	var Dom = {
		top_nav : $('#topid'),
		bottom_nav : $('#bottomid'),
		font_container : $('.fc'),
		font_button : $('#typeface_button'),
		ift : $('#ift'),
		night_icon : $('#night_icon'),
		day_icon : $('#day_icon'),
		body : $('body')
	}

	var Win = $(window);
	var Doc = $(document);
	var readerModel;
	var readerUI;
	var RootContainer = $('#ctid');
	var DayorNight = Util.StorageGetter('day_night');
	DayorNight = parseInt(DayorNight);
	var initFontSize = Util.StorageGetter('font_size');
	initFontSize = parseInt(initFontSize);
	var bkd = Util.StorageGetter('bkd');
	if (!initFontSize) {
		initFontSize = 14;
	}
	RootContainer.css('font-size', initFontSize);

	switch (DayorNight) {
	case 0:
		Dom.night_icon.show();
		Dom.day_icon.hide();
		Dom.body.css('background', '#e9dfc7');
		break;
	case 1:
		Dom.night_icon.hide();
		Dom.day_icon.show();
		Dom.body.css("background-color", 'rgb(15,20,16)');
	default:
		break;
	}

	if (!bkd) {
		Dom.body.css('background', '#e9dfc7');
	}
	Dom.body.css('background', bkd);

	function main() {
		// todo 整个项目的入口函数
		readerModel = ReaderModel();
		readerUI = ReaderBaseFrame(RootContainer);
		readerModel.init(function(data) {
			readerUI(data);
		});
		EventHanlder();
	}

	function ReaderModel() {
		// todo 实现和阅读器相关的数据交互的方法
		var Chapter_id;
		var ChapterTotal;
		// 初始化
		var init = function(UIcallback) {

			getFictionInfo(function() {
				getCurChapterContent(Chapter_id, function(data) {
					// todo
					UIcallback && UIcallback(data)
				});
			})

			/*
			 * //ES6方式 getFictionInfoPromise().then(function(d){ return
			 * getCurChapterContentPromise(); }).then(function(data){ UIcallback &&
			 * UIcallback(data); });
			 */
		}
		/*
		 * //ES6方式 var getFictionInfoPromise = function(){ return new
		 * Promise(function(resolve,reject){
		 * $.get('data/chapter.json',function(data){ //todo 获得章节信息之后的回调
		 * if(data.result == 0){ Chapter_id =
		 * Util.StorageGetter('lase_chapter_id'); if(Chapter_id == null){
		 * Chapter_id = data.chapters[1].chapter_id; } ChapterTotal =
		 * data.chapters.length; resolve(); }else{ reject(); } },'json'); }); }
		 */

		var getFictionInfo = function(callback) {
			$.get('/txt/list', function(data) {
				// todo 获得章节信息之后的回调
				Chapter_id = Util.StorageGetter('lase_chapter_id');
				if (Chapter_id == null||isNaN(Chapter_id)) {
					Chapter_id = data.chapters[0].chapter_id;
				}
				ChapterTotal = data.chapters.length;
				callback && callback();
			}, 'json');
		}

		/*
		 * //ES6方式 var getCurChapterContentPromise = function(){ return new
		 * Promise(function(resolve,reject){ $.get('data/data' + Chapter_id +
		 * '.json',function(data){ //todo if(data.result == 0){ var url =
		 * data.jsonp; Util.getBSONP(url,function(data){ resolve(data); });
		 * }else{ reject({msg:'fail'}); } },'json'); }); }
		 */

		var getCurChapterContent = function(chapter_id, callback) {
			$.get('/txt/preread/' + chapter_id, function(data) {
				// todo
				if (data.result == 0) {
					var url = data.jsonp;
					Util.getBSONP(url, function(data) {
						callback && callback(data);
					});
				}
			}, 'json');
		}

		// 上一页
		var prevChapter = function(UIcallback) {
			Chapter_id = parseInt(Chapter_id, 10);
			if (Chapter_id <= 1) {
				Chapter_id = 1;
			} else {
				Chapter_id -= 1;
				getCurChapterContent(Chapter_id, UIcallback);
				Util.StorageSetter('lase_chapter_id', Chapter_id);
			}
		}
		// 下一页
		var nextChapter = function(UIcallback) {
			Chapter_id = parseInt(Chapter_id, 10);
			if (Chapter_id >= ChapterTotal) {
				Chapter_id = ChapterTotal;
			} else {
				Chapter_id += 1;
				getCurChapterContent(Chapter_id, UIcallback);
				Util.StorageSetter('lase_chapter_id', Chapter_id);
			}
		}
		return {
			init : init,
			prevChapter : prevChapter,
			nextChapter : nextChapter
		}
	}

	function ReaderBaseFrame(container) {
		// todo 渲染基本的UI结构
		function parseChapterData(jsonData) {
			var jsonObj = JSON.parse(jsonData);
			var html = '<h4>' + jsonObj.t + '</h4>';
			for (var i = 0; i < jsonObj.p.length; i++) {
				html += '<p>' + jsonObj.p[i] + '</p>';
			}
			return html;
		}
		return function(data) {
			container.html(parseChapterData(data));
		}
	}

	function EventHanlder() {
		// todo 交互的事件绑定
		$('#action_mid').click(function() {
			if (Dom.top_nav.css('display') == 'none') {
				Dom.bottom_nav.show();
				Dom.top_nav.show();
				Dom.ift.attr('class', 'icon-ft');
			} else {
				Dom.bottom_nav.hide();
				Dom.top_nav.hide();
				Dom.font_container.hide();
			}
		});

		Dom.font_button.click(function() {
			// todo 唤起字体面板
			var bt = Util.StorageGetter('btn-id');
			$('#' + bt).trigger("click");
			if (Dom.font_container.css('display') == 'none') {
				Dom.font_container.show();
				Dom.ift.attr('class', 'current');
			} else {
				Dom.font_container.hide();
				Dom.ift.attr('class', 'icon-ft');
			}
		})

		$('.bk-container').click(function() {
			// todo 触发背景切换的事件
			var btnid = $(this).attr("id");
			Util.StorageSetter('btn-id', btnid);
			switch (btnid) {
			case "btna":
				Dom.body.css('background', '#f7eee5');
				$('#btna-a').show();
				$('#btnb-b').hide();
				$('#btnc-c').hide();
				$('#btnd-d').hide();
				$('#btne-e').hide();
				Util.StorageSetter('bkd', '#f7eee5');
				break;
			case "btnb":
				Dom.body.css('background', '#e9dfc7');
				$('#btna-a').hide();
				$('#btnb-b').show();
				$('#btnc-c').hide();
				$('#btnd-d').hide();
				$('#btne-e').hide();
				Util.StorageSetter('bkd', '#e9dfc7');
				break;
			case "btnc":
				Dom.body.css('background', '#a4a4a4');
				$('#btna-a').hide();
				$('#btnb-b').hide();
				$('#btnc-c').show();
				$('#btnd-d').hide();
				$('#btne-e').hide();
				Util.StorageSetter('bkd', '#a4a4a4');
				break;
			case "btnd":
				Dom.body.css('background', '#cdefce');
				$('#btna-a').hide();
				$('#btnb-b').hide();
				$('#btnc-c').hide();
				$('#btnd-d').show();
				$('#btne-e').hide();
				Util.StorageSetter('bkd', '#cdefce');
				break;
			case "btne":
				Dom.body.css('background', '#283548');
				$('#btna-a').hide();
				$('#btnb-b').hide();
				$('#btnc-c').hide();
				$('#btnd-d').hide();
				$('#btne-e').show();
				Util.StorageSetter('bkd', '#283548');
				break;
			default:
				break;
			}
		})

		$('#night_icon').click(function() {
			// todo 触发夜间模式
			Dom.night_icon.hide();
			Dom.day_icon.show();
			Util.StorageSetter('day_night', 1);
			Dom.body.css("background-color", 'rgb(15,20,16)');
		})

		$('#day_icon').click(function() {
			// todo 触发白天模式
			Dom.night_icon.show();
			Dom.day_icon.hide();
			Util.StorageSetter('day_night', 0);
			Dom.body.css('background', '#e9dfc7');
		})

		$('#large-font').click(function() {
			// todo 字体放大
			if (initFontSize > 20) {
				return;
			}
			initFontSize += 1;
			RootContainer.css('font-size', initFontSize);
			Util.StorageSetter('font_size', initFontSize);
		})

		$('#small-font').click(function() {
			// todo 字体缩小
			if (initFontSize < 12) {
				return;
			}
			initFontSize -= 1;
			RootContainer.css('font-size', initFontSize);
			Util.StorageSetter('font_size', initFontSize);
		})

		Win.scroll(function() {
			Dom.bottom_nav.hide();
			Dom.top_nav.hide();
			Dom.font_container.hide();
			Dom.ift.attr('class', 'icon-ft');
		})

		$('#prev').click(function() {
			// todo 获得章节的翻页数据->把数据拿出来渲染
			readerModel.prevChapter(function(data) {
				readerUI(data);
			});
		});

		$('#next').click(function() {
			readerModel.nextChapter(function(data) {
				readerUI(data);
			});
		});
	}

	main();

})();