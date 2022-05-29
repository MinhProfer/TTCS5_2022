package ltw.groupjava.app.entity;

public enum ProductTag {
    SALE_OFF("SALE_OFF"),
    NEW("NEW"),
    COMING_SOON("COMING_SOON"),
    OTHER("OTHER");

    private String id;

    ProductTag(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static ProductTag fromId(String id) {
        id = id == null ? "" : id.toUpperCase();
        for (ProductTag e : ProductTag.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return OTHER;
    }
}
