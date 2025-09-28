package mod.acomit.fuckforgeobjmodelrender.test.block.event;

import mod.acomit.fuckforgeobjmodelrender.Fuckforgeobjmodelrender;
import mod.acomit.fuckforgeobjmodelrender.test.block.init.BlockEntityTypes;
import mod.acomit.fuckforgeobjmodelrender.test.block.render.TestBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-27 13:35
 * @Description: TODO
 */
@Mod.EventBusSubscriber(modid = Fuckforgeobjmodelrender.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterBlockEntityRendererHandler {

    @SubscribeEvent
    public static void onRegisterBlockEntityRenderer(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(BlockEntityTypes.TEST_BLOCK_ENTITY_TYPE.get(), TestBlockEntityRenderer::new);
    }

}
