package com.tim.ap.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tim.ap.CommendProcess;
import com.tim.ap.OutputString;
import com.tim.ap.entity.AudioEntity;
import com.tim.ap.entity.ConferenceEntity;
import com.tim.ap.service.AudioService;
import com.tim.ap.service.ConferenceService;
import com.tim.ap.util.FileTool;
import com.tim.ap.util.UniqueFileIdGenerator;

@Controller
@RequestMapping("/audio")
public class AudioController {
	private static final Logger logger = LoggerFactory.getLogger(AudioController.class);

	@Autowired
	private AudioService audioService;
	
	@Autowired
	private ConferenceService conferenceService;
	
	//서버 프로세스 구동에 필요한 변수
	public String filetemppath="/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/converter/wavtemp";
	public String uploadpath="/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/converter/wavfile/";
	public String downloadpath="/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/converter/wavInfo/";
	public String command="/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/converter/conv.sh";
	public String mlfpath="/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/result/TEST_MT_N1_n0.mlf2.org";
	public String run="/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/11_MT_list.dnn_gpu.sh";
	public String clean = "/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/clean.sh";

	@RequestMapping("/form")
	public ModelAndView main(Locale locale) {
		logger.info("/audio/form", locale);

		ModelAndView result = new ModelAndView();

		result.setViewName("/audio/form");
		return result;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView upload(Locale locale, @RequestParam("multipartFile") List<MultipartFile> multipartFiles, ConferenceEntity conferenceEntity) throws IOException, UnsupportedAudioFileException {
		logger.info("/audio/upload", locale);
		
		System.out.println("FileSize = "+multipartFiles.get(0).getSize()+" // FileName = "+ multipartFiles.get(0).getName());
		System.out.println(conferenceEntity);
		
		File file = convert(multipartFiles.get(0));
		
		
		 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		    AudioFormat format = audioInputStream.getFormat();
		    long audioFileLength = file.length();
		    int frameSize = format.getFrameSize();
		    float frameRate = format.getFrameRate();
		    float durationInSeconds = (audioFileLength / (frameSize * frameRate));
		    int playTime = (int)durationInSeconds;
		    System.out.println("몇초? ->"+ playTime+"초");
		
		conferenceService.insertConference(conferenceEntity);
		
		int conferenceId = conferenceService.selectConference().getId(); 
		
		try {
			if (multipartFiles != null && multipartFiles.size() != 0) {
				// get information - audio.properties
				String uploadTempRootPath = System.getProperty("catalina.home") + File.separator + "audio_temp";
				System.out.println(uploadTempRootPath);

				if (!FileTool.isExistsDirectory(uploadTempRootPath)) {
					FileTool.makeDirectory(uploadTempRootPath);
				}

				String uploadTempFilePath = uploadTempRootPath + File.separator + UniqueFileIdGenerator.getUniqueFileId();
				FileTool.makeDirectory(uploadTempFilePath);

				for (int i = 0; i < multipartFiles.size(); i++) {
					MultipartFile multipartFile = multipartFiles.get(i);

					if (!multipartFile.isEmpty()) {
						AudioEntity audioDataEntity = new AudioEntity();

						audioDataEntity.setMultipartFile(multipartFile);

						byte[] bytes = multipartFile.getBytes();

						
						
						String tempFileName = UniqueFileIdGenerator.getUniqueFileId() +"_"+ dateMaker() + ".wav" ;
						
						File uploadFile = new File(uploadTempFilePath + File.separator + tempFileName);
//						File uploadFile = new File(uploadTempFilePath + File.separator + multipartFile.getOriginalFilename());
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFile));

						stream.write(bytes);
						stream.close();

						logger.info("/audio/upload : Upload File Location : " + uploadFile.getAbsolutePath());
					} else {
						throw new FileUploadException();
					}
				}

				File uploadTempDirectory = new File(uploadTempFilePath);

				if (!uploadTempDirectory.isDirectory()) {
					throw new FileUploadException();
				}

				// get information - audio.properties
				String uploadRealRootPath = "/usr/local/ap/data/";
//				String uploadRealRootPath = "/home/speech/20161012/2016Jul06_ASR_Package_8k_DNN_support/STT/converter/wavInfo/";
//				String uploadRealRootPath = "d:/temp/";

				File[] uploadTempFileList = uploadTempDirectory.listFiles();

				for (int i = 0; i < uploadTempFileList.length; i++) {
					File uploadTempFile = uploadTempFileList[i];
					String uploadTempFileName = uploadTempFile.getName().substring(0, uploadTempFile.getName().lastIndexOf("."));
					String uploadTempFileExtention = uploadTempFile.getName() .substring(uploadTempFile.getName().lastIndexOf(".") + 1);
					String uploadRealFilePath = uploadRealRootPath;

					if ("WAV".equals(uploadTempFileExtention.toUpperCase())) {
						String uploadTempFileNameArray[] = uploadTempFileName.split("_");

						for (int j = 0; j < uploadTempFileNameArray.length - 1; j++) {
							uploadRealFilePath = uploadRealFilePath + uploadTempFileNameArray[j] + File.separator;

							if (!FileTool.isExistsDirectory(uploadRealFilePath)) {
								FileTool.makeDirectory(uploadRealFilePath);
							}
						}

						String sttPath = uploadpath;
						
						//프로세스 생성 후 클린(이전파일 데이터 삭제)
						CommendProcess cp = new CommendProcess();
						cp.commend(clean);

						FileTool.copy(uploadTempFile.getAbsolutePath(), sttPath + uploadTempFile.getName());
						FileTool.copy(uploadTempFile.getAbsolutePath(), uploadRealRootPath + uploadTempFile.getName());

						logger.info("/audio/upload : Move File Location : " + uploadRealFilePath + uploadTempFile.getName());

						cp.commend(command);
						cp.commendSTT(run);
						
						OutputString out = new OutputString();
						String output = out.output(mlfpath);
						
						System.out.println(output);
						
						// test data
						AudioEntity audioEntity = new AudioEntity();
						
						String playTimeFormatter = String.format("%06d", playTime);
						
						audioEntity.setC_id(conferenceId);
						audioEntity.setM_email("sysadmin");
//						audioEntity.setM_email(uploadTempFileNameArray[uploadTempFileNameArray.length - 2]);
//						audioEntity.setTime_beg(uploadTempFileNameArray[uploadTempFileNameArray.length - 1].split("-")[0]);
						audioEntity.setTime_beg("000000");
//						audioEntity.setTime_end(uploadTempFileNameArray[uploadTempFileNameArray.length - 1].split("-")[1]);
						audioEntity.setTime_end(playTimeFormatter);
						audioEntity.setAd_text(output);
						audioEntity.setAd_wav_filepath(uploadTempFile.getName());
						audioEntity.setAd_download_cnt(0);

						audioService.insertAudio(audioEntity);
					}
				}
			}
		} catch (FileUploadException fe) {
			fe.printStackTrace();
			logger.error("/audio/upload", "File Upload Error");
		} catch (Exception e) {
			logger.error("/audio/upload", "Error");
			e.printStackTrace();
		}

		return done(locale);
	}

	public ModelAndView done(Locale locale) {
		logger.info("/audio/done", locale);
		List<AudioEntity> AudioDataList = null;

		ModelAndView result = new ModelAndView();

		result.addObject("result", AudioDataList);
		result.setViewName("/audio/done");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(Locale locale, @RequestParam("c_id") int c_id) {
		logger.info("/audio/list", locale);

		AudioEntity audioEntity = new AudioEntity();
		audioEntity.setC_id(c_id);

		ModelAndView result = new ModelAndView();

		List<AudioEntity> audioList = audioService.getAudioList(audioEntity);

		result.addObject("result", audioList);
		result.setViewName("/audio/list");

		return result;
	}

	@RequestMapping(value = "/list", produces="text/plain;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String listGet(Locale locale, @RequestParam("c_id") int c_id) {
		logger.info("/audio/list", locale);

		AudioEntity audioEntity = new AudioEntity();
		audioEntity.setC_id(c_id);

		List<AudioEntity> audioList = audioService.getAudioList(audioEntity);

		return new com.google.gson.Gson().toJson(audioList);
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(Locale locale, HttpServletResponse response, @RequestParam("id") int id) throws IOException {
		logger.info("/audio/download", locale);

		AudioEntity audioEntity = new AudioEntity();
		audioEntity.setId(id);

		List<AudioEntity> audioList = audioService.getAudioList(audioEntity);

		audioEntity = audioList.get(0);
		audioEntity.setAd_download_cnt(1);

		audioService.updateAudio(audioEntity);

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + audioEntity.getAd_wav_filepath() + "\"");

		//String filePath = "C:\\Users\\JunHyuk\\Desktop\\wav_file\\wav_data";
		String filePath = "/usr/local/ap";

		OutputStream outPutStream = response.getOutputStream();
		FileInputStream fileInputStream = new FileInputStream(filePath + File.separator + audioEntity.getAd_wav_filepath());

		int n = 0;
		byte[] b = new byte[512];

		while ((n = fileInputStream.read(b)) != -1) {
			outPutStream.write(b, 0, n);
		}

		fileInputStream.close();
		outPutStream.close();
	}
	
	public String dateMaker(){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		String mTime = mSimpleDateFormat.format ( currentTime );
		return mTime;
	}
	
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
}
