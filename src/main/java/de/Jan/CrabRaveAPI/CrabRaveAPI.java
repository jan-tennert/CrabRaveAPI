package de.Jan.CrabRaveAPI;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CrabRaveAPI {
    private String ffmpeg;
    private String video = getClass().getResource("crab.mp4").getPath();
    private String font = getClass().getResource("font.ttf").getPath();
    private File lastOutput;

    public CrabRaveAPI(String ffmpeg_path) {
        this.ffmpeg = ffmpeg_path;
        video = video.substring(1);
        font = font.substring(1);
    }

    public File addText(String text, String color, File output, boolean printOutput) throws IOException {
        if(color == null) color = "white";
        String command = String.format("%s -i \"%s\" -vf drawtext=\"fontfile=%s: \\text='%s': fontcolor=%s: fontsize=24: \\boxborderw=5: x=(w-text_w)/2: y=(h-text_h)/2\" -codec:a copy \"%s\"", ffmpeg, video, font, text, color, output.getAbsolutePath());
        if(printOutput) {
            System.out.println(command);
        }
        Runtime.getRuntime().exec(command);
        lastOutput = output;
        return output;
    }

    public boolean isDone() throws IOException {
        if(lastOutput != null && lastOutput.exists() && Files.size(Paths.get(lastOutput.toURI())) >= 2480000) {
            return true;
        } else {
            return false;
        }
    }
}
