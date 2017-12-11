package org.newdawn.spaceinvaders;

import sun.audio.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {
	static Clip clip;

	static public void Play(String name) {
		try {
			File Filename = new File(name);
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;

			stream = AudioSystem.getAudioInputStream(Filename);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();

		} catch (Exception e) {
		}
	}

	static public void Stop() {
		clip.stop();
	}

	public static void bgm(String fileName) throws FileNotFoundException, IOException {

		ContinuousAudioDataStream loop = null;
		InputStream in = null;
		
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}
		
		try {
			AudioStream s = new AudioStream(in);
			AudioData audiodata = s.getData();
			loop = new ContinuousAudioDataStream(audiodata);
			AudioPlayer.player.start(loop);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
