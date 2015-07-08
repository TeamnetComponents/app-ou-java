package ro.teamnet.ou.domain.neo;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class    Person {
    @GraphId
    private Long id;
    @GraphProperty(propertyName = "name", defaultValue = "")
    private String name;
    @GraphProperty(propertyName = "born", defaultValue = "")
    private Integer born;

    @RelatedTo(type="ACTED_IN", direction = Direction.OUTGOING)
    private Set<Movie> actedInMovies;

    @RelatedTo(type="DIRECTED", direction = Direction.OUTGOING)
    private Set<Movie> directedMovies;

    @RelatedTo(type="REVIEWED",direction = Direction.OUTGOING)
    private Set<Movie> reviewedMovies;



    @RelatedTo(type="FOLLOWS",direction = Direction.INCOMING)
    private Set<Person> followers;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBorn() {
        return born;
    }

    public void setBorn(Integer born) {
        this.born = born;
    }

    public Set<Movie> getActedInMovies() {
        return actedInMovies;
    }

    public void setActedInMovies(Set<Movie> actedInMovies) {
        this.actedInMovies = actedInMovies;
    }

    public Set<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(Set<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public Set<Movie> getReviewedMovies() {
        return reviewedMovies;
    }

    public void setReviewedMovies(Set<Movie> reviewedMovies) {
        this.reviewedMovies = reviewedMovies;
    }

    public Set<Person> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Person> followers) {
        this.followers = followers;
    }
}
