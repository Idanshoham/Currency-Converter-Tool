package program;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import program.currencyConverterAPI.FileParser;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class CurrencyConverterToolApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterToolApplication.class);

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CurrencyConverterToolApplication.class, args);
		FileParser fileParser = applicationContext.getBean(FileParser.class);
		try {
			fileParser.convertFileFromCurrencyValues();
		} catch (IOException | InterruptedException | JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(ApplicationArguments args) {
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
		logger.info("OptionNames: {}", args.getOptionNames());

		for (String name : args.getOptionNames()){
			logger.info("arg-" + name + "=" + args.getOptionValues(name));
		}

		boolean containsOption = args.containsOption("file.name");
		logger.info("Contains file.name: " + containsOption);
	}

}
