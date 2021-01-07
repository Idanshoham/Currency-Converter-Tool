package program.currencyConverterAPI.interfaces;

import org.json.JSONException;
import java.io.IOException;

public interface CurrencyConverter {
    float getConversionRate(String baseCurrency, String symbolCurrency) throws IOException, InterruptedException, JSONException;
}
