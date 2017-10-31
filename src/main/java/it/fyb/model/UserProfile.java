package it.fyb.model;

import java.util.List;

public class UserProfile {

    private String id;
    private String name;
    private String description;
    private String phone;
    private String email;
    private String priceBand;
    private String youtube;
    private Integer feedbackCount;
    private float feedbackValue;
    private List<Media> music;
    private List<Media> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPriceBand() {
        return priceBand;
    }

    public void setPriceBand(String priceBand) {
        this.priceBand = priceBand;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public Integer getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(Integer feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public float getFeedbackValue() {
        return feedbackValue;
    }

    public void setFeedbackValue(float feedbackValue) {
        this.feedbackValue = feedbackValue;
    }

    public List<Media> getMusic() {
        return music;
    }

    public void setMusic(List<Media> music) {
        this.music = music;
    }

    public List<Media> getImages() {
        return images;
    }

    public void setImages(List<Media> images) {
        this.images = images;
    }
}
