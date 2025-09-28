package mod.acomit.fuckforgeobjmodelrender.test.block;

import mod.acomit.fuckforgeobjmodelrender.test.block.init.BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-28 15:19
 * @Description: TODO
 */
public class TestBlockEntity_2 extends BlockEntity {
    public TestBlockEntity_2(BlockPos pos, BlockState state) {
        super(BlockEntityTypes.TEST_BLOCK_ENTITY_TYPE_2.get(), pos, state);
    }
}