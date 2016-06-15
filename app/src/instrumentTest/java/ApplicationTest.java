import android.app.Application;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.example.alex.myapplication.DocumentParser;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends TestCase{

    String sampleCSVFile = "column 1, data 1.1\n" +
            "column 2, data 2.1";

    public void testDocumentParserGetLengthReturnsCorrect(){
        DocumentParser documentParser = new DocumentParser();
        documentParser.consumeDocument("column 1, data 1.1\n" +
                                       "column 2, data 2.1");

        Assert.assertEquals(2, documentParser.getDocLength());
        Log.v("Test", "the doc length is " + documentParser.getDocLength());
    }
    public final void testDocumentParserGetValueReturnsCorrect(){
        DocumentParser documentParser = new DocumentParser();
        documentParser.consumeDocument("column 1, data 1.1\n" +
                                       "column 2, data 2.1");

        Assert.assertEquals("column 1", documentParser.getValue(1, 1));
        Assert.assertEquals("data 2.1", documentParser.getValue(2, 2));

    }

    public final void testIsTrueTrue(){
        Assert.assertEquals(true, true);

    }
}