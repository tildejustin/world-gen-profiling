package dev.tildejustin.world_gen_profiling.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Unique
    private static long start;

    @Unique
    private static final List<Float> timeList = new CopyOnWriteArrayList<>();

    @Inject(method = "prepareWorlds", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/MinecraftServer;getTimeMillis()J", ordinal = 0))
    private void startTime(CallbackInfo ci) {
        start = System.currentTimeMillis();
    }

    @Inject(method = "prepareWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;save()V"))
    private void endTime(CallbackInfo ci) {
        long time = System.currentTimeMillis() - MinecraftServerMixin.start;
        float floatTime = ((float) time / 1000);
        timeList.add(floatTime);
        System.out.println(timeList);
    }
}
