package com.xpgaming.pixelhunt.ui;

import ca.landonjw.gooeylibs.inventory.api.Button;
import ca.landonjw.gooeylibs.inventory.api.Page;
import ca.landonjw.gooeylibs.inventory.api.Template;
import com.xpgaming.pixelhunt.config.PixelHuntConfig;
import com.xpgaming.pixelhunt.hunt.PixelHunt;
import com.xpgaming.pixelhunt.hunt.PixelHuntFactory;
import com.xpgaming.pixelhunt.utils.item.ItemBuilder;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;

public class HuntUI {

    private static final Button BACKGROUND_FILLER = Button.of(new ItemBuilder()
            .type(Item.getByNameOrId(PixelHuntConfig.getInstance().getBackgroundItem()))
            .damage(PixelHuntConfig.getInstance().getBackgroundItemDamage())
            .build());

    private static final Button BACKGROUND_OFF_FILLER = Button.of(new ItemBuilder()
            .type(Item.getByNameOrId(PixelHuntConfig.getInstance().getOffColourBackgroundItem()))
            .damage(PixelHuntConfig.getInstance().getOffColourBackgroundItemDamage())
            .build());

    public static void open(EntityPlayerMP player) {
        Template.Builder template = Template.builder(PixelHuntConfig.getInstance().getGuiHeight());

        if (PixelHuntConfig.getInstance().isCheckeredBackground()) {
            template.checker(0, 0, 8, PixelHuntConfig.getInstance().getGuiHeight() - 1, BACKGROUND_FILLER, BACKGROUND_OFF_FILLER);
        } else {
            template.fill(BACKGROUND_FILLER);
        }

        int deltaY = 0;
        int deltaX = 0;

        for (PixelHunt hunt : PixelHuntFactory.getAllHunts()) {
            template.set(1 + deltaX, 1 + deltaY, Button.of(hunt.getDisplay()));

            ++deltaX;

            if (deltaX == 7) {
                deltaX = 1;
                ++deltaY;
            }
        }

        Page.builder()
                .title(PixelHuntConfig.getInstance().getGuiName())
                .template(template.build())
                .build().forceOpenPage(player);
    }
}
