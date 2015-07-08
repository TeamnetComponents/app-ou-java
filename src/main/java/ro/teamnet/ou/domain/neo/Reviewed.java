package ro.teamnet.ou.domain.neo;

import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity
public class Reviewed {

    @StartNode
    @Fetch
    private Person reviewer;

    @EndNode
    @Fetch
    private Movie child;



    @GraphId
    private Long id;

    private String summary;

    private Integer rating;

    public Person getReviewer() {
        return reviewer;
    }

    public void setReviewer(Person reviewer) {
        this.reviewer = reviewer;
    }

    public Movie getChild() {
        return child;
    }

    public void setChild(Movie child) {
        this.child = child;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
