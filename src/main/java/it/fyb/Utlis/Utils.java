package it.fyb.Utlis;

import it.fyb.model.Media;
import it.fyb.model.UploadedMedia;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.naming.InitialContext;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sql.DataSource;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Map;

public class Utils {

    public static Connection getDataConnection() throws Exception {
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup("jdbc/FYB");
        return ds.getConnection();
    }

    public static void close(Connection conn) throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    public static void close(ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
    }

    public static void closeAll(Connection conn, ResultSet rs) throws Exception {
        close(conn);
        close(rs);
    }

    public static String md5Encode(String toEncode) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(toEncode.getBytes(), 0, toEncode.length());
        return (new BigInteger(1, md.digest())).toString();
    }

    public static UploadedMedia saveFile(UploadedMedia media) throws Exception {
        // Create folder name
        Calendar todayDate = Calendar.getInstance();
        String dayOfMonth = String.valueOf(todayDate.get(Calendar.DAY_OF_MONTH));
        String monthOfYear = String.valueOf(todayDate.get(Calendar.MONTH));
        String year = String.valueOf(todayDate.get(Calendar.YEAR));
        String todayFolder = year + "-" + monthOfYear + "-" + dayOfMonth;

        // Create folder
        String filePathBase = "/upload/";
        String filePath = filePathBase + todayFolder;
        File folder = new File(filePath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (!success) {
            return fillError(media, Messages.KO_OUTCOME, Messages.CANT_MKDIR);
        }

        // Write file
        String[] pieces = media.getFileName().split("[.]");
        String extension = pieces[pieces.length - 1];
        String fileName = folder + "/" + String.valueOf(todayDate.getTimeInMillis()) + "." + extension;
        File f = new File(fileName);
        if (f.exists()) {
            return fillError(media, Messages.KO_OUTCOME, Messages.FILE_ALREADY_EXISTS);
        }
        FileOutputStream out = new FileOutputStream(fileName);
        byte[] buffer = new byte[1024];
        while (media.getDataStream().read(buffer) != -1) {
            out.write(buffer);
        }
        out.flush();
        out.close();
        media.setUrl(fileName);
        if (media.getMimeType().contains(Types.AUDIO_FILES)) {
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(f);
            if (fileFormat instanceof TAudioFileFormat) {
                Map<?, ?> properties = (fileFormat).properties();
                String key = "duration";
                Long microseconds = (Long) properties.get(key);
                int seconds = (int) (microseconds / 1000000);
                media.setSize(seconds);
            }
        }
        media.setOutcome(Messages.OK_OUTCOME);
        media.setMessage("");
        return media;
    }

    private static UploadedMedia fillError(UploadedMedia media, String errorCode, String errorMessage) {
        media.setOutcome(errorCode);
        media.setMessage(errorMessage);
        return media;
    }

    public static void deleteMediaFile(Media media) throws Exception{
        File f = new File(media.getUrl());
        Files.deleteIfExists(f.toPath());
    }
}
