package com.justlel.plotflagsselector.helpers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullHelper {

    /**
     * Create an empty {@link ItemStack head}.
     *
     * @return An empty {@link ItemStack head}.
     */
    private static ItemStack createHead() {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    }

    /**
     * Generate a random {@link GameProfile profile}.
     * This will be later modified.
     *
     * @return A random {@link GameProfile profile}
     */
    public static GameProfile generateRandomProfile() {
        return new GameProfile(UUID.randomUUID(), "");
    }

    /**
     * Edit the generated random profile and set on it
     * the texture of the wanted skull.
     *
     * @param texture The <a href="http://textures.minecraft.net">texture</a> to be set on the head.
     * @param profile The profile on which the texture will be set.
     * @return A custom {@link ItemStack head}.
     */
    public static ItemStack getCustomTextureHead(String texture, GameProfile profile) {
        ItemStack head = SkullHelper.createHead();
        String textureUrl = "http://textures.minecraft.net/texture/" + texture;
        profile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{\"textures\":{\"SKIN\":{\"url\":\"" + textureUrl + "\"}}}")));
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

            head.setItemMeta(headMeta);
            return head;
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack getCustomTextureHead(String texture) {
        return getCustomTextureHead(texture, generateRandomProfile());
    }
}
