package net.fabricmc.example.mixin;

import net.fabricmc.example.accessors.ColorAccessor;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Locale;

@Mixin(FishingBobberEntityRenderer.class)
public abstract class FishingBobberEntityRendererMixin implements ColorAccessor {

    @Shadow
    private static float percentage(int value, int max) {
        return 0;
    }


   @Inject(method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/FishingBobberEntityRenderer;renderFishingLine(FFFLnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/util/math/MatrixStack$Entry;FF)V"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void mixin(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci, PlayerEntity playerEntity, MatrixStack.Entry entry, Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int j, ItemStack itemStack, float h, float k, float l, double d, double e, double m, double n, double o, double p, double q, float r, double s, double t, double u, float v, float w, float x, VertexConsumer vertexConsumer2, MatrixStack.Entry entry2, int y, int z){
        customRenderFishingLine(fishingBobberEntity,v, w, x, vertexConsumer2, entry2, percentage(z, 16), percentage(z + 1, 16));
    }

    @Redirect(method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/FishingBobberEntityRenderer;renderFishingLine(FFFLnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/util/math/MatrixStack$Entry;FF)V"))
    private void mixin(float x, float y, float z, VertexConsumer entry2, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {

    }

    private static void customRenderFishingLine(FishingBobberEntity fishingBobberEntity,float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {
        float f = x * segmentStart;
        float gg = y * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
        float h = z * segmentStart;
        float i = x * segmentEnd - f;
        float j = y * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.25F - gg;
        float k = z * segmentEnd - h;
        float l = MathHelper.sqrt(i * i + j * j + k * k);
        i /= l;
        j /= l;
        k /= l;
        int color = ((ColorAccessor)fishingBobberEntity).getColor();
        if (color == -16383998) {
            color = 1908001;
        }
        if (color == 16383998) {
            color = -1908001;
        }
        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = color & 0xFF;
        float r2;
        float g2;
        float b2;

            switch (((ColorAccessor) fishingBobberEntity).getRGB().toLowerCase(Locale.ROOT)){
                case "flux":
                    r2 = (float)(Math.sin( ((fishingBobberEntity.age) % 240f) * Math.PI / 60f ) + 1 ) / 2;
                    g2 = (float)(Math.sin( ((fishingBobberEntity.age + 80) % 240f) * Math.PI / 60f ) + 1 ) / 2;
                    b2 = (float)(Math.sin( ((fishingBobberEntity.age + 160) % 240f) * Math.PI / 60f ) + 1 ) / 2;

                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(r2, g2 ,b2, 1.0f).normal(matrices.getNormalMatrix(), i, j, k).next();
                    break;
                case "trans":
                    break;
                default:
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(r, g, b, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }


    }

    }
