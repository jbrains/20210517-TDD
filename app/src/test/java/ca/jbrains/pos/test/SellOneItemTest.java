package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SellOneItemTest {
    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale();

        sale.onBarcode("12345");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    private static class Display {
        public String getText() {
            return "EUR 12.50";
        }
    }

    private static class Sale {
        public void onBarcode(final String barcode) {
        }
    }
}
