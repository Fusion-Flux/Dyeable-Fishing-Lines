package net.fabricmc.example.mixin;

import net.fabricmc.example.accessors.ColorAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Locale;
import java.util.Objects;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin implements DyeableItem {

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 1908001;
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void mixin(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir,ItemStack itemStack,int i,int j) {
        FishingBobberEntity bobber = new FishingBobberEntity(user, world, j, i);
        ((ColorAccessor)bobber).setColor(this.getColor(itemStack));
        System.out.println(itemStack.getName().asString());
        if(itemStack.hasCustomName() && Objects.equals(itemStack.getName().asString().toLowerCase(Locale.ROOT), "flux")){
            ((ColorAccessor)bobber).setRGB(true);
        }
        world.spawnEntity(bobber);
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private boolean mixin(World instance, Entity entity) {

        return false;

    }
}
