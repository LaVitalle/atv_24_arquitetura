public class Book implements Product {

    private String title;
    private int priceInCents;

    public Book(String title, int priceInCents) {
        this.title = title;
        this.priceInCents = priceInCents;
    }

    public String getTitle() {
        return title;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }
}
