package mod.acomit.fuckforgeobjmodelrender.test.block.init;

import mod.acomit.fuckforgeobjmodelrender.Fuckforgeobjmodelrender;
import mod.acomit.fuckforgeobjmodelrender.test.block.TestBlock;
import mod.acomit.fuckforgeobjmodelrender.test.block.TestBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-27 13:30
 * @Description: TODO
 */
public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            ForgeRegistries        .BLOCK_ENTITY_TYPES,
            Fuckforgeobjmodelrender.MODID
    );

    public static final RegistryObject<BlockEntityType<TestBlockEntity>> TEST_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "test_block_entity",
                    () -> BlockEntityType.Builder.of(
                            TestBlockEntity::new,
                            Blocks.TESTBLOCK.get()
                    ).build(null)
    );

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
