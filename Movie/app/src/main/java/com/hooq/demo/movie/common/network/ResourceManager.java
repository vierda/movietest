package com.hooq.demo.movie.common.network;

public class ResourceManager {

    private static ResourceManager _instance = null;
    private String protocol = "https";
    private String serverName = "api.themoviedb.org";
    private String version = "3";
    private String apiKey = "73a8fd2bb070bcdffa612263d18136b2";

    public static ResourceManager getInstance() {
        if (_instance == null)
            _instance = new ResourceManager();

        return _instance;
    }

    private ResourceManager() {
        // Instantiation disabled
    }

    public String getHost() {
        StringBuilder builder = new StringBuilder(1024);

        builder.append(protocol)
                .append("://")
                .append(serverName)
                .append("/")
                .append(version);

        return builder.toString();
    }

    public String getMovieResults(int page) {
        return getHost() + "/movie/now_playing?api_key=" + apiKey + "&language=en-US&page=" + page;
    }

    public String getSimilarMovies(int movieId, int page) {
        return getHost()+ "/movie/" +movieId + "/similar?api_key=" + apiKey + "&language=en-US&page=" + page;
    }

    public String getImage(String imagePath) {
        return protocol+"://image.tmdb.org/t/p/w500"+imagePath;
    }
}
