package libraries;

public enum APIEndPoints {

    POSTSAPI("/posts"),

    ;

    private final String resource;

    APIEndPoints(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
