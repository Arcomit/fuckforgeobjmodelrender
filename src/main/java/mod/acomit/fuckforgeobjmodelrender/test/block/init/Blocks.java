package mod.acomit.fuckforgeobjmodelrender.test.block.init;

import mod.acomit.fuckforgeobjmodelrender.Fuckforgeobjmodelrender;
import mod.acomit.fuckforgeobjmodelrender.test.block.TestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-27 13:27
 * @Description: TODO
 */
public class Blocks {
    public static final DeferredRegister<Block> BLOCKS    = DeferredRegister.create(
            ForgeRegistries        .BLOCKS,
            Fuckforgeobjmodelrender.MODID
    );

    public static final RegistryObject<Block>   TESTBLOCK = BLOCKS.register(
            "test_block",
            () -> new TestBlock(Block.Properties.of()
                    .strength   (3.0f)
                    .lightLevel (state -> 15)
                    .noOcclusion()
            )
    );

    public static final RegistryObject<Block> TESTBLOCK_2 = BLOCKS.register(
            "test_block2",
            () -> new TestBlock(Block.Properties.of()
                    .strength   (3.0f)
                    .lightLevel (state -> 15)
                    .noOcclusion()
            )
    );

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
