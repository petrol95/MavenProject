package HomeWork2;

public class Good {

    private int id;
    private int productId;
    private String title;
    private int cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productId;
    }

    public void setProductID(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Good {" +
                "id=" + id +
                ", productId=" + productId +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }
}
