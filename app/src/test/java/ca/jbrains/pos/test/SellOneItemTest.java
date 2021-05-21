package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(new HashMap<>() {{
            put("12345", "EUR 12.50");
            put("23456", "EUR 7.95");
        }}));

        sale.onBarcode("12345");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(new HashMap<>() {{
            put("12345", "EUR 12.50");
            put("23456", "EUR 7.95");
        }}));

        sale.onBarcode("23456");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(new HashMap<>()));

        sale.onBarcode("99999");

        Assertions.assertEquals("Product not found: 99999", display.getText());
    }

    @Test
    void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(null));

        sale.onBarcode("");

        Assertions.assertEquals("Scanning error: empty barcode", display.getText());
    }

    private static class Display {
        private String text;

        public String getText() {
            return text;
        }

        private void displayPrice(final String price) {
            text = price;
        }

        private void displayEmptyBarcodeMessage() {
            text = "Scanning error: empty barcode";
        }

        private void displayProductNotFoundMessage(final String barcode) {
            text = "Product not found: " + barcode;
        }
    }

    public static class Catalog {
        private final Map<String, String> pricesByBarcode;

        public Catalog(final Map<String, String> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        private String findPrice(final String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }

    private static class Sale {
        private final Display display;
        private final Catalog catalog;

        public Sale(final Display display, final Catalog catalog) {
            this.display = display;
            this.catalog = catalog;
        }

        public void onBarcode(final String barcode) {
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMessage();
                return;
            }

            final String price = catalog.findPrice(barcode);
            if (price == null) {
                display.displayProductNotFoundMessage(barcode);
            }
            else {
                display.displayPrice(price);
            }
        }

    }
}
