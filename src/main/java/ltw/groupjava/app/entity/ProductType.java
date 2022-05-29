package ltw.groupjava.app.entity;

public enum ProductType {
    VEGETABLE("VEGETABLE"),
    MEAT("MEAT"),
    FISH("FISH"),
    OTHER("OTHER");

    private String id;

    ProductType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static ProductType fromId(String id) {
        id = id == null ? "" : id.toUpperCase();
        for (ProductType e : ProductType.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return OTHER;
    }
}
