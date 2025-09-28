package mod.acomit.fuckforgeobjmodelrender;

import com.mojang.logging.LogUtils;
import mod.acomit.fuckforgeobjmodelrender.test.block.init.BlockEntityTypes;
import mod.acomit.fuckforgeobjmodelrender.test.block.init.Blocks;
import mod.acomit.fuckforgeobjmodelrender.test.block.init.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@SuppressWarnings("removal")
@Mod(Fuckforgeobjmodelrender.MODID)
public class Fuckforgeobjmodelrender {

    public static final String MODID  = "fuckforgeobjmodelrender";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Fuckforgeobjmodelrender() {
        LOGGER.info(MODID + " is loaded!");

        FMLJavaModLoadingContext context       = FMLJavaModLoadingContext.get();
        IEventBus                modEventBus   = context.getModEventBus();
        IEventBus                forgeEventBus = MinecraftForge.EVENT_BUS;

        Blocks.register(modEventBus);
        BlockEntityTypes.register(modEventBus);
        Items.register(modEventBus);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(Fuckforgeobjmodelrender.MODID, path);
    }

}
