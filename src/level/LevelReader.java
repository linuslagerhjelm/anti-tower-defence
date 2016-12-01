package level;

import org.xml.sax.SAXException;


import javax.xml.XMLConstants;
import javax.xml.validation.Validator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * Author: Linus Lagerhjelm
 * File: LevelReader
 * Created: 16-11-28
 * Description: Validates and parses an XML-file containing levels. Supports
 * running on its own thread. Results can be acquired through the ParseResult
 * callback interface.
 */
public class LevelReader implements Runnable {
    private File file;
    private ParseResult callback;
    private LevelXMLHandler handler;

    /**
     * Creates a new LevelReader for a level file
     * @param fileName name of file to read (not path)
     * @param schema name of schema file (not path)
     * @param callback callback interface
     */
    public LevelReader(String fileName, String schema, ParseResult callback) {
        String fullPath = getClass().getResource("/" + fileName).getFile();
        file = new File(fullPath);
        this.callback = callback;
        handler = new LevelXMLHandler(this.callback);
        validate(schema);
    }

    /**
     * Validate the XML-file against the provided Schema.
     * @param schemaName name of the schema file
     */
    private void validate(String schemaName) {
        // Set the validation schema
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(
                    getClass().getResource("/" + schemaName).getFile()));

            //saxParserFactory.setSchema(schema);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));

        } catch (SAXException | IOException e) {
            callback.onError(e);
        }

    }

    @Override
    public void run() {
        try {
            // Create the sax parser
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            // Parse
            saxParser.parse(file, handler);

        } catch (IOException | ParserConfigurationException | SAXException e) {
            callback.onError(e);
        }
    }
}
