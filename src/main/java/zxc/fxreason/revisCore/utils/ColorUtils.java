package zxc.fxreason.revisCore.utils;

/*
 * By fxreason
 * 25.02.2026
 **/

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.awt.*;

public class ColorUtils {

    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static Component parse(String input) {
        if (input == null) return Component.empty();
        return MINI_MESSAGE.deserialize(input);
    }

    public static Component parseItem(String input) {
        if (input == null) return Component.empty();
        return MiniMessage.miniMessage().deserialize(input).decoration(TextDecoration.ITALIC, false);
    }

    public static Component parseFull(String input) {
        String legacyTranslated = input.replace("&", "§");
        Component legacy = LegacyComponentSerializer.legacySection().deserialize(legacyTranslated);

        return MINI_MESSAGE.deserialize(input);
    }
}
