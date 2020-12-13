package program.currencyConverterAPI.services;

import org.springframework.stereotype.Service;
import program.currencyConverterAPI.interfaces.Printable;

import java.util.ArrayList;

@Service
public class FilePrinterService implements Printable {
    private static FilePrinterService instance = null;

    public static FilePrinterService getInstance() {
        if (instance == null)
            instance = new FilePrinterService();

        return instance;
    }

    @Override
    public void printCurrencyValues(ArrayList<Float> values) {
        for (float value : values)
            System.out.printf("%.2f\n", value);
    }

    private FilePrinterService() {}
}
