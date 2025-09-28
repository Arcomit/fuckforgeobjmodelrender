package mod.acomit.fuckforgeobjmodelrender.test.block.init;

import mod.acomit.fuckforgeobjmodelrender.Fuckforgeobjmodelrender;
import mod.acomit.fuckforgeobjmodelrender.test.block.TestBlockEntity;
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
public class BlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries        .BLOCK_ENTITY_TYPES,
            Fuckforgeobjmodelrender.MODID
    );

    public static final RegistryObject<BlockEntityType<TestBlockEntity>> TEST_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register(
            "test_block_entity_type",
                    () -> BlockEntityType.Builder.of(
                            TestBlockEntity::new,
                            Blocks.TESTBLOCK.get()
                    ).build(null)
    );

    public static final RegistryObject<BlockEntityType<TestBlockEntity>> TEST_BLOCK_ENTITY_TYPE_2 = BLOCK_ENTITY_TYPES.register(
            "test_block_entity_type2",
            () -> BlockEntityType.Builder.of(
                    TestBlockEntity::new,
                    Blocks.TESTBLOCK_2.get()
            ).build(null)
    );

    public static void register(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
