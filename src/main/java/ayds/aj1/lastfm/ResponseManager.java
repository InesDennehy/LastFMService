package ayds.aj1.lastfm;

public interface ResponseManager {
    Artist getArtistCardFromResponse(String responseBody) throws Exception;
}
