# Currency-Converter-Tool

A simple tool to convert currencies files powered by [CurrencyConverterAPI](https://exchangeratesapi.io/)

The file should be in the following format:
```text
SRC_CURRENCY
TARGET_CURRENCY
SRC_CURRENCY_value_1
...
SRC_CURRENCY_value_N
```

For example:
```text
USD
EUR
1
5
10.5
200
```
The output for that given file should be like:
```text
0.82
4.1
8.61
164
```
assuming the rate between USD to EUR is 0.82;
output will be printed in the console.
