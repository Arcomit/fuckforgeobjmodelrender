package mod.acomit.fuckforgeobjmodelrender.test.block;

import mod.acomit.fuckforgeobjmodelrender.test.block.init.BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-27 13:26
 * @Description: TODO
 */
public class TestBlockEntity extends BlockEntity {
    public TestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypes.TEST_BLOCK_ENTITY_TYPE.get(), pos, state);
    }
}