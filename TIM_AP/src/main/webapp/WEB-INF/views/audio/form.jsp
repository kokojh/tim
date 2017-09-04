<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="/TIM_AP_WEB/resources/js/audio/audioplay.js"></script>
	<script src="/TIM_AP_WEB/resources/js/audio/main.js"></script>
	
<script>

//날짜+요일만들기
var d = new Date();
var today = getFormatDate(d);

//날짜 포멧 변환
function getFormatDate(date){
	var year = date.getFullYear();                                 //yyyy
	var month = (1 + date.getMonth());                     //M
	month = month >= 10 ? month : '0' + month;     // month 두자리로 저장
	var day = date.getDate();                                        //d
	day = day >= 10 ? day : '0' + day;                            //day 두자리로 저장
	return  year + '-' + month + '-' + day;
}

// 날짜 패턴
var datePattern = /[0-9]{4}-[0-9]{2}-[0-9]{2}/;



$(document).ready(function() {
	
	//날짜 정보 입력
	$('input[name="date"]').val(today);
	
	//녹음기 숨기기
	$('#voiceRecorder').hide();
	
	
	$('#addFile').click(function() {
		var fileIndex = $('#fileListTable tr').children().length;      
		$('#fileListTable').append('<tr><td>'+'<input type="file" name="multipartFile" required="required" />'+'</td></tr>');
    });
    
    $('#holdConfbtn').click(function() {
    	var confTitle = $('#title').val(); 
    	var date = $('#date').val(); 
    	if(confTitle == null ||  confTitle == "" || date == null || date =="") {
    		alert("회의 정보를 입력하세요")
    	}else{
    		$('#voiceRecorder').show();
    	}
    });
    
    $('#closeConf').click(function() {
    	$('#title').val("");
    	location.reload();
    });
    
});
	//작성자 : 홍기훈 오디오 녹음을 위한객체
	
	  var WORKER_PATH = '/TIM_AP_WEB/resources/js/audio/recorderWorker.js';

	  var Recorder = function(source, cfg){
		    var config = cfg || {};
		    var bufferLen = config.bufferLen || 4096;
		    this.context = source.context;
		    if(!this.context.createScriptProcessor){
		       this.node = this.context.createJavaScriptNode(bufferLen, 2, 2);
		    } else {
		       this.node = this.context.createScriptProcessor(bufferLen, 2, 2);
		    }
		   
		    var worker = new Worker(config.workerPath || WORKER_PATH);
		    worker.postMessage({
		      command: 'init',
		      config: {
		        sampleRate: this.context.sampleRate
		      }
		    });
		    var recording = false,
		      currCallback;

		    this.node.onaudioprocess = function(e){
		      if (!recording) return;
		      worker.postMessage({
		        command: 'record',
		        buffer: [
		          e.inputBuffer.getChannelData(0),
		          e.inputBuffer.getChannelData(1)
		        ]
		      });
		    }

		    this.configure = function(cfg){
		      for (var prop in cfg){
		        if (cfg.hasOwnProperty(prop)){
		          config[prop] = cfg[prop];
		        }
		      }
		    }

		    this.record = function(){
		      recording = true;
		    }

		    this.stop = function(){
		      recording = false;
		    }

		    this.clear = function(){
		      worker.postMessage({ command: 'clear' });
		    }

		    this.getBuffers = function(cb) {
		      currCallback = cb || config.callback;
		      worker.postMessage({ command: 'getBuffers' })
		    }

		    this.exportWAV = function(cb, type){
		      currCallback = cb || config.callback;
		      type = type || config.type || 'audio/wav';
		      if (!currCallback) throw new Error('Callback not set');
		      worker.postMessage({
		        command: 'exportWAV',
		        type: type
		      });
		    }

		    this.exportMonoWAV = function(cb, type){
		      currCallback = cb || config.callback;
		      type = type || config.type || 'audio/wav';
		      if (!currCallback) throw new Error('Callback not set');
		      worker.postMessage({
		        command: 'exportMonoWAV',
		        type: type
		      });
		    }

		    worker.onmessage = function(e){
		      var blob = e.data;
		      currCallback(blob);
		    }

		    source.connect(this.node);
		    this.node.connect(this.context.destination);   // if the script node is not connected to an output the "onaudioprocess" event is not triggered in chrome.
		  };

		  Recorder.setupDownload = function(blob, filename){
		    var url = (window.URL || window.webkitURL).createObjectURL(blob);
		    var link = document.getElementById("save");
		    link.href = url;
		    link.download = filename || 'output.wav';
//			alert(url);
		    uploadWav(blob);
		  }
		  
		  
		  function uploadWav(blob){
				var confTitle = $('#title').val(); 
		    	var date = $('#date').val(); 
		  		var formData = new FormData();
		  		formData.append("multipartFile", blob,"temp.wav");
		  		formData.append('title', confTitle);
		  		formData.append('date', date);
		  		
					$.ajax({
						type : 'POST',
						url : 'upload',
						data : formData,
						processData:false,
						contentType:false,
						enctype: "multipart/form-data",
						success : function(result){
								alert("성공적으로 회의가 저장되었습니다.")
						}
					});
			  
			 }
		  
		  window.Recorder = Recorder;
		  
	
	</script>
	<style>
	
	html { overflow: hidden; }
	#voiceRecorder { 
/* 		font: 14pt Arial, sans-serif;  */
		background: lightgrey;
		display: flex;
		flex-direction: row;
		align-items: center;
		height: 15vh;
		width: 50vh;
		margin: 0 0;
	}
	canvas { 
		display: inline-block; 
		background: #202020; 
		width: 95%;
		height: 45%;
		box-shadow: 0px 0px 10px blue;
	}
	#controls {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-around;
		height: 70%;
		width: 10%;
	}
	#record { height: 5vh; }
	#record.recording { 
		background: red;
		background: -webkit-radial-gradient(center, ellipse cover, #ff0000 0%,lightgrey 75%,lightgrey 100%,#7db9e8 100%); 
		background: -moz-radial-gradient(center, ellipse cover, #ff0000 0%,lightgrey 75%,lightgrey 100%,#7db9e8 100%); 
		background: radial-gradient(center, ellipse cover, #ff0000 0%,lightgrey 75%,lightgrey 100%,#7db9e8 100%); 
	}
	#save, #save img { height: 5vh; }
	#save { opacity: 0.25;}
	#save[download] { opacity: 1;}
	#viz {
		height: 80%;
		width: 60%;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
	}
	@media (orientation: landscape) {
		body { flex-direction: row;}
		#controls { flex-direction: column; height: 40%; width: 30%;}
		#viz { height: 70%; width: 80%;}
	}

	</style>	

<div id="content">
	<form action="upload" method="post" enctype="multipart/form-data">
	    <input id="addFile" type="button" value="파일추가" />&nbsp;&nbsp;<input type="submit" value="완료" /><input type="reset" value="취소"><br /><br />    
	    <table id="fileListTable">
        	<tr><td><input type="file" name="multipartFile" required="required" /></td></tr>        
    	</table>
	</form>
</div>

<div id="conferenceInfo">

	회의명 : <input type="text" id="title" name="title">  날짜 : <input type="text" id="date" name="date" value="date" readonly><br />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="holdConfbtn" value="회의개설">

</div>


<div id="voiceRecorder">
녹음영역
	<div id="viz">
		<canvas id="analyser" width="300" height="200"></canvas>
		<canvas id="wavedisplay" width="300" height="200"></canvas>
	</div>
	<div id="controls">
		<img id="record" src="/TIM_AP_WEB/resources/images/mic1.png" onclick="toggleRecording(this);" width="50px" height="50px"> 
		<a id="save" href="#">
		<img src="/TIM_AP_WEB/resources/images/save.svg" width="50px" height="50px">
		</a>
	</div>
	<input type="button" id="closeConf" value="회의종료">
</div>





