
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import program.currencyConverterAPI.FileParser;
import program.currencyConverterAPI.services.CurrencyConverterService;
import program.currencyConverterAPI.services.FilePrinterService;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {

    private final CurrencyConverterService currencyConverterService = CurrencyConverterService.getInstance();
    private final FilePrinterService filePrinterService = FilePrinterService.getInstance();

    @Test
    void getConvertedFromCurrencyValuesStandardFileTest() throws InterruptedException, JSONException, IOException {
        FileParser fileParser = new FileParser("Currencies", currencyConverterService, filePrinterService);
        /*
           standard file:
           USD
           ILS
           1.0
           5.0
           10.5
        */
        var values = fileParser.getConvertedFromCurrencyValues();
        assertEquals(1.0f * fileParser.getRate(), values.get(0));
        assertEquals(5.0f * fileParser.getRate(), values.get(1));
        assertEquals(10.5f * fileParser.getRate(), values.get(2));
    }

    @Test
    void getConvertedFromCurrencyValuesEmptyFileTest() throws InterruptedException, JSONException, IOException {
        FileParser fileParser = new FileParser("CurrenciesEmpty", currencyConverterService, filePrinterService);
        /*
           empty file:
           USD
           ILS
        */
        var values = fileParser.getConvertedFromCurrencyValues();
        assertTrue(values.isEmpty());
    }

    @Test
    void getFileName() {
        FileParser fileParser = new FileParser("Currencies", currencyConverterService, filePrinterService);
        assertEquals("Currencies", fileParser.getFileName());
    }
}