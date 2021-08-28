package club.xena.client.api.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    EXPLOIT("Exploit"),
    MISC("Misc"),
    CHAT("Chat"),
    CORE("Core");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
}