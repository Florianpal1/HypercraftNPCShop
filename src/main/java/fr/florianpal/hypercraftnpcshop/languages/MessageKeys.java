package fr.florianpal.hypercraftnpcshop.languages;


import co.aikar.locales.MessageKey;
import co.aikar.locales.MessageKeyProvider;

public enum MessageKeys implements MessageKeyProvider {

    SHOP_BUYING_SUCCESS,
    SHOP_BUYING_NOT_HAVE_ITEMS,
    SHOP_SELLING_SUCCESS,
    SHOP_SELLING_NOT_HAVE_MONEY;

    private static final String PREFIX = "hypercraft";

    private final MessageKey key = MessageKey.of(PREFIX + "." + this.name().toLowerCase());

    public MessageKey getMessageKey() {
        return key;
    }
}
