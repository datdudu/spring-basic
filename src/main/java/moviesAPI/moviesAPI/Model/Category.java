package moviesAPI.moviesAPI.Model;

public enum Category {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String categoyOmdb;

    Category(String categoyOmdb){
        this.categoyOmdb = categoyOmdb;
    }

    public static Category fromString(String text) {
        for(Category category : Category.values()) {
            if(category.categoyOmdb.equalsIgnoreCase(text)) {
                return category;
            }
        }

        throw  new IllegalArgumentException("No category was found for this string");
    }
}
