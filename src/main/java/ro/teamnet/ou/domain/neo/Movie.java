package ro.teamnet.ou.domain.neo;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

@NodeEntity
public class Movie {

    @GraphId
    private Long id;


    @GraphProperty(propertyName = "title", defaultValue = "")
    private String title;


    @GraphProperty(propertyName = "released", defaultValue = "")
    private Long released;

    @GraphProperty(propertyName = "tagline", defaultValue = "")
    private String tagline;


    @RelatedTo(type = "DIRECTED", direction = Direction.INCOMING)
    private Person director;

    @RelatedTo(type = "ACTED_IN", direction = Direction.INCOMING)
    private Set<Person> actors;


    @Query("start movie=node({self}) match movie-->genre<--similar return similar")
    private Iterable<Movie> similarMovies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public Set<Person> getActors() {
        return actors;
    }

    public void setActors(Set<Person> actors) {
        this.actors = actors;
    }

    public Iterable<Movie> getSimilarMovies() {
        return similarMovies;
    }

    public void setSimilarMovies(Iterable<Movie> similarMovies) {
        this.similarMovies = similarMovies;
    }

    public Long getReleased() {
        return released;
    }

    public void setReleased(Long released) {
        this.released = released;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}