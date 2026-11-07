import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Book("Design Patterns", 10000),
                new Electronics("Headphones", 20000));

        System.out.println("=== Report ===");
        applyToAll(products, new ProductReportVisitor());

        System.out.println("\n=== Promotional prices ===");
        applyToAll(products, new PromotionalPriceVisitor());

        System.out.println("\n=== Report again (original prices preserved) ===");
        applyToAll(products, new ProductReportVisitor());

        System.out.println("\n=== Empty collection ===");
        applyToAll(Collections.emptyList(), new ProductReportVisitor());
        System.out.println("(no products)");

        System.out.println("\n=== Book of 999 cents ===");
        applyToAll(Arrays.asList(new Book("Short Book", 999)), new PromotionalPriceVisitor());

        System.out.println("\n=== Total price ===");
        TotalPriceVisitor total = new TotalPriceVisitor();
        applyToAll(products, total);
        System.out.println("Total cents: " + total.getTotalInCents());

        TotalPriceVisitor emptyTotal = new TotalPriceVisitor();
        applyToAll(Collections.emptyList(), emptyTotal);
        System.out.println("Total cents (empty collection): " + emptyTotal.getTotalInCents());
    }

    private static void applyToAll(List<Product> products, ProductVisitor visitor) {
        for (Product product : products) {
            product.accept(visitor);
        }
    }
}
