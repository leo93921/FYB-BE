package it.fyb.rs.impl;

import it.fyb.Utlis.Messages;
import it.fyb.Utlis.Types;
import it.fyb.Utlis.Utils;
import it.fyb.dao.MediaManagementDAO;
import it.fyb.model.Media;
import it.fyb.model.UploadedMedia;
import it.fyb.rs.interfaces.IMediaManagement;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.util.List;

public class MediaManagement implements IMediaManagement {

    @Override
    public List<Media> getMediaFiles(Integer userId, String type) throws Exception {
        if (!type.equals(Types.AUDIO_FILES) && !type.equals(Types.IMAGE_FILES)){
            throw new Exception("Files not supported!");
        }
        return MediaManagementDAO.getMedia(userId, type);
    }

    public boolean saveMediaFile(InputStream inputStream,
                                 FormDataContentDisposition mediaInfo,
                                 String title,
                                 Integer userId,
                                 String mimeType) throws Exception {
        UploadedMedia media = new UploadedMedia();
        media.setDataStream(inputStream);
        media.setFileName(mediaInfo.getFileName());
        media.setSize(mediaInfo.getSize());
        media.setMimeType(mimeType);
        media.setName(title);
        media.setUserId(userId);

        media = Utils.saveFile(media);
        if (media.getOutcome().equals(Messages.KO_OUTCOME)) {
            return false;
        }
        int id = MediaManagementDAO.saveMediaFile(media);
        System.out.print(id);
        return true;
    }

    @Override
    public boolean deleteMedia(Integer mediaId) throws Exception {
        // TODO remove file
        Media media = MediaManagementDAO.getMediaById(mediaId);
        Utils.deleteMediaFile(media);
        return MediaManagementDAO.deleteMedia(mediaId);
    }
}
