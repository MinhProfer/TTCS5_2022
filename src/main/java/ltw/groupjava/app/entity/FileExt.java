package ltw.groupjava.app.entity;

public enum FileExt {
    TXT("TXT"),
    MP3("MP3"),
    MP4("MP4"),
    JPG("JPG"),
    PNG("PNG"),
    JPEG("JPEG"),
    OTHER("OTHER");

    private String id;

    FileExt(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static FileExt fromId(String id) {
        id = id.toUpperCase();
        for (FileExt e : FileExt.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return OTHER;
    }
}
