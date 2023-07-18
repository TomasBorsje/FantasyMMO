package tomasborsje.plugin.fantasymmo.core.util;

public enum ItemGainReason {
    DEFAULT("received"),
    CRAFT("crafted"),
    PURCHASE("purchased"),
    LOOT("looted");

    public final String chatMessage;

    ItemGainReason(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
