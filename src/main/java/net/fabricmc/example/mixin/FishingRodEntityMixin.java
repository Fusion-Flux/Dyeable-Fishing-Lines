package net.fabricmc.example.mixin;

import net.fabricmc.example.accessors.ColorAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingRodEntityMixin extends Entity implements ColorAccessor {

    private static final TrackedData<Boolean> DORGB = DataTracker.registerData(FishingBobberEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(FishingBobberEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public FishingRodEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker",at = @At("HEAD"))
    public void initDataTracker(CallbackInfo ci) {
        this.getDataTracker().startTracking(DORGB, false);
        this.getDataTracker().startTracking(COLOR, 0);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setColor(nbt.getInt("color"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("color", this.getColor());
    }

    @Override
    public Integer getColor() {
        return getDataTracker().get(COLOR);
    }
    @Override
    public void setColor(Integer color) {
        this.getDataTracker().set(COLOR, color);
    }

    @Override
    public boolean getRGB() {
        return getDataTracker().get(DORGB);
    }
    @Override
    public void setRGB(boolean color) {
        this.getDataTracker().set(DORGB, color);
    }
}
