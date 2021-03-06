package net.minecraft.src;

import net.minecraft.client.Minecraft;
import bspkrs.util.BSProp;
import bspkrs.util.BSPropRegistry;
import bspkrs.util.ModVersionChecker;

public class mod_bspkrsCore extends BaseMod
{
    @BSProp(info = "Set to true to allow checking for updates for ALL of my mods, false to disable")
    public static boolean     allowUpdateCheck = true;
    @BSProp
    public static boolean     allowDebugOutput = false;
    
    private ModVersionChecker versionChecker;
    private final String      versionURL       = "http://bspk.rs/Minecraft/1.5.1/bspkrsCore.version";
    private final String      mcfTopic         = "http://www.minecraftforum.net/topic/1114612-";
    private static boolean    doUpdateCheck;
    
    public mod_bspkrsCore()
    {
        BSPropRegistry.registerPropHandler(this.getClass());
        doUpdateCheck = allowUpdateCheck;
    }
    
    @Override
    public String getName()
    {
        return "bspkrsCore";
    }
    
    @Override
    public String getVersion()
    {
        return "v2.01(1.5.1)";
    }
    
    @Override
    public String getPriorities()
    {
        return "before:*";
    }
    
    @Override
    public void load()
    {
        if (doUpdateCheck)
        {
            versionChecker = new ModVersionChecker(getName(), getVersion(), versionURL, mcfTopic, ModLoader.getLogger());
            versionChecker.checkVersionWithLoggingBySubStringAsFloat(1, 4);
            ModLoader.setInGameHook(this, true, true);
        }
    }
    
    @Override
    public boolean onTickInGame(float f, Minecraft mc)
    {
        if (doUpdateCheck && mc.theWorld.isRemote)
        {
            if (!versionChecker.isCurrentVersionBySubStringAsFloatNewer(1, 4))
                for (String msg : versionChecker.getInGameMessage())
                    mc.thePlayer.addChatMessage(msg);
            doUpdateCheck = false;
        }
        
        if (allowDebugOutput && mc.theWorld.isRemote)
        {
            mc.thePlayer.addChatMessage("\2470\2470\2471\2472\2473\2474\2475\2476\2477\247e\247f");
        }
        
        return false;
    }
}
