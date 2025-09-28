package mod.acomit.fuckforgeobjmodelrender.test.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-27 13:34
 * @Description: TODO
 */
public class TestBlockEntityRenderer_2 implements BlockEntityRenderer<BlockEntity> {
    private static RenderCache renderCache = new RenderCache();
    private static boolean isInit = false;

    public TestBlockEntityRenderer_2(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(
            @NotNull BlockEntity       entity,
                     float             partialTicks,
                     PoseStack         poseStack,
            @NotNull MultiBufferSource bufferSource,
                     int               packedLight,
                     int               packedOverlay
    ) {
        float time = (Minecraft.getInstance().level.getGameTime() + partialTicks) / 20.0f;
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
//        poseStack.mulPose(com.mojang.math.Axis.YP.rotation(time));
//        poseStack.mulPose(com.mojang.math.Axis.XP.rotation(time * 0.5f));
        poseStack.scale(0.01F, 0.01F, 0.01F);


        if (!isInit){
            init();
        }

        renderCache.render(poseStack,packedLight,packedOverlay);
        poseStack.popPose();
    }

    public static void init(){
        renderCache.init(false);
        isInit = true;
    }

}
