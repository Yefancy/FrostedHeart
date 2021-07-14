package com.teammoeg.frostedheart.common.item;

import com.teammoeg.frostedheart.FHContent;
import com.teammoeg.frostedheart.FHMain;
import net.minecraft.item.Item;

public class FHBaseItem extends Item {
    public String itemName;

    public FHBaseItem(String name, Properties properties) {
        super(properties);
        this.itemName = name;
        setRegistryName(FHMain.MODID, name);
        FHContent.registeredFHItems.add(this);
    }
}
