package net.helinos.tpsketchup.mixin;

import net.minecraft.server.MinecraftServer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftServer.class)
public abstract class DisableCatchupMixin {
    @Shadow
    private long tickStartTimeNanos;

    @Shadow
    private long tickEndTimeNanos;

    @Inject(method = "runServer()V", at = @At( value = "FIELD", target = "Lnet/minecraft/server/MinecraftServer;tickEndTimeNanos:J", opcode = Opcodes.PUTFIELD), locals = LocalCapture.CAPTURE_FAILHARD)
    private void disableCatchup(CallbackInfo ci, long l) {
        long currentTime = System.nanoTime();
        tickStartTimeNanos = currentTime + l;
        tickEndTimeNanos = tickStartTimeNanos;
    }
}
