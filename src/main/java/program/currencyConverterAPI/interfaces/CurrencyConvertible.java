package program.currencyConverterAPI.interfaces;

import org.json.JSONException;
import java.io.IOException;

public interface CurrencyConvertible {
    float getSpecificRateBetween(String baseCurrency, String symbolCurrency) throws IOException, InterruptedException, JSONException;
}
