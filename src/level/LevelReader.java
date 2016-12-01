package level;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Author: Linus Lagerhjelm
 * File: LevelReader
 * Created: 16-11-28
 * Description:
 */
public class LevelReader implements Runnable {
    private File file;
    private ParseResult callback;
    private LevelXMLHandler handler;

    public LevelReader(String fileName, ParseResult callback) {
        String fullPath = getClass().getResource("/" + fileName).getFile();
        file = new File(fullPath);
        this.callback = callback;
        handler = new LevelXMLHandler(this.callback);

    }

    @Override
    public void run() {
        try {
            // Create the sax parser
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();



            // Parse
            saxParser.parse(file, handler);

        } catch (IOException | ParserConfigurationException |SAXException e) {
            callback.receiveResult(null);
        }
    }
}
