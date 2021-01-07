package program.currencyConverterAPI.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import program.currencyConverterAPI.interfaces.CurrencyPrinter;

import java.util.ArrayList;

@Service
@Scope("singleton")
public class CurrencyPrinterService implements CurrencyPrinter {
    @Override
    public void printCurrencyValues(ArrayList<Float> values) {
        values.forEach(value -> System.out.printf("%.2f\n", value));
    }
}
