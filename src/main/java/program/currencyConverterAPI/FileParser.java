package program.currencyConverterAPI;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import program.currencyConverterAPI.services.CurrencyConverterService;
import program.currencyConverterAPI.services.CurrencyPrinterService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import static program.constants.Constants.FILES_PATH;
import static program.constants.Constants.FILE_EXTENSION_TXT;

@Controller
public class FileParser {

    private final String fileName;
    @Autowired private final CurrencyConverterService currencyConverterService;
    @Autowired private final CurrencyPrinterService filePrinterService;
    private float rate;

    public FileParser(@Value("${file.name}") String fileName, CurrencyConverterService currencyConverterService, CurrencyPrinterService filePrinterService) {
        this.fileName = fileName;
        this.currencyConverterService = currencyConverterService;
        this.filePrinterService = filePrinterService;
    }

    public ArrayList<Float> getConvertedFromCurrencyValues() throws IOException, InterruptedException, JSONException {
        String fromCurrencyType = "", toCurrencyType = "";
        Path filePath = Paths.get(FILES_PATH, getFileName() + FILE_EXTENSION_TXT);
        var fileData = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        System.out.println(fileData);

        fromCurrencyType = fileData.get(0);
        toCurrencyType = fileData.get(1);
        this.rate = this.currencyConverterService.getConversionRate(fromCurrencyType, toCurrencyType);

        return (ArrayList<Float>) fileData.stream().filter(data -> data.matches("[+-]?([0-9]*[.])?[0-9]+"))
                .map(Float::parseFloat)
                .map(fromValue -> fromValue * this.rate)
                .collect(Collectors.toList());
    }

    public String getFileName() {
        return this.fileName;
    }

    public CurrencyPrinterService getFilePrinterService() {
        return filePrinterService;
    }

    public float getRate() {
        return this.rate;
    }

}
