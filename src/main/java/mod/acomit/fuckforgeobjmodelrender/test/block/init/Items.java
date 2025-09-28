package mod.acomit.fuckforgeobjmodelrender.test.block.init;

import mod.acomit.fuckforgeobjmodelrender.Fuckforgeobjmodelrender;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-27 14:24
 * @Description: TODO
 */
public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS,
            Fuckforgeobjmodelrender.MODID
    );

    public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register(
            "test_block_item",
            () -> new BlockItem(
                    Blocks.TESTBLOCK.get(),
                    new Item.Properties()
            )
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
