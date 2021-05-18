package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SellOneItemTest {
    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("12345");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("23456");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    private static class Display {
        private String text;

        public String getText() {
            return text;
        }
    }

    private static class Sale {
        private final Display display;

        public Sale(final Display display) {
            this.display = display;
        }

        public void onBarcode(final String barcode) {
            if ("12345".equals(barcode))
                display.text = "EUR 12.50";
            else
                display.text = "EUR 7.95";
        }
    }
}
