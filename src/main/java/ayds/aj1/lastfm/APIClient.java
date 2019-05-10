package ayds.aj1.lastfm;

public interface APIClient {
    Artist getArtistFromResponse(String artistName) throws Exception;
}
