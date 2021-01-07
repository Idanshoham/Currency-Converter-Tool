package program.currencyConverterAPI.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import program.currencyConverterAPI.interfaces.CurrencyConverter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static program.constants.Constants.CURRENCY_CONVERTER_API_URL;

@Service
@Scope("singleton")
public class CurrencyConverterService implements CurrencyConverter {
    public float getConversionRate(String baseCurrency, String symbolCurrency) throws IOException, InterruptedException, JSONException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CURRENCY_CONVERTER_API_URL + "?base=" + baseCurrency + "&symbols=" + symbolCurrency))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return (float) new JSONObject(response.body()).getJSONObject("rates").getDouble(symbolCurrency);
    }
}
