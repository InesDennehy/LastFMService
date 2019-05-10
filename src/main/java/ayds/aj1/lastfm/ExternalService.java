package ayds.aj1.lastfm;

public interface ExternalService {
    Artist getArtist(String artistName) throws Exception;
}
