package ayds.aj1.lastfm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class ResponseManagerImp implements ResponseManager{

    public Artist getArtistCardFromResponse(String serviceData) throws Exception{
        NodeList images = parseResponse(serviceData).getElementsByTagName("image");
        NodeList content = parseResponse(serviceData).getElementsByTagName("content");
        NodeList name = parseResponse(serviceData).getElementsByTagName("name");
        Node extractContent = content.item(0);
        Node extractName = name.item(0);
        String bio = checkValidation(extractContent.getTextContent().replace("\\n", "\n"));
        String artistName = checkValidation(extractName.getTextContent().replace("\\n", "\n"));
        String image = checkValidation(lookForPathImage(images));
        return new Artist(artistName, bio, image);
    }

    private String checkValidation(String stringResult) throws Exception{
        String checkedStringResult = stringResult;

        if (stringResult == null || stringResult.equals(""))
            throw new Exception("Sorry, we couldn't find any information about the artist :(");

        return checkedStringResult;
    }

    private String lookForPathImage(NodeList images){
       int i=0;
       boolean found = false;
       String pathImageFromResponse = "";

       while (!found && i < images.getLength()){
           Node image = images.item(i);
           NamedNodeMap atts = image.getAttributes();
           Node size = atts.getNamedItem("size");

           if(size.getTextContent().equals("extralarge")){
               pathImageFromResponse = image.getTextContent();
               found = true;
           }
           i++;
       }
       return pathImageFromResponse;
    }

    private Element parseResponse(String responseBody){
        Document docFromResponse = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            docFromResponse= db.parse(new InputSource(new StringReader(responseBody)));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return docFromResponse.getDocumentElement();
    }
}
