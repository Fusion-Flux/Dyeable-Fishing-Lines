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
    private void mixin(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci, PlayerEntity playerEntity, MatrixStack.Entry entry, Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int j, ItemStack itemStack, float h, float k, float l, double d, double e, double m, double n, double o, double p, double q, float r, double s, double t, double u, float v, float w, float x, VertexConsumer vertexConsumer2, MatrixStack.Entry entry2, int y, int z) {
       String name = ((ColorAccessor) fishingBobberEntity).getRGB().toLowerCase(Locale.ROOT);
       switch (name) {
           case "trans", "transgender","lesbian","genderfluid","pan","pansexual","poly","polysexual", "aro","aromantic","aroace" -> {
               if (z <= 15) {
                   customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, percentage(z, 15), percentage(z + 1, 15));
               }
           }
           case "gay", "proud" -> {
               if (z <= 12) {
                   customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, percentage(z, 12), percentage(z + 1, 12));
               }
           }
           case "gay mlm", "mlm","agender","demiboy","demigirl","bigender" -> {
               if (z <= 14) {
                   customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, percentage(z, 14), percentage(z + 1, 14));
               }
           }
           default -> customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, percentage(z, 16), percentage(z + 1, 16));
       }
   }

    @Redirect(method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/FishingBobberEntityRenderer;renderFishingLine(FFFLnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/util/math/MatrixStack$Entry;FF)V"))
    private void mixin(float x, float y, float z, VertexConsumer entry2, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {

    }

    private static void customRenderFishingLine(FishingBobberEntity fishingBobberEntity,float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {

        //System.out.println("SS"+segmentStart);
        //System.out.println("SE"+segmentStart);

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

        switch (((ColorAccessor) fishingBobberEntity).getRGB().toLowerCase(Locale.ROOT)) {
            case "flux" -> {
                r2 = (float) (Math.sin((((segmentStart*16)*2+ fishingBobberEntity.age) % 240f) * Math.PI / 60f) + 1) / 2;
                g2 = (float) (Math.sin(((segmentStart*16)*2+(fishingBobberEntity.age + 80) % 240f) * Math.PI / 60f) + 1) / 2;
                b2 = (float) (Math.sin(((segmentStart*16)*2+(fishingBobberEntity.age + 160) % 240f) * Math.PI / 60f) + 1) / 2;
                buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(r2, g2, b2, 1.0f).normal(matrices.getNormalMatrix(), i, j, k).next();
            }
            case "bi","bisexual" -> {
                if (segmentStart <= 1f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(214, 2, 112, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 16f && segmentStart > 6f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(155, 79, 150, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 56, 168, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "intersex" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 216, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 16f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(121, 2, 170, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 16f && segmentStart > 6f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 216, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(121, 2, 170, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 216, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "demisexual" -> {
                if (segmentStart <= 1f && segmentStart > 11f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 11f / 16f && segmentStart > 9f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(109, 0, 112, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 9f / 16f && segmentStart > 7f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 7f / 16f && segmentStart > 5f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(109, 0, 112, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 5f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(210, 210, 210, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "trans","transgender" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 15f || segmentStart <= 3f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(91, 206, 250, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if ((segmentStart <= 12f / 15f && segmentStart > 9f / 15f) || (segmentStart <= 6f / 15f && segmentStart > 3f / 15f))
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(245, 169, 184, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "gay","proud" -> {
                if (segmentStart <= 1f && segmentStart > 10f / 12f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 12f && segmentStart > 8f / 12f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 140, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 12f && segmentStart > 6f / 12f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 12f && segmentStart > 4f / 12f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 255, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 12f && segmentStart > 2f / 12f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 2f / 12f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 0, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "gay mlm","mlm" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(7, 141, 112, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 14f && segmentStart > 10f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(39, 201, 171, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 14f && segmentStart > 8f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(154, 233, 195, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 14f && segmentStart > 4f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(124, 174, 228, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 14f && segmentStart > 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(81, 74, 204, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(62, 26, 120, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "agender" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 14f && segmentStart > 10f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(188, 196, 198, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 14f && segmentStart > 8f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(182, 245, 131, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 14f && segmentStart > 4f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 14f && segmentStart > 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(188, 196, 198, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "lesbian" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(213, 45, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 154, 86, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(211, 98, 164, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(163, 2, 98, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "ace","asexual" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(163, 163, 163, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(128, 0, 128, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "plural" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(48, 198, 159, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(52, 125, 223, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(107, 63, 190, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "nonbinary" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 244, 48, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(156, 89, 209, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "ukraine" -> {
                if (segmentStart <= 1f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 91, 187, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 213, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "genderfluid" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 117, 162, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(190, 24, 214, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(51, 62, 189, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "aroace" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(226, 140, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(236, 205, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(98, 174, 220, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(32, 56, 86, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "aro","aromantic" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(61, 165, 66, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(167, 211, 121, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(169, 169, 169, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "pan","pansexual" -> {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(254, 33, 139, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(254, 215, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(33, 176, 254, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }
            case "poly","polysexual" -> {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(246, 28, 158, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(7, 213, 105, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(28, 146, 246, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }
            case "genderqueer" -> {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(181, 126, 220, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(74, 129, 35, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "disability" -> {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(234, 191, 63, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(207, 209, 208, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(211, 152, 74, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "demiboy" -> {
                if ((segmentStart <= 1f && segmentStart > 12f / 14f) || segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(127, 127, 127, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if ((segmentStart <= 12f / 14f && segmentStart > 10f / 14f) || (segmentStart <= 4f / 14f && segmentStart > 2f / 14f))
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(196, 196, 196, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if ((segmentStart <= 10f / 14f && segmentStart > 8f / 14f) || (segmentStart <= 6f / 14f && segmentStart > 4f / 14f))
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(154, 217, 235, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }
            case "demigirl" -> {
                if ((segmentStart <= 1f && segmentStart > 12f / 14f) || segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(127, 127, 127, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if ((segmentStart <= 12f / 14f && segmentStart > 10f / 14f) || (segmentStart <= 4f / 14f && segmentStart > 2f / 14f))
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(196, 196, 196, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if ((segmentStart <= 10f / 14f && segmentStart > 8f / 14f) || (segmentStart <= 6f / 14f && segmentStart > 4f / 14f))
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 174, 201, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "bigender" -> {
                if (segmentStart <= 1f && segmentStart > 12f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(196, 121, 160, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 12f / 14f && segmentStart > 10f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(236, 166, 203, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 14f && segmentStart > 8f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(214, 199, 233, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 255, 255, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 6f / 14f && segmentStart > 4f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(214, 199, 233, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 14f && segmentStart > 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(155, 199, 232, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(107, 131, 207, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }


            case "amongus","sussy","sus" -> {
                if (segmentStart <= 1f && segmentStart > 14f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(199, 16, 18, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 14f / 16f && segmentStart > 13f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(19, 40, 57, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 13f / 16f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(149, 202, 220, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(79, 125, 161, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 8f / 16f && segmentStart > 7f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(19, 40, 57, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 7f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(199, 16, 18, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            case "pineapple" -> {
                if (segmentStart <= 1f && segmentStart > 14f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 85, 16, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 14f / 16f && segmentStart > 11f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 113, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 11f / 16f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 10f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 113, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 4f / 16f && segmentStart > 3f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
                if (segmentStart <= 3f / 16f)
                    buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(255, 113, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
            }

            default -> buffer.vertex(matrices.getPositionMatrix(), f, gg, h).color(r, g, b, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
        }


    }

    }
