public class PromotionalPriceVisitor implements ProductVisitor {

    @Override
    public void visit(Book book) {
        long promotionalPrice = (long) book.getPriceInCents() * 90 / 100;
        System.out.println("Book: " + book.getTitle() + " - promotional cents: " + promotionalPrice);
    }

    @Override
    public void visit(Electronics electronics) {
        long promotionalPrice = (long) electronics.getPriceInCents() * 95 / 100;
        System.out.println("Electronics: " + electronics.getModel() + " - promotional cents: " + promotionalPrice);
    }
}
