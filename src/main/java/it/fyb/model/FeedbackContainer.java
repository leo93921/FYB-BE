package it.fyb.model;

import java.util.ArrayList;
import java.util.List;

public class FeedbackContainer {

    private List<FeedbackCount> counts = new ArrayList<>();
    private List<Feedback> feedback = new ArrayList<>();

    public List<FeedbackCount> getCounts() {
        return counts;
    }

    public void setCounts(List<FeedbackCount> counts) {
        this.counts = counts;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

}
