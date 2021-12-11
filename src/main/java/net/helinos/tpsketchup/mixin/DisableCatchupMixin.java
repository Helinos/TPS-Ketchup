package net.helinos.tpsketchup.mixin;

import net.minecraft.server.MinecraftServer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(MinecraftServer.class)
public abstract class DisableCatchupMixin {
    @Shadow
    private long nextTickTimestamp;

    @Shadow
    private long timeReference;

    @Redirect(method = "runServer()V", at = @At( value = "FIELD", target = "Lnet/minecraft/server/MinecraftServer;nextTickTimestamp:J", opcode = Opcodes.PUTFIELD))
    private void disableCatchup(MinecraftServer minecraftServer, long time) {
        long curTime = System.nanoTime();
        this.nextTickTimestamp = this.timeReference = curTime / 1000000L + 50L;
    }
}
